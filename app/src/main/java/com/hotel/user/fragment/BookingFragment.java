/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hotel.user.R;
import com.hotel.user.adapter.BookingAdapter;
import com.hotel.user.listener.BookingItemListener;
import com.hotel.user.model.Booking;
import com.hotel.user.utils.Server;
import com.hotel.user.utils.ShowProgress;

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
    ShowProgress showProgress;
    BottomSheetDialog sheetDialog;

    public BookingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        showProgress = new ShowProgress(getContext());
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
                    // Toast.makeText(getContext() , ""+response , Toast.LENGTH_SHORT).show();
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
//                                String status = single.getString("booking_status_val");
                                String amount = single.getString("booking_amount");
//                                String property = single.getString("property_name");
//                                String room = single.getString("room_number");
//                                String roomId = single.getString("room_id");
                                String propertyId = single.getString("booking_property");
                                int isCheck = single.getInt("booking_is_checked");
                                boolean status = false;
                                if (isCheck == 1) {
                                    status = true;
                                } else {

                                    status = false;
                                }
                                //String propertyCoverImage = single.getString("property_cover_image");
                                Booking booking = new Booking(id, number, date, startDate, endDate, amount, propertyId, status);
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
    public void onCheckInClick(final Booking booking) {

        sheetDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.otp_verify_chekin, null, false);
        sheetDialog.setContentView(view);
        Button verify;
        final EditText otp;
        otp = view.findViewById(R.id.otp_enter);
        verify = view.findViewById(R.id.checkIn);


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = null;
                str = otp.getText().toString();
                if (TextUtils.isEmpty(str)) {
                    Toast.makeText(getContext(), "Please enter OTP", Toast.LENGTH_SHORT).show();
                    return;
                }
                verify(str, booking.getId());
            }
        });
        sheetDialog.show();
    }

    private void verify(final String str, final String id) {
        showProgress.show("Please wait for verification ...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.VERIFY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showProgress.dismiss();
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    String msg = jsonObject.getString("msg");
                    if (error) {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    } else {
                        sheetDialog.dismiss();
//                        fetchActiveBooking(user.getUid());
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
                HashMap<String, String> hash = new HashMap<>();
                hash.put("booking", id);
                hash.put("otp", str);

                return hash;
            }
        };


        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Fragment Resume");
        Log.d(TAG, "onResume: " + user.getUid());
        fetchActiveBooking(user.getUid());
    }
}
