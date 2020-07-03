/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
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
import com.flatsondemand.user.adapter.PaymentsAdapterDate;
import com.flatsondemand.user.model.Payments;
import com.flatsondemand.user.model.PaymentsDate;
import com.flatsondemand.user.utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PaymentHistoryForBooking extends AppCompatActivity  {
    ImageButton homeBack, filter;
    String TAG = PaymentHistoryForBooking.class.getSimpleName();
    ShimmerFrameLayout loader;
    ArrayList<PaymentsDate> arrayList = new ArrayList<>();
    String bookingId = null;
    RecyclerView paymentsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history_for_booking);
        /**
         * Init views
         */
        try {
            bookingId = getIntent().getStringExtra("bookingId");
        } catch (Exception e) {

        }
        homeBack = findViewById(R.id.back_to_home);
        filter = findViewById(R.id.filter);
        loader = findViewById(R.id.loader_layout);
        paymentsList = findViewById(R.id.payments_list);
        loader.startShimmerAnimation();
        homeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }


        });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Filter Button click");
            }
        });

        getPaymentHistory();


    }

    private void getPaymentHistory() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.PAYMENTS_BY_BOOKING_DATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                loader.stopShimmerAnimation();
                loader.setVisibility(View.GONE);
                try {
                    JSONObject object = new JSONObject(response);
                    boolean error = object.getBoolean("error");
                    if (!error) {
                        JSONArray records = object.getJSONArray("records");
                        for (int i = 0; i < records.length(); i++) {
                            JSONObject recordSingle = records.getJSONObject(i);
                            ArrayList<Payments> payments = new ArrayList<>();
                            String date = recordSingle.getString("date");
                            JSONArray array = recordSingle.getJSONArray("payments");
                            for (int j = 0; j < array.length(); j++) {
                                JSONObject single = array.getJSONObject(i);
                                String id = single.getString("booking_pay_id");
                                String time = single.getString("booking_pay_time");
                                String startDate = single.getString("booking_pay_startdate");
                                String endDate = single.getString("booking_pay_enddate");
                                String electricity = single.getString("booking_pay_elec");
                                String electricityRef = single.getString("booking_pay_elect_ref");
                                String rent = single.getString("booking_pay_rent");
                                String others = single.getString("booking_pay_others");
                                int stat = single.getInt("booking_pay_is_paid");
                                boolean isPaid = false;
                                if (stat == 1) {
                                    isPaid = true;
                                } else {
                                    isPaid = false;
                                }
                                String status = single.getString("booking_pay_status");

                                String submittedOn = single.getString("booking_pay_submitted_on");
                                String paymode = single.getString("booking_pay_mode");
                                String paymodeRef = single.getString("booking_pay_mode_ref");
                                String room = single.getString("booking_pay_room");
                                String total = null;
                                String fine = null;
                                String dueDate = null;
                                Payments payment = new Payments(id, time, startDate, endDate, electricity, electricityRef, rent, others, status, submittedOn, paymode, paymodeRef, total, room, dueDate, fine, isPaid);
                                payments.add(payment);
                            }

                            PaymentsDate paymentsDate = new PaymentsDate(date, payments);
                            arrayList.add(paymentsDate);

                        }
                        setAdapter(arrayList);
                    } else {

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                return super.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("booking", bookingId);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setAdapter(ArrayList<PaymentsDate> arrayList) {
        PaymentsAdapterDate adapterDate = new PaymentsAdapterDate(this, arrayList);
        paymentsList.setVisibility(View.VISIBLE);
        paymentsList.setAdapter(adapterDate);
        paymentsList.setLayoutManager(new LinearLayoutManager(this));


    }


}
