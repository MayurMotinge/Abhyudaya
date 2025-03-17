package com.mayurmotinge.abhyudaya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.app.AppLocalesStorageHelper;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mayurmotinge.abhyudaya.supportclasses.Urls;

import org.json.JSONObject;

public class HomeFragment extends Fragment {

    CardView cvNotice, cvEvent, cvLiveChat;

    TextView tvNoticeCardTitle, tvNoticeCardDescription, tvNoticeCardSentAt,
            tvEventCardTitle, tvEventCardDescription, tvEventCardSentAt,
             tvRecentChat, tvChatSentBy, tvError;

    ImageView ivNoticeList, ivEventList;

    LinearLayout llMaintenanceAddFeedbackEmergency, llMaintenance, llFeedbacks, llEmergency;

    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cvNotice = view.findViewById(R.id.cvNoticeCard);
        cvEvent = view.findViewById(R.id.cvEventCard);
        cvLiveChat = view.findViewById(R.id.cvLiveChatCard);

        tvNoticeCardTitle = view.findViewById(R.id.tvNoticeCardTitle);
        tvNoticeCardDescription = view.findViewById(R.id.tvNoticeCardDescription);
        tvNoticeCardSentAt = view.findViewById(R.id.tvNoticeCardSentAt);

        tvEventCardTitle = view.findViewById(R.id.tvEventCardTitle);
        tvEventCardDescription = view.findViewById(R.id.tvEventCardDescription);
        tvEventCardSentAt = view.findViewById(R.id.tvEventCardSentAt);

        tvRecentChat = view.findViewById(R.id.tvRecentChat);
        tvChatSentBy = view.findViewById(R.id.tvChatSentBy);

        tvError = view.findViewById(R.id.tvError);

        ivNoticeList = view.findViewById(R.id.ivNoticeList);
        ivEventList = view.findViewById(R.id.ivEventList);

        llMaintenanceAddFeedbackEmergency = view.findViewById(R.id.llMaintenanceAddFeedbackEmergency);
        llMaintenance = view.findViewById(R.id.llMaintenance);
        llFeedbacks = view.findViewById(R.id.llFeedbacks);
        llEmergency = view.findViewById(R.id.llEmergency);

        progressBar = view.findViewById(R.id.homeProgressBar);

        fetchHomeData();

        //Setting Click Listeners
        cvNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewNoticeActivity.class);
                startActivity(intent);
            }
        });
        ivNoticeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NoticeListActivity.class);
                startActivity(intent);
            }
        });

        cvEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewEventActivity.class);
                startActivity(intent);
            }
        });
        ivEventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EventListActivity.class);
                startActivity(intent);
            }
        });

        cvLiveChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        });

        llMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        llFeedbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedbackListActivity.class);
                startActivity(intent);
            }
        });
        llEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
    //Fetching Data from the server
    private void fetchHomeData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        progressBar.setVisibility(View.VISIBLE);
        cvNotice.setVisibility(View.GONE);
        cvEvent.setVisibility(View.GONE);
        llMaintenanceAddFeedbackEmergency.setVisibility(View.GONE);
        cvLiveChat.setVisibility(View.GONE);

        progressBar.setVisibility(View.VISIBLE);

        StringRequest request = new StringRequest(Request.Method.POST, Urls.URL_HOME_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getBoolean("success")){
                                setNoticeCard(jsonObject);
                                setEventCard(jsonObject);
                                setLiveChatCard(jsonObject);

                                cvNotice.setVisibility(View.VISIBLE);
                                cvEvent.setVisibility(View.VISIBLE);
                                llMaintenanceAddFeedbackEmergency.setVisibility(View.VISIBLE);
                                cvLiveChat.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                            } else {
                                showError("Failed to Display Data, Please Try Again.");
                            }

                        } catch (Exception e) {
                            showError("Error Parsing Response");
                            Log.e("Home", "Error Parsing Response", e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showError("Server/Network error! Check your connection.");
                Log.e("Home", "Error: "+error.getMessage());
            }
        });

        queue.add(request);

    }

    //Showing Error Message
    private void showError(String msg){
        cvNotice.setVisibility(View.GONE);
        cvEvent.setVisibility(View.GONE);
        llMaintenanceAddFeedbackEmergency.setVisibility(View.GONE);
        cvLiveChat.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(msg);
    }

    //Setting up the cards at homepage
    private void setLiveChatCard(JSONObject jsonObject) {

        try {
            JSONObject liveChat = jsonObject.getJSONObject("liveChat");
            tvRecentChat.setText(liveChat.getString("recentChat"));
            tvChatSentBy.setText(liveChat.getString("sentBy"));
        } catch (Exception e){
            showError("Failed to Display Data");
            Log.e("Home", "Error Parsing Response", e);
        }

    }

    private void setNoticeCard(JSONObject jsonObject) {

        try {
            JSONObject notice = jsonObject.getJSONObject("notice");
            tvNoticeCardTitle.setText(notice.getString("title"));
            tvNoticeCardDescription.setText(notice.getString("description"));
            tvNoticeCardSentAt.setText(notice.getString("sentAt"));
        } catch (Exception e){
            showError("Failed to Display Data");
            Log.e("Home", "Error Parsing Response", e);
        }

    }

    private void setEventCard(JSONObject jsonObject) {

        try {
            JSONObject event = jsonObject.getJSONObject("event");
            tvEventCardTitle.setText(event.getString("title"));
            tvEventCardDescription.setText(event.getString("description"));
            tvEventCardSentAt.setText(event.getString("sentAt"));
        } catch (Exception e){
            showError("Failed to Display Data");
            Log.e("Home", "Error Parsing Response", e);
        }

    }

    //

}