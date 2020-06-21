/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.flatsondemand.user.R;
import com.flatsondemand.user.model.Booking;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class SingleFod extends AppCompatActivity {
    Booking booking;
    String TAG = SingleFod.class.getSimpleName();
    CoordinatorLayout parent;
    ImageButton close;
    BottomSheetBehavior sheetBehavior;
    ConstraintLayout bottomSheetLayout;
    TextView contactHelpline;
    RelativeLayout houseKeeping;

//    public SingleFod(Booking booking) {
//        this.booking = booking;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fod);
        booking = (Booking) getIntent().getSerializableExtra("booking");
//        Log.d(TAG, "onCreate: " + booking);
        /**
         * INit views
         */

        bottomSheetLayout = findViewById(R.id.botoomSheetSingleFod);
        parent = findViewById(R.id.parent);
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        sheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        contactHelpline = findViewById(R.id.contact_helpline);
        houseKeeping = findViewById(R.id.house_keeping);
        houseKeeping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hoseKeepingService = new Intent(getApplicationContext(), HoseKeepingSchedule.class);
                hoseKeepingService.putExtra("booking", booking);
                startActivity(hoseKeepingService);

            }
        });
        contactHelpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: CLICK to make call ");
            }
        });
//        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                switch (newState) {
//                    case BottomSheetBehavior.STATE_EXPANDED:
//                        sheetBehavior.setPeekHeight(500);
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });


    }
}
