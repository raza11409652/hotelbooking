/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.flatsondemand.user.R;
import com.flatsondemand.user.adapter.TimeSlotAdapter;
import com.flatsondemand.user.listener.TimeSlotListener;
import com.flatsondemand.user.model.Booking;
import com.flatsondemand.user.model.TimeSlot;
import com.flatsondemand.user.utils.Server;
import com.flatsondemand.user.utils.ShowAlert;
import com.flatsondemand.user.utils.ShowProgress;
import com.flatsondemand.user.utils.ShowSnackBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HoseKeepingSchedule extends AppCompatActivity implements TimeSlotListener, PaymentResultListener {
    Toolbar toolbar;
    TextView selectDate, selectTime, bill, charge, total, propertyName, roomNumber;

    BottomSheetDialog calenderSheet, timeSheet;
    CalendarView date;
    Date Today = new Date();
    String dateSelected = null, timeSelected = null;
    Calendar calendar = Calendar.getInstance();
    ShimmerFrameLayout loader_layout;
    ConstraintLayout parent;
    String TAG = HoseKeepingSchedule.class.getSimpleName();
    RecyclerView timeSlotListHolder;
    ArrayList<TimeSlot> list;
    ShowAlert showAlert;
    Booking booking;
    String userUid = null;
    double billAmount = 100.00;
    double chargePercent = 2.5;
    double chargeAmount = 0.0, totalAmount = 0.0;
    ShowProgress progress;

    Button payNow;
    Checkout checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_hose_keeping_schedule);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        /**
         *
         * Razor pay init
         */
        checkout = new Checkout();
        checkout.setKeyID(getString(R.string.RZR_PAY_TOKEN));
        Checkout.preload(getApplicationContext());
        propertyName = findViewById(R.id.property_name);
        roomNumber = findViewById(R.id.room_number);


        selectDate = findViewById(R.id.select_date);
        selectTime = findViewById(R.id.select_time);
        parent = findViewById(R.id.parent);
        showAlert = new ShowAlert(this, this);
        bill = findViewById(R.id.bill);
        charge = findViewById(R.id.charge);
        total = findViewById(R.id.total);

        progress = new ShowProgress(this);
        try {
            booking = (Booking) getIntent().getSerializableExtra("booking");
            propertyName.setText(booking.getPropertyName());
            roomNumber.setText(booking.getRoomNumber());
            chargeAmount = billAmount * (chargePercent) / 100;
            totalAmount = billAmount + chargeAmount;
        } catch (Exception e) {
            e.printStackTrace();
        }

        bill.setText("Rs." + billAmount);
        charge.setText("Rs." + chargeAmount);
        total.setText("Rs." + totalAmount);

        userUid = "nLUlFKXtMcaryhnqBKSg2iJ7AeA3";
