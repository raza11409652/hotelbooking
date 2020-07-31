/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.hotel.user.R;
import com.hotel.user.model.Booking;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class SingleFod extends AppCompatActivity {
    Booking booking;
    Button pendingPayment;
    String TAG = SingleFod.class.getSimpleName();
    CoordinatorLayout parent;
    ImageButton close;
    BottomSheetBehavior sheetBehavior;
    ConstraintLayout bottomSheetLayout;
    TextView contactHelpline, propertyName, roomNumber, status, moveInDate;
    RelativeLayout houseKeeping, complaints;

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
        propertyName = findViewById(R.id.property_name);
        roomNumber = findViewById(R.id.room_number);
        status = findViewById(R.id.status);
        moveInDate = findViewById(R.id.moveInDate);

        try {
            propertyName.setText(booking.getPropertyName());
            roomNumber.setText(booking.getRoomNumber());
            status.setText(booking.getStatus());
            moveInDate.setText("Moved in " + booking.getStartDate());
        } catch (Exception e) {
            e.printStackTrace();
        }

        pendingPayment = findViewById(R.id.pendingPayments);
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
        complaints = findViewById(R.id.complaints);


        complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent complaintsService = new Intent(getApplicationContext(), Complaints.class);
                complaintsService.putExtra("booking", booking);
                startActivity(complaintsService);
            }
        });
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
        pendingPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pendingPaymentService = new Intent(getApplicationContext(), PaymentByBooking.class);
                Bundle bundle = new Bundle();
                bundle.putString("bookingId", booking.getId());
                bundle.putString("bookingNumber", booking.getNumber());
                pendingPaymentService.putExtras(bundle);
                startActivity(pendingPaymentService);
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
