/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.hotel.user.adapter.NearByAdapter;
import com.hotel.user.adapter.NearByLocationAdapter;
import com.hotel.user.listener.NearBySelector;
import com.hotel.user.model.NearByLocation;
import com.hotel.user.model.Nearby;
import com.hotel.user.model.Property;
import com.hotel.user.utils.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PropertyView extends AppCompatActivity implements NearBySelector {
    Property property;
    ImageView imageView;
    Toolbar toolbar;
    RecyclerView nearByList, nearByLocations;
    NearByAdapter adapter;
    CalendarView date;
    Calendar calendar = Calendar.getInstance();
    Date Today = new Date();
    String dateSelected = null, dateSelectedEnd = null;
    NearByLocationAdapter nearByLocationAdapter;
    ArrayList<Nearby> arrayList = new ArrayList<>();
    String TAG = PropertyView.class.getSimpleName();
    String lat, lng;
    BottomSheetDialog sheetDialogStartDate, sheetDialogEndDate;
    TextView startDate, endDate;
    FirebaseAuth auth;
    FirebaseUser user;
    String userUid = null;
    ProgressDialog progressDialog;
    Button bookingInit;
    TextView propertyName, propertyPrice, propertyAddress, propertyType;
    ArrayList<NearByLocation> locations = new ArrayList<>();
    String[] nearBy = {"Hospital", "Mall", "Bus stand", "Railway Station", "Restaurant"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_view);
        toolbar = findViewById(R.id.toolbar);
        imageView = findViewById(R.id.imageView);
        propertyName = findViewById(R.id.propertyName);
        propertyPrice = findViewById(R.id.propertyPrice);
        propertyAddress = findViewById(R.id.propertyAdd);
        progressDialog = new ProgressDialog(this);
        startDate = findViewById(R.id.select_start_date);
        endDate = findViewById(R.id.select_end_date);
        bookingInit = findViewById(R.id.booking);

        try {
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            userUid = user.getUid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEndDateSelector();
            }
        });


        bookingInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateSelected == null || dateSelectedEnd == null) {
                    Toast.makeText(getApplicationContext(), "Please select date fist", Toast.LENGTH_SHORT).show();
                    return;
                }

                /**
                 * Start booking
                 */
                Log.d(TAG, "onClick: Booking for" + userUid);
                Log.d(TAG, "onClick: Booking property " + property.getId());
                Log.d(TAG, "onClick: start date " + dateSelected);
                Log.d(TAG, "onClick: end date" + dateSelectedEnd);
                progressDialog.setMessage("Please wait");
                progressDialog.show();
                createBooking(userUid, property.getId(), dateSelected, dateSelectedEnd, user.getPhoneNumber());


            }
        });

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartDateSelector();
            }
        });
        propertyType = findViewById(R.id.type);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        try {
            property = (Property) getIntent().getSerializableExtra("property");
            /**
             *  booking = (Booking) getIntent().getSerializableExtra("booking");
             */
            Log.d("TAG PROPERTY VIEW", "onCreate: " + property.getAddress() + " , " + property.getType());
            toolbar.setTitle(property.getName());

            propertyName.setText(property.getName());
            propertyPrice.setText(property.getPrice() + " /Day");
            propertyAddress.setText(property.getAddress());
            propertyType.setText(property.getType());
            lat = property.getLat();
            lng = property.getLng();
        } catch (Exception e) {
            e.printStackTrace();
        }
        nearByList = findViewById(R.id.nearby_selector);
        nearByLocations = findViewById(R.id.location_list);

        for (int i = 0; i < nearBy.length; i++) {
            boolean s = false;
            if (i == 0) {
                s = true;
            }
            Nearby near = new Nearby(nearBy[i], s);
            arrayList.add(near);
        }

        nearByList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new NearByAdapter(arrayList, this, this);
        nearByList.setAdapter(adapter);
        Picasso.get().load(property.getImage()).placeholder(R.drawable.single_property_fod).error(R.drawable.single_property_fod).into(imageView);


        fetchNearByData(nearBy[0]);
    }

    private void createBooking(final String userUid, final String id, final String dateSelected, final String dateSelectedEnd, final String phoneNumber) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.CREATE_BOOKING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d(TAG, "onResponse: " + response);
                Toast.makeText(getApplicationContext() , response , Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d(TAG, "onErrorResponse: " + error.getMessage());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("auth-token", userUid);
                map.put("client-token", userUid);
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("mobile", phoneNumber);
                map.put("property", id);
                map.put("start", dateSelected);
                map.put("end", dateSelectedEnd);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void openEndDateSelector() {
        if (dateSelected == null) return;
        sheetDialogStartDate = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_calender, null);
        sheetDialogStartDate.setContentView(view);
        date = view.findViewById(R.id.date_selector);
        date.setMinDate(Today.getTime());

        sheetDialogStartDate.show();

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
                dateSelectedEnd = year + "-" + Month + "-" + Day;
                endDate.setText(dateSelectedEnd);
                sheetDialogStartDate.dismiss();
//                if (dateSelected != null) {
//                    showBottomSheetTime();
//                }

            }
        });
    }

    private void openStartDateSelector() {
        sheetDialogStartDate = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_calender, null);
        sheetDialogStartDate.setContentView(view);
        date = view.findViewById(R.id.date_selector);
        date.setMinDate(Today.getTime());

        sheetDialogStartDate.show();

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
                startDate.setText(dateSelected);
                sheetDialogStartDate.dismiss();
//                if (dateSelected != null) {
//                    showBottomSheetTime();
//                }

            }
        });
    }

    private void fetchNearByData(final String s) {
        locations = new ArrayList<>();
        String url = Server.HERE_MAP_PLACE;
        final String location = lat + "," + lng;
        url = url + "?at=" + location + "&limit=5&in=countryCode:IND&q=" + s + "&apiKey=" + getString(R.string.here_api_key);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("items");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject single = array.getJSONObject(i);
                        String Title = single.getString("title");
                        String id = single.getString("id");
                        JSONObject address = single.getJSONObject("address");
                        String label = address.getString("label");
                        String countryCode = address.getString("countryCode");
                        String distance = single.getString("distance");
                        label = label + " , " + countryCode;
                        NearByLocation nearByLocation = new NearByLocation(id, Title, label, distance);
                        locations.add(nearByLocation);

                    }
                    setAdapter(locations);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setAdapter(ArrayList<NearByLocation> locations) {
        nearByLocationAdapter = new NearByLocationAdapter(locations, this);
        nearByLocations.setAdapter(nearByLocationAdapter);
        nearByLocations.setLayoutManager(new LinearLayoutManager(this));
        nearByLocations.setHasFixedSize(true);
    }

    @Override
    public void onItemClick(int position, String title, ArrayList<Nearby> list) {
        for (int i = 0; i < list.size(); i++) {
            String t = list.get(i).getTitle();

            list.remove(i);
            list.add(i, new Nearby(t, false));
        }
        arrayList = list;
        arrayList.remove(position);
        arrayList.add(position, new Nearby(title, true));
        adapter.notifyDataSetChanged();

        Log.d(TAG, "onItemClick: " + title);
        fetchNearByData(title);
    }


}