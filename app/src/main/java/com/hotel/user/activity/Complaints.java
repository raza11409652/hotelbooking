/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import com.hotel.user.R;
import com.hotel.user.adapter.CategoryAdapter;
import com.hotel.user.adapter.SubCategoryAdapter;
import com.hotel.user.listener.CategoryClickListener;
import com.hotel.user.listener.SubCategoryItemListener;
import com.hotel.user.model.Booking;
import com.hotel.user.model.ComplaintsCategory;
import com.hotel.user.model.ComplaintsSubCategory;
import com.hotel.user.utils.Server;
import com.hotel.user.utils.ShowSnackBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Complaints extends AppCompatActivity implements CategoryClickListener, SubCategoryItemListener {
    Toolbar toolbar;
    Booking booking;
    ArrayList<ComplaintsCategory> arrayList;
    ArrayList<ComplaintsSubCategory> subCategories;
    CategoryAdapter adapter;
    TextView propertyName, roomNumber, categorySelector, subCategorySelector;
    String TAG = Complaints.class.getSimpleName();
    String categorySelectorId = null, subCategorySelectorId = null;
    BottomSheetDialog sheetDialog, subCatSheet;
    CoordinatorLayout parent;
    ImageButton submitComplaints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        /**
         * Init views
         */
        propertyName = findViewById(R.id.property_name);
        roomNumber = findViewById(R.id.room_number);
        parent = findViewById(R.id.parent);

        categorySelector = findViewById(R.id.select_category);
        subCategorySelector = findViewById(R.id.select_sub_category);
        submitComplaints = findViewById(R.id.complaints_submit_button);


        try {
            booking = (Booking) getIntent().getSerializableExtra("booking");
            propertyName.setText(booking.getPropertyName());
            roomNumber.setText(booking.getRoomNumber());
//            chargeAmount = billAmount * (chargePercent) / 100;
//            totalAmount = billAmount + chargeAmount;
        } catch (Exception e) {
            e.printStackTrace();
        }


        categorySelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Category selector click");
                openCategoryBottomSheet();
            }
        });
        subCategorySelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Sub category");
                if (categorySelectorId == null) {
                    ShowSnackBar.showWithCoordinateLayout(getString(R.string.error_category_required), parent);
                    return;
                }
                openSubCategoryBottomSheet(categorySelectorId);
            }
        });


        submitComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categorySelectorId == null) {
                    ShowSnackBar.showWithCoordinateLayout(getString(R.string.error_category_required), parent);

                    return;
                }
                if (subCategorySelectorId == null) {
                    ShowSnackBar.showWithCoordinateLayout(getString(R.string.error_sub_category_required), parent);
                    return;
                }



            }
        });


    }

    private void openCategoryBottomSheet() {
        sheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(Complaints.this).inflate(R.layout.bottom_sheet_category, null);
        RecyclerView list = view.findViewById(R.id.list);
        ShimmerFrameLayout loader = view.findViewById(R.id.loader);
        loader.startShimmerAnimation();
        list.setHasFixedSize(true);
        fetchCategory(list, loader);
        sheetDialog.setContentView(view);
        sheetDialog.show();

    }

    private void fetchCategory(final RecyclerView list, final ShimmerFrameLayout loader) {
        arrayList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.COMPLAINTS_CAT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loader.stopShimmerAnimation();
                loader.setVisibility(View.GONE);
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if (!error) {
                        JSONArray array = jsonObject.getJSONArray("records");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject single = array.getJSONObject(i);
                            String id = single.getString("complaints_issue_id");
                            String title = single.getString("complaints_issue_topic");
                            ComplaintsCategory category = new ComplaintsCategory(id, title);
                            arrayList.add(category);
                        }
                        setAdapterCategory(list, arrayList);
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

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setAdapterCategory(RecyclerView list, ArrayList<ComplaintsCategory> arrayList) {
        list.setHasFixedSize(true);
        list.setVisibility(View.VISIBLE);
        adapter = new CategoryAdapter(arrayList, this, this);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(this));

    }


    private void openSubCategoryBottomSheet(String category) {
        if (category == null) {
            return;
        }
        subCatSheet = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        View view = LayoutInflater.from(Complaints.this).inflate(R.layout.bottom_sheet_category, null);
        RecyclerView list = view.findViewById(R.id.list);
        ShimmerFrameLayout loader = view.findViewById(R.id.loader);
        loader.startShimmerAnimation();
        list.setHasFixedSize(true);
        fetchSubCategory(list, loader, category);
        subCatSheet.setContentView(view);
        subCatSheet.show();
    }

    private void fetchSubCategory(final RecyclerView list, final ShimmerFrameLayout loader, final String category) {
        subCategories = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.COMPLAINTS_SUB_CAT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loader.stopShimmerAnimation();
                loader.setVisibility(View.GONE);
                try {
                    JSONObject object = new JSONObject(response);
                    boolean error = object.getBoolean("error");
                    if (!error) {
                        JSONArray array = object.getJSONArray("records");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject single = array.getJSONObject(i);
                            String id = single.getString("complaints_sub_issue_id");
                            String title = single.getString("complaints_sub_issue_topic");
                            ComplaintsSubCategory subCategory = new ComplaintsSubCategory(id, title);
                            subCategories.add(subCategory);
                        }
                        setSubCategoryAdapter(subCategories, list);
                    } else {

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
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("category", category);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void setSubCategoryAdapter(ArrayList<ComplaintsSubCategory> subCategories, RecyclerView list) {
        list.setHasFixedSize(true);
        SubCategoryAdapter adapter = new SubCategoryAdapter(this, subCategories, this);
        list.setAdapter(adapter);
        list.setVisibility(View.VISIBLE);
        list.setLayoutManager(new LinearLayoutManager(this));
    }


    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onItemClick(ComplaintsCategory complaintsCategory) {

        categorySelector.setText(complaintsCategory.getTitle());
        categorySelectorId = complaintsCategory.getId();
        subCategorySelectorId = null;
        if (sheetDialog.isShowing()) sheetDialog.dismiss();
        openSubCategoryBottomSheet(categorySelectorId);

    }

    @Override
    public void onListItemClick(ComplaintsSubCategory subCategory) {
        subCategorySelector.setText(subCategory.getTitle());
        if (subCatSheet.isShowing()) subCatSheet.dismiss();
        subCategorySelectorId = subCategory.getId();

    }
}
