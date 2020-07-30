/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.flatsondemand.user.R;
import com.flatsondemand.user.adapter.NearByAdapter;
import com.flatsondemand.user.adapter.NearByLocationAdapter;
import com.flatsondemand.user.listener.NearBySelector;
import com.flatsondemand.user.model.NearByLocation;
import com.flatsondemand.user.model.Nearby;
import com.flatsondemand.user.model.Property;
import com.flatsondemand.user.utils.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PropertyView extends AppCompatActivity implements NearBySelector {
    Property property;
    ImageView imageView;
    Toolbar toolbar;
    RecyclerView nearByList, nearByLocations;
    NearByAdapter adapter;
    NearByLocationAdapter nearByLocationAdapter;
    ArrayList<Nearby> arrayList = new ArrayList<>();
    String TAG = PropertyView.class.getSimpleName();
    String lat, lng;

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
            propertyPrice.setText(property.getPrice() + " /Month");
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