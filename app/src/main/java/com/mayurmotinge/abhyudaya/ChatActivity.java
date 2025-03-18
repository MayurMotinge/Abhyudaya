package com.mayurmotinge.abhyudaya;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mayurmotinge.abhyudaya.adapters.ChatAdapter;
import com.mayurmotinge.abhyudaya.api.ApiClient;
import com.mayurmotinge.abhyudaya.api.ApiService;
import com.mayurmotinge.abhyudaya.supportclasses.Urls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<Object> chatList = new ArrayList<>();
    private EditText messageInput;
    private ImageView sendButton, attachButton, ivSelectedMedia, btnRemoveMedia;
    private Uri selectedMediaUri;
    private String societyId = "1"; // Replace with dynamic society ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Initialize UI elements
        chatRecyclerView = findViewById(R.id.rvChatMessages);
        messageInput = findViewById(R.id.etChatMsgInput);
        sendButton = findViewById(R.id.btnSendMsg);
        attachButton = findViewById(R.id.attachButton);
        ivSelectedMedia = findViewById(R.id.ivSelectedMedia);
        btnRemoveMedia = findViewById(R.id.btnRemoveMedia);

        // Initialize adapter
        chatAdapter = new ChatAdapter(chatList, "currentUserId");
        chatRecyclerView.setAdapter(chatAdapter);

        // Attach listeners
        attachButton.setOnClickListener(v -> openMediaSelector());
        sendButton.setOnClickListener(v -> sendMessage());
        btnRemoveMedia.setOnClickListener(v -> {
            selectedMediaUri = null;
            ivSelectedMedia.setImageURI(null);
            ivSelectedMedia.setVisibility(ImageView.GONE);
            btnRemoveMedia.setVisibility(ImageView.GONE);
        });
    }

    // Opens the media selector for image or video
    private void openMediaSelector() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/* video/*");
        startActivityForResult(intent, 100);
    }

    // Handles the result of the media selector
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedMediaUri = data.getData();
            // Show the selected media in the input bar preview
            assert selectedMediaUri != null;
            String mimeType = getMimeType(selectedMediaUri);
            ivSelectedMedia.setVisibility(ImageView.VISIBLE);
            btnRemoveMedia.setVisibility(ImageView.VISIBLE);
            if (mimeType != null && mimeType.startsWith("image/")) {
                // Display image directly
                ivSelectedMedia.setImageURI(selectedMediaUri);
            } else if (mimeType != null && mimeType.startsWith("video/")) {
                ivSelectedMedia.setImageResource(R.drawable.icon_video);
                ivSelectedMedia.setScaleType(ImageView.ScaleType.CENTER_CROP);;
            }
        }
    }

    // Sends a chat message with optional media attachment
    private void sendMessage() {
        String messageText = messageInput.getText().toString().trim();

        // Check if message or media is selected
        if (messageText.isEmpty() && selectedMediaUri == null) {
            Toast.makeText(this, "Enter a message or select media", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create RequestBody for the message
        RequestBody messageBody = RequestBody.create(messageText, MediaType.parse("text/plain"));
        RequestBody senderBody = RequestBody.create("currentUser", MediaType.parse("text/plain"));
        RequestBody senderIdBody = RequestBody.create("currentUserId", MediaType.parse("text/plain"));
        RequestBody societyIdBody = RequestBody.create(societyId, MediaType.parse("text/plain"));

        // Create MultipartBody.Part for the media file
        MultipartBody.Part filePart = null;
        if (selectedMediaUri != null) {
            RequestBody requestFile = createRequestBodyFromUri(selectedMediaUri);
            filePart = MultipartBody.Part.createFormData("file", getFileNameFromUri(selectedMediaUri), requestFile);
        }

        // Create Retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Urls.URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create ApiService and make the API call
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ResponseBody> call = apiService.sendMessage(messageBody, senderBody, senderIdBody, societyIdBody, filePart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Toast.makeText(ChatActivity.this, "Message Sent!", Toast.LENGTH_SHORT).show();
                messageInput.setText("");
                selectedMediaUri = null;
                ivSelectedMedia.setVisibility(ImageView.GONE);
                btnRemoveMedia.setVisibility(ImageView.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(ChatActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Create RequestBody from Uri by reading the input stream bytes
    private RequestBody createRequestBodyFromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            assert inputStream != null;
            byte[] fileBytes = getBytes(inputStream);
            return RequestBody.create(fileBytes, MediaType.parse(getMimeType(uri)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Reads the entire InputStream into a byte array
    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    // Retrieves the display name of the file from its Uri
    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (displayNameIndex != -1) {
                        result = cursor.getString(displayNameIndex);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            if(result!= null){
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
        }
        return result;
    }

    // Retrieves the MIME type of the file from its Uri
    private String getMimeType(Uri uri) {
        if (Objects.equals(uri.getScheme(), ContentResolver.SCHEME_CONTENT)) {
            ContentResolver contentResolver = getContentResolver();
            return contentResolver.getType(uri);
        } else {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    MimeTypeMap.getFileExtensionFromUrl(uri.toString()));
        }
    }
}
