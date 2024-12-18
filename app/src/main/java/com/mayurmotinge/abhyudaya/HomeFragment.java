package com.mayurmotinge.abhyudaya;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.PopupMenu;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    CardView cvMaintenanceCard, cvMaintenanceCardAdm, cvFeedbackCard, cvNoticeBoard, cvEventBoard;
    TextView tvMaintenanceTitle, tvMaintenanceAmount, tvMaintenanceDueDate,
            tvMaintenanceTitleAdm, tvNoOfMentePaid, tvNoOfMentePending, tvNoOfResidents,
            tvFeedbackTitle, tvFeedbackFrom, tvFeedbackType,
            ttNoticeTitle, ttNoticeDateTime, ttEventTitle, ttEventDateTime;
    ImageView ivNoticeCardImage, ivEventCardImage;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        cvMaintenanceCard = view.findViewById(R.id.cvMaintenanceCard);
        tvMaintenanceTitle = view.findViewById(R.id.tvMaintenanceTitle);
        tvMaintenanceAmount = view.findViewById(R.id.tvMaintenanceAmount);
        tvMaintenanceDueDate = view.findViewById(R.id.tvMaintenanceDueDate);

        cvMaintenanceCardAdm = view.findViewById(R.id.cvMaintenanceCardAdm);
        tvMaintenanceTitleAdm = view.findViewById(R.id.tvMaintenanceTitleAdm);
        tvNoOfMentePaid = view.findViewById(R.id.tvNoOfMentePaid);
        tvNoOfMentePending = view.findViewById(R.id.tvNoOfMentePending);
        tvNoOfResidents = view.findViewById(R.id.tvNoOfResidents);

        cvFeedbackCard = view.findViewById(R.id.cvFeedbackCard);
        tvFeedbackTitle = view.findViewById(R.id.tvFeedbackTitle);
        tvFeedbackFrom = view.findViewById(R.id.tvFeedbackFrom);
        tvFeedbackType = view.findViewById(R.id.tvFeedbackType);

        cvNoticeBoard = view.findViewById(R.id.cvNoticeBoard);
        ttNoticeTitle = view.findViewById(R.id.ttNoticeTitle);
        ttNoticeDateTime = view.findViewById(R.id.ttNoticeDateTime);
        ivNoticeCardImage = view.findViewById(R.id.ivNoticeCardImage);

        cvEventBoard = view.findViewById(R.id.cvEventBoard);
        ttEventTitle = view.findViewById(R.id.ttEventTitle);
        ttEventDateTime = view.findViewById(R.id.ttEventDateTime);
        ivEventCardImage = view.findViewById(R.id.ivEventCardImage);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());;
        editor = sharedPreferences.edit();

//        String userRole = sharedPreferences.getString("user_role", "");
        String userRole = "admin";

        if (userRole.equalsIgnoreCase("admin") || userRole.equalsIgnoreCase("officer")){
            cvMaintenanceCardAdm.setVisibility(View.VISIBLE);
            cvMaintenanceCard.setVisibility(View.GONE);
            view = setMaintenanceCardAdm(view);
        } else {
            cvMaintenanceCardAdm.setVisibility(View.GONE);
            cvMaintenanceCard.setVisibility(View.VISIBLE);
            view = setMaintenanceCard(view);
        }


        cvMaintenanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);
            }
        });

        cvMaintenanceCardAdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MaintenancesListActivity.class);
                startActivity(i);
            }
        });

        cvFeedbackCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FeedbackListActivity.class);
                startActivity(i);
            }
        });

        cvNoticeBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), cvNoticeBoard);
                popupMenu.getMenuInflater().inflate(R.menu.home_cards_menu, popupMenu.getMenu());

                popupMenu.getMenu().removeItem(R.id.viewEvent);
                popupMenu.getMenu().removeItem(R.id.viewRecentEvents);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.viewNotce){
                            Intent i = new Intent(getActivity(), ViewNoticeActivity.class);
                            startActivity(i);
                            return true;
                        }
                        else if (item.getItemId() == R.id.viewRecentNotices) {
                            Intent i = new Intent(getActivity(), NoticeEventListActivity.class);
                            i.putExtra("title", "Recent Notices");
                            startActivity(i);
                            return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        cvEventBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), cvEventBoard);
                popupMenu.getMenuInflater().inflate(R.menu.home_cards_menu, popupMenu.getMenu());

                popupMenu.getMenu().removeItem(R.id.viewNotce);
                popupMenu.getMenu().removeItem(R.id.viewRecentNotices);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.viewEvent){
                            Intent i = new Intent(getActivity(), ViewEventActivity.class);
                            startActivity(i);
                            return true;
                        }
                        else if (item.getItemId() == R.id.viewRecentEvents) {
                            Intent i = new Intent(getActivity(), NoticeEventListActivity.class);
                            i.putExtra("title", "Recent Events");
                            startActivity(i);
                            return true;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        return view;
    }
    private View setMaintenanceCard(View view) {

        return view;
    }

    private View setMaintenanceCardAdm(View view) {

        return view;
    }

    private View setNoticeCard(View view) {

        return view;
    }

    private View setEventCard(View view) {

        return view;
    }


}