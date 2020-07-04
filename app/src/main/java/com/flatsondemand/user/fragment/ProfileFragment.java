/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flatsondemand.user.R;
import com.flatsondemand.user.activity.OpenWebView;
import com.flatsondemand.user.utils.Server;
import com.flatsondemand.user.utils.ShowAlert;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    Button btnSupport;
    ShowAlert alert;
    String TAG = ProfileFragment.class.getSimpleName();
    TextView houseKeeping, complaintsHistory, support, privacyPolicy, termsConditions, logout;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alert = new ShowAlert(getActivity(), getContext());
        houseKeeping = view.findViewById(R.id.house_keeping_btn);
        complaintsHistory = view.findViewById(R.id.complaints_history);
        support = view.findViewById(R.id.support);
        privacyPolicy = view.findViewById(R.id.privacy_policy);
        termsConditions = view.findViewById(R.id.terms_condition);
        logout = view.findViewById(R.id.logout);

        houseKeeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: House keeping support");
            }
        });
        complaintsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Complaints history ");
            }
        });
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView(Server.CHAT);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.alertLogout();
            }
        });

    }

    private void openWebView(String url) {
        Intent webview = new Intent(getContext(), OpenWebView.class);
        webview.putExtra("url", url);
        startActivity(webview);

        webview.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
