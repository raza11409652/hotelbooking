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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.flatsondemand.user.R;
import com.flatsondemand.user.activity.SingleFod;
import com.flatsondemand.user.adapter.MyFodAdapter;
import com.flatsondemand.user.listener.OnMyFodClick;
import com.flatsondemand.user.model.Booking;
import com.flatsondemand.user.utils.Server;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnMyFodClick {
    FloatingActionButton bookingsShortcut;
    String userId;
    String TAG = HomeFragment.class.getSimpleName();
    MyFodAdapter adapter;
    ArrayList<Booking> list;
    BottomSheetDialog sheetDialog;
    ShimmerFrameLayout loader;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        userId = "";
        view = inflater.inflate(R.layout.fragment_home, container, false);
        bookingsShortcut = view.findViewById(R.id.home_booking_shortcut);
        bookingsShortcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet(userId);
            }
        });
        return view;
    }

    private void openBottomSheet(String userId) {
        sheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.booking_shortcut_bottom_sheet, null);
        sheetDialog.setContentView(view);
        loader = view.findViewById(R.id.loader_layout);
        loader.startShimmerAnimation();
        RecyclerView listHolder = view.findViewById(R.id.list_my_fod);
        fetchActiveBooking(listHolder);
        sheetDialog.show();

    }

    private void fetchActiveBooking(final RecyclerView bookingLists) {
        list = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.ACTIVE_BOOKING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d(TAG, "onResponse: " + response);
//                loader.stopShimmerAnimation();
                loader.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean error = jsonObject.getBoolean("error");
                    if (error) {
//                        noBookingFound.setVisibility(View.VISIBLE);
                    } else {
                        JSONArray array = jsonObject.getJSONArray("records");
//                        Log.d(TAG, "onResponse: " + array);
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
                                String image = single.getString("property_cover_image");
                                Booking booking = new Booking(id, number, date, startDate, endDate, status, amount, roomId, room, propertyId, property, image);
                                list.add(booking);
                            }
                            setAdapter(list, bookingLists);
                        } else {
//                            noBookingFound.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    noBookingFound.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                loader.stopShimmerAnimation();
//                loader.setVisibility(View.GONE);
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

    private void setAdapter(ArrayList<Booking> list, RecyclerView bookingLists) {
        adapter = new MyFodAdapter(list, getContext(), this);
        bookingLists.setAdapter(adapter);
        bookingLists.setVisibility(View.VISIBLE);
        bookingLists.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    @Override
    public void onItemButtonClick(Booking booking) {
        Log.d(TAG, "onItemButtonClick: " + booking.getId());
        if (sheetDialog != null) {
            sheetDialog.dismiss();
        }
        Intent intent = new Intent(getContext(), SingleFod.class);
        intent.putExtra("booking", booking);

        startActivity(intent);

    }
}
