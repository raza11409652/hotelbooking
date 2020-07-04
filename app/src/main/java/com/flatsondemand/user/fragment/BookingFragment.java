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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flatsondemand.user.R;
import com.flatsondemand.user.activity.PaymentHistoryForBooking;
import com.flatsondemand.user.adapter.BookingAdapter;
import com.flatsondemand.user.listener.BookingItemListener;
import com.flatsondemand.user.model.Booking;
import com.flatsondemand.user.utils.Server;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ArrayList<Booking> list = new ArrayList<>();
    RelativeLayout noBookingFound;
    RecyclerView bookingList;


    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        if (user != null) {

        } else {
            Log.d(TAG, "onCreateView: user is not login");
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bookingList = view.findViewById(R.id.booking_list);
        noBookingFound = view.findViewById(R.id.no_booking_found);
        bookingList.setHasFixedSize(true);
    }


    private void fetchActiveBooking(final String userUid) {
        list = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.ACTIVE_BOOKING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean error = jsonObject.getBoolean("error");
                    if (error) {
                        Log.d(TAG, "onResponse: " + response);
                        noBookingFound.setVisibility(View.VISIBLE);
                    } else {
                        JSONArray array = jsonObject.getJSONArray("records");
                        Log.d(TAG, "onResponse: " + array);
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
                                String propertyCoverImage = single.getString("property_cover_image");
                                Booking booking = new Booking(id, number, date, startDate, endDate, status, amount, roomId, room, propertyId, property, propertyCoverImage);
                                list.add(booking);
                            }
                            setAdapter(list);
                        } else {
                            Log.d(TAG, "onResponse: no booking found");
                            noBookingFound.setVisibility(View.VISIBLE);
                        }
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
            /**
             *
             * @return
             * @throws AuthFailureError
             * TODO :: NEED TO CHANGE CLIENT TOKEN DYNAMIC MODE
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("auth-token", userUid);
                map.put("client-token", userUid);
                return map;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }

    private void setAdapter(ArrayList<Booking> list) {
        BookingAdapter adapter = new BookingAdapter(list, getContext(), this);
        bookingList.setAdapter(adapter);
        bookingList.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), linearLayoutManager.getOrientation());
        bookingList.setLayoutManager(linearLayoutManager);
//        bookingLists.addItemDecoration(dividerItemDecoration);

    }

    @Override
    public void onPaymentButtonClick(String bookingId, String bookingNumber) {
//        Log.d(TAG, "onPaymentButtonClick: " + bookingId);
//        Intent payments = new Intent(getContext(), PaymentByBooking.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("bookingId", bookingId);
//        bundle.putString("bookingNumber", bookingNumber);
//        payments.putExtras(bundle);
//        startActivity(payments);
//        payments.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    }

    @Override
    public void onPaymentViewClick(Booking booking) {

        Log.d(TAG, "onPaymentViewClick: " + booking.getNumber());
        Intent pendingPaymentService = new Intent(getContext(), PaymentHistoryForBooking.class);
        Bundle bundle = new Bundle();
        bundle.putString("bookingId", booking.getId());
        bundle.putString("bookingNumber", booking.getNumber());
        pendingPaymentService.putExtras(bundle);
        startActivity(pendingPaymentService);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Fragment Resume");
        Log.d(TAG, "onResume: " + user.getUid());
        fetchActiveBooking(user.getUid());
    }
}
