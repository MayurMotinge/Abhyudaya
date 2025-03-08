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
import android.widget.PopupMenu;
import android.widget.TextView;

public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);




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