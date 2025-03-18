package com.mayurmotinge.abhyudaya.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @Multipart
    @POST("send_chat_message.php")
    Call<ResponseBody> sendMessage(
            @Part("message") RequestBody message,
            @Part("sender") RequestBody sender,
            @Part("sender_id") RequestBody senderId,
            @Part("society_id") RequestBody societyId,
            @Part MultipartBody.Part file
    );
}
