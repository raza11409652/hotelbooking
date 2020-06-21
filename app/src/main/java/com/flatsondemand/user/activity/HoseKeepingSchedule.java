/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.flatsondemand.user.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class HoseKeepingSchedule extends AppCompatActivity {
    Toolbar toolbar;
    TextView selectDate, selectTime;

    BottomSheetDialog calenderSheet;
    CalendarView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hose_keeping_schedule);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        selectDate = findViewById(R.id.select_date);
        selectTime = findViewById(R.id.select_time);
/**
 * Calender bottom Sheet
 */
        calenderSheet = new BottomSheetDialog(HoseKeepingSchedule.this, R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_calender, null);
        calenderSheet.setContentView(view);
        date = view.findViewById(R.id.date_selector);

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderSheet.show();
                date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