/**
 * Calender bottom Sheet
 */

        calenderSheet = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_calender, null);
        calenderSheet.setContentView(view);
        date = view.findViewById(R.id.date_selector);
        date.setMinDate(Today.getTime());
        /**
         * Time selector
         */
        timeSheet = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View timPicker = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_time_select, null, false);
        timeSheet.setContentView(timPicker);
        loader_layout = timeSheet.findViewById(R.id.loader_layout);
        timeSlotListHolder = timeSheet.findViewById(R.id.list_item);
        timeSlotListHolder.setHasFixedSize(true);
        payNow = findViewById(R.id.pay_now);


        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSelected = null;
                showBottomSheetTime();
            }
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderSheet.show();
                timeSelected = null;
                date.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        month = month + 1;
                        String Month = null, Day = null;
                        if (month < 10) {
                            Month = "0" + month;
                        } else {
                            Month = String.valueOf(month);
                        }
                        if (dayOfMonth < 10) {
                            Day = "0" + dayOfMonth;
                        } else {
                            Day = String.valueOf(dayOfMonth);
                        }
                        dateSelected = year + "-" + Month + "-" + Day;
                        selectDate.setText(dateSelected);
                        calenderSheet.dismiss();
                        if (dateSelected != null) {
                            showBottomSheetTime();
                        }

                    }
                });
            }
        });

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPayment(dateSelected, timeSelected, booking.getId(), userUid, totalAmount);
            }
        });
    }

    private void initPayment(final String dateSelected, final String timeSelected, final String id, final String userUid, final double totalAmount) {
        if (dateSelected == null) {
            ShowSnackBar.showWithConstraintLayout(getString(R.string.error_date_not_selected), parent);
            return;
        }
        if (timeSelected == null) {
            ShowSnackBar.showWithConstraintLayout(getString(R.string.error_time_not_selected), parent);
            return;
        }
        if (id == null || userUid == null) {
            Log.d(TAG, "initPayment: sjdlfj");
            return;
        }

        final int amountToBePaid = (int) (totalAmount * 100);
        progress.show(getString(R.string.wiat_payment));
        StringRequest razorPayInitToken = new StringRequest(Request.Method.POST, Server.ORDER_GEN_RAZOR_PAY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
//                if (progress.isShowing()) progress.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (error) {
//                        alert.alert("Razorpay payment is not supported at this time please use other one");
                        return;
                    } else {
                        String RAZOR_PAY_TOKEN = jsonObject.getString("RAZORPAY_ORDER_TOKEN");
                        Log.d(TAG, "onResponse: " + RAZOR_PAY_TOKEN);
                        payWithRazorPay(String.valueOf(amountToBePaid), timeSelected, dateSelected, RAZOR_PAY_TOKEN);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("orderId", "FOD_PAY_" + timeSelected + dateSelected);
                map.put("amount", String.valueOf(amountToBePaid));
                return map;
//                return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(razorPayInitToken);


    }

    private void payWithRazorPay(String amountToBePaid, String timeSelected, String dateSelected,
                                 String razor_pay_token) {

        final Activity activity = this;
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", "Flatsondemand");
            jsonObject.put("description", "FOD_PAY_" + timeSelected + dateSelected);
            jsonObject.put("order_id", razor_pay_token);
            jsonObject.put("currency", "INR");
            jsonObject.put("amount", amountToBePaid);
            jsonObject.put("prefill.name", "Khalid");
            jsonObject.put("prefill.contact", "+919835555982");
            checkout.open(activity, jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showBottomSheetTime() {
        if (dateSelected == null) {
            ShowSnackBar.showWithConstraintLayout(getString(R.string.error_date_not_selected), parent);
        } else {
            timeSheet.show();
            loader_layout.startShimmerAnimation();
            getFreeTimeSlot(dateSelected);
        }
    }

    private void getFreeTimeSlot(final String dateSelected) {
        list = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.FREE_TIME_SLOT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loader_layout.stopShimmerAnimation();
                loader_layout.setVisibility(View.GONE);
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        JSONArray array = jsonObject.getJSONArray("records");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject single = array.getJSONObject(i);
                            Log.d(TAG, "onResponse: " + single);
                            String id = single.getString("id");
                            String time = single.getString("timing");
                            boolean status = single.getBoolean("status");
                            TimeSlot slot = new TimeSlot(id, time, status);
                            list.add(slot);
                        }
                        setAdapter(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("auth-token", "TOKEN");
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("date", dateSelected);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void setAdapter(ArrayList<TimeSlot> list) {
        timeSlotListHolder.setVisibility(View.VISIBLE);
        TimeSlotAdapter adapter = new TimeSlotAdapter(list, this, this);
        timeSlotListHolder.setAdapter(adapter);
        timeSlotListHolder.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemClick(TimeSlot slot) {
        if (slot.isStatus()) {
            if (timeSheet != null) timeSheet.dismiss();
            selectTime.setText(slot.getTime());
            timeSelected = slot.getId();
        } else {
            showAlert.alert(slot.getTime() + " ,  " + getString(R.string.error_time_slot_free));
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d(TAG, "onPaymentSuccess: " + s);
        /**
         * CREATE house keeping booking on the server
         */
        String bookingId = booking.getId();
        String user = userUid;
        String selectedTime = timeSelected;
        String selectedDate = dateSelected;
        String amount = String.valueOf(totalAmount);
        String paymentMode = s;

        createHouseKeeping(bookingId, user, selectedDate, selectedTime, amount, paymentMode);


    }

    private void createHouseKeeping(final String bookingId, final String user, final String selectedDate, final String selectedTime, final String amount, final String paymentMode) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.CREATE_HOUSE_KEEPING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    if (progress.isShowing()) progress.dismiss();
                    JSONObject object = new JSONObject(response);
                    boolean error = object.getBoolean("error");
                    if (!error) {
                        String msg = object.getString("msg");
                        showAlert.alertBackToHome(msg);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progress.isShowing()) progress.dismiss();
                Log.d(TAG, "onErrorResponse: " + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("auth-token", "TOKEN");
                map.put("client-token", userUid);
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("date", selectedDate);
                map.put("time", selectedTime);
                map.put("booking", bookingId);
                map.put("user", user);
                map.put("paymentMode", paymentMode);
                map.put("amount", amount);
                return map;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onPaymentError(int i, String s) {
        if (progress.isShowing()) progress.dismiss();
        ShowSnackBar.showWithConstraintLayout("" + s, parent);

    }
}
