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
import android.widget.TextView;

public class HomeFragment extends Fragment {

    CardView cvNotice, cvEvent, cvLiveChat;

    ImageView ivNoticeList, ivEventList;

    LinearLayout llEmergency, llAddFeedback, llMaintenance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cvNotice = view.findViewById(R.id.cvNoticeCard);
        cvEvent = view.findViewById(R.id.cvEventCard);
        cvLiveChat = view.findViewById(R.id.cvLiveChatCard);

        ivNoticeList = view.findViewById(R.id.ivNoticeList);
        ivEventList = view.findViewById(R.id.ivEventList);

        llEmergency = view.findViewById(R.id.llEmergency);
        llAddFeedback = view.findViewById(R.id.llAddFeedback);
        llMaintenance = view.findViewById(R.id.llMaintenance);

        cvNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewNoticeActivity.class);
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

        cvLiveChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        });

        ivNoticeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewNoticeActivity.class);
                startActivity(intent);
            }
        });

        ivEventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewEventActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }
    private View setMaintenanceCard(View view) {

        return view;
    }

    private View setNoticeCard(View view) {

        return view;
    }

    private View setEventCard(View view) {

        return view;
    }


}