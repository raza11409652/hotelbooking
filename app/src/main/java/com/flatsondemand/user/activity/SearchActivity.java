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
import com.flatsondemand.user.adapter.LocationAdapter;
import com.flatsondemand.user.listener.LocationListener;
import com.flatsondemand.user.model.LocationModel;
import com.flatsondemand.user.utils.Server;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements LocationListener {
    ShimmerFrameLayout loader;
    Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseUser user;
    String userId = null;
    String TAG = SearchActivity.class.getSimpleName();
    ArrayList<LocationModel> arrayList;
    RecyclerView recyclerView ;
    LocationAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.include);
        recyclerView= findViewById(R.id.location_list) ;
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        try {
            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            userId = user.getUid();

        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * TODO ::Init views
         */

        loader = findViewById(R.id.loader_layout);
        loader.startShimmerAnimation();


        if (userId != null) {
            getLocation(userId);
        }
    }

    private void getLocation(final String userId) {
        arrayList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.LOCATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        JSONArray records = jsonObject.getJSONArray("records");
                        for (int i = 0; i < records.length(); i++) {
                            JSONObject single = records.getJSONObject(i);
                            String id = single.getString("location_id");
                            String val = single.getString("location_val");
                            LocationModel locationModel = new LocationModel(id, val);
                            arrayList.add(locationModel);

                        }
                        setAdapter(arrayList);
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
                map.put("auth-token", userId);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setAdapter(ArrayList<LocationModel> arrayList) {
       loader.stopShimmerAnimation();
       loader.setVisibility(View.GONE);
       recyclerView.setVisibility(View.VISIBLE);
       adapter = new LocationAdapter(arrayList   , this ,this) ;
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       recyclerView.setAdapter(adapter) ;

    }

    @Override
    public void onItemClick(LocationModel locationModel) {
        Log.d(TAG, "onItemClick: "+locationModel.getName());
    }
}