/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
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
import com.facebook.shimmer.ShimmerFrameLayout;
import com.flatsondemand.user.R;
import com.flatsondemand.user.adapter.PropertySearchAdapter;
import com.flatsondemand.user.model.Property;
import com.flatsondemand.user.utils.Constant;
import com.flatsondemand.user.utils.Server;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PropertySearch extends AppCompatActivity {
    Toolbar toolbar;
    String TAG = PropertySearch.class.getSimpleName();
    String location = null;
    ShimmerFrameLayout loader;
    FirebaseAuth auth;
    FirebaseUser user;
    String userId;
    ArrayList<Property> properties = new ArrayList<>();
    RecyclerView list;
    PropertySearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_search);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        try {
            Log.d(TAG, "onCreate: " + Constant.CURRENT_LOCATION);
            location = Constant.CURRENT_LOCATION;
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            userId = user.getUid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setTitle(location.toUpperCase());

        loader = findViewById(R.id.loader);
        list = findViewById(R.id.list);
        loader.startShimmerAnimation();


        if (location != null) {
            searchProperty(location);
        }


    }

    private void searchProperty(final String location) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.SEARCH_PROPERTY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);

                try {

                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        JSONArray array = jsonObject.getJSONArray("records");
                        for (int i = 0; i < array.length(); i++) {
//                          Log.d(TAG, "onResponse: " + array.getJSONObject(i));
                            JSONObject single = array.getJSONObject(i);
                            String id = single.getString("id");
                            String name = single.getString("name");
                            String price = single.getString("price");
                            String image = single.getString("image");
                            int numRoom = single.getInt("room");
                            boolean mapped = single.getBoolean("mapped");
                            Property property = new Property(id, name, price, image, numRoom, mapped);

                            properties.add(property);

                        }
                        setAdapter(properties);


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
                HashMap<String, String> map = new HashMap<>();
                map.put("auth-token", userId);
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("location", location);
                return map;
            }
        };
        RequestQueue d = Volley.newRequestQueue(this);
        d.add(stringRequest);
    }

    private void setAdapter(ArrayList<Property> properties) {
        loader.stopShimmerAnimation();
        loader.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
        adapter = new PropertySearchAdapter(properties, this);
        list.setHasFixedSize(true);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));


    }
}