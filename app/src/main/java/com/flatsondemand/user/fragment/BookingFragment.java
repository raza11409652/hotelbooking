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
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.flatsondemand.user.activity.PaymentByBooking;
import com.flatsondemand.user.adapter.BookingAdapter;
import com.flatsondemand.user.listener.BookingItemListener;
import com.flatsondemand.user.model.Booking;
import com.flatsondemand.user.utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment implements BookingItemListener {
    String TAG = BookingFragment.class.getSimpleName();
    ShimmerFrameLayout loader;
    RecyclerView bookingLists;
    RelativeLayout noBookingFound;
    ArrayList<Booking> list;

    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        loader = view.findViewById(R.id.loader);
        bookingLists = view.findViewById(R.id.bookings);
        noBookingFound = view.findViewById(R.id.no_booking_found);
        bookingLists.setHasFixedSize(true);
        loader.startShimmerAnimation();
        fetchActiveBooking();
        return view;
    }

    private void fetchActiveBooking() {
        list = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.ACTIVE_BOOKING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d(TAG, "onResponse: " + response);
                loader.stopShimmerAnimation();
                loader.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean error = jsonObject.getBoolean("error");
                    if (error) {
                        noBookingFound.setVisibility(View.VISIBLE);
                    } else {
                        JSONArray array = jsonObject.getJSONArray("records");
                        Log.d(TAG, "onResponse: " + array);
                        bookingLists.setVisibility(View.VISIBLE);
                        if (array.length() > 0) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject single = array.getJSONObject(i);
                                String id = single.getString("booking_id");
                                String number = single.getString("booking_number");
                                String date = single.getString("booking_time");
                                String startDate = single.getString("booking_start_date");
                                String endDate = single.getString("booking_end_date");
                                String status = single.getString("booking_status_val");
                                String amount = single.getString("booking_amount");
                                String property = single.getString("property_name");
                                String room = single.getString("room_number");
                                String roomId = single.getString("room_id");
                                String propertyId = single.getString("property_id");
                                Booking booking = new Booking(id, number, date, startDate, endDate, status, amount, roomId, room, propertyId, property);
                                list.add(booking);
                            }
                            setAdapter(list);
                        } else {
                            noBookingFound.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    noBookingFound.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.stopShimmerAnimation();
                loader.setVisibility(View.GONE);
                Log.d(TAG, "onErrorResponse: " + error);
            }
        }) {
            /**
             *
             * @return
             * @throws AuthFailureError
             * TODO :: NEED TO CHANGE CLIENT TOKEN DYNAMIC MODE
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("auth-token", "TOKEN");
                map.put("client-token", "nLUlFKXtMcaryhnqBKSg2iJ7AeA3");
                return map;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void setAdapter(ArrayList<Booking> list) {
        BookingAdapter adapter = new BookingAdapter(list, getContext(), this);
        bookingLists.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        bookingLists.setLayoutManager(linearLayoutManager);
        bookingLists.addItemDecoration(dividerItemDecoration);

    }

    @Override
    public void onPaymentButtonClick(String bookingId, String bookingNumber) {
        Log.d(TAG, "onPaymentButtonClick: " + bookingId);
        Intent payments = new Intent(getContext(), PaymentByBooking.class);
        Bundle bundle = new Bundle();
        bundle.putString("bookingId", bookingId);
        bundle.putString("bookingNumber", bookingNumber);
        payments.putExtras(bundle);
        startActivity(payments);
        payments.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    }
}
