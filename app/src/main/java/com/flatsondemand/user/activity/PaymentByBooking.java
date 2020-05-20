/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.flatsondemand.user.adapter.PaymentAdapter;
import com.flatsondemand.user.adapter.UpiAdapter;
import com.flatsondemand.user.listener.PaymentClickListener;
import com.flatsondemand.user.listener.UpiListener;
import com.flatsondemand.user.model.Payments;
import com.flatsondemand.user.utils.Server;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentByBooking extends AppCompatActivity implements PaymentClickListener, UpiListener {
    Bundle data;
    String TAG = PaymentByBooking.class.getSimpleName();
    Toolbar toolbar;
    String booking = null, bookingNumber = null;
    ShimmerFrameLayout loader;
    RelativeLayout noPaymentFound;
    ArrayList<Payments> payments;
    RecyclerView paymentsHolder;
    ConstraintLayout parent;
    BottomSheetDialog sheetDialog;
    RecyclerView upiAppListHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_by_booking);
        data = getIntent().getExtras();
        booking = data.getString("bookingId");
        bookingNumber = data.getString("bookingNumber");
        Log.d(TAG, "onCreate: " + data);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        paymentsHolder = findViewById(R.id.payments);
        paymentsHolder.setHasFixedSize(true);
        parent = findViewById(R.id.parent);

//        setTitle(bookingNumber);
        loader = findViewById(R.id.loader);
        loader.startShimmerAnimation();
        noPaymentFound = findViewById(R.id.no_payment_found);
        fetchPayments();

    }

    private void fetchPayments() {
        payments = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.PAYMENTS_BY_BOOKING, new Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: " + response);
                loader.stopShimmerAnimation();
                loader.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean error = jsonObject.getBoolean("error");
                    if (error) {

                    } else {
                        JSONArray array = jsonObject.getJSONArray("records");
                        if (array.length() < 1) {
                            noPaymentFound.setVisibility(View.VISIBLE);
                        } else {
//                            parent.setBackgroundColor(R.color.shimeerback);
                            paymentsHolder.setVisibility(View.VISIBLE);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject single = array.getJSONObject(i);
                                String id = single.getString("id");
                                String time = single.getString("time");
                                String startDate = single.getString("startdate");
                                String endDate = single.getString("enddate");
                                String electricity = single.getString("electricity");
                                String electricityRef = single.getString("elecref");
                                String rent = single.getString("rent");
                                String others = single.getString("others");
                                String status = single.getString("status");
                                boolean isPaid = single.getBoolean("ispaid");
                                String submittedOn = single.getString("submittedOn");
                                String paymode = single.getString("paymode");
                                String paymodeRef = single.getString("paymodeRef");
                                String room = single.getString("room");
                                String total = single.getString("total");
                                String fine = single.getString("fine");
                                String dueDate = single.getString("dueDate");
                                Payments payment = new Payments(id, time, startDate, endDate, electricity, electricityRef, rent, others, status, submittedOn, paymode, paymodeRef, total, room, dueDate, fine, isPaid);
                                payments.add(payment);

                            }
                            setAdapter(payments);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: " + error);
                loader.stopShimmerAnimation();
                loader.setVisibility(View.GONE);
                noPaymentFound.setVisibility(View.VISIBLE);

            }
        }) {
            /**
             * TODO ::
             * update dynamic client token and auth token
             * @return
             * @throws AuthFailureError
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("auth-token", "TOKEN");
                map.put("client-token", "nLUlFKXtMcaryhnqBKSg2iJ7AeA3");
                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("booking", booking);
                return map;
            }


        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    private void setAdapter(ArrayList<Payments> payments) {
        PaymentAdapter adapter = new PaymentAdapter(payments, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        paymentsHolder.setLayoutManager(linearLayoutManager);
        paymentsHolder.addItemDecoration(dividerItemDecoration);

        paymentsHolder.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        fetchPayments();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: App is restarted");
        fetchPayments();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d(TAG, "onOptionsItemSelected: HELLO");
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                this.finish();
            } else {
                getFragmentManager().popBackStack();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPaymentReceive(String amount, String bookingId) {
        //        Log.d(TAG, "onPaymentReceive: " + bookingId + amount);
        sheetDialog = new BottomSheetDialog(this, R.style.customLoader);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_payment_options, null);
        sheetDialog.setContentView(view);
        upiAppListHolder = view.findViewById(R.id.upi_payments_list);
        sheetDialog.setTitle("Hello");
        /**
         * UPI INTENT BUILDER
         */

        Uri.Builder payUri = new Uri.Builder();
        payUri.scheme("upi").authority("pay");
        payUri.appendQueryParameter("pa", "9815963210@paytm");
        payUri.appendQueryParameter("pn", "Vendor Name");
        Long tsLong = System.currentTimeMillis() / 1000;
        String transaction_ref_id = tsLong.toString();
        payUri.appendQueryParameter("tid", transaction_ref_id);
        payUri.appendQueryParameter("mc", "");
        payUri.appendQueryParameter("tr", transaction_ref_id);
        payUri.appendQueryParameter("tn", "Order Ref 74657475858");
        payUri.appendQueryParameter("am", amount);
        payUri.appendQueryParameter("cu", "INR");

        Uri uri = payUri.build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
            showApp(list, intent, getApplicationContext());
            Log.d(TAG, "" + list);
        } else {
            Toast.makeText(getApplicationContext(), "No UPI App found", Toast.LENGTH_LONG).show();
            Log.d("IN", "error");

        }
        sheetDialog.show();

    }

    private void showApp(List<ResolveInfo> list, Intent intent, Context applicationContext) {
        upiAppListHolder.setHasFixedSize(true);
        UpiAdapter adapter = new UpiAdapter(applicationContext, list, intent, this);
        upiAppListHolder.setAdapter(adapter);
        upiAppListHolder.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onUpiItemClick(ResolveInfo resolveInfo, Intent intent) {
        Log.d(TAG, "onUpiItemClick: " + resolveInfo);
        intent.setPackage(resolveInfo.activityInfo.packageName);
        startActivityForResult(intent, 1002);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1002:
                    String response = data.getStringExtra("response");
                    Log.d("Response", response);

                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(response);
                    upiPaymentDataOperation(dataList);
                    break;
            }
        }

    }

    private void upiPaymentDataOperation(ArrayList<String> dataList) {


        String str = dataList.get(0);
        Log.d(TAG, "upiPaymentDataOperation: " + str);
        Log.d("UPIPAY", "upiPaymentDataOperation: " + str);
        String paymentCancel = "";
        if (str == null) str = "discard";
        String status = "";
        String approvalRefNo = "";
        String response[] = str.split("&");
        for (int i = 0; i < response.length; i++) {
            String equalStr[] = response[i].split("=");
            if (equalStr.length >= 2) {
                if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                    status = equalStr[1].toLowerCase();
                } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                    approvalRefNo = equalStr[1];
                }
            } else {
                paymentCancel = "Payment cancelled by user.";
            }
        }

        if (status.equals("success")) {
            //Code to handle successful transaction here.
            //Toast.makeText(getApplicationContext(), "Transaction successful.", Toast.LENGTH_SHORT).show();
            Log.d("UPI", "responseStr: " + approvalRefNo);
                /*Intent payconfirmation = new Intent(getApplicationContext(), PaymentConfirmation.class);
                String s = "responseStr: " + approvalRefNo;
                payconfirmation.putExtra("payref", s);
                payconfirmation.putExtra("amount", Constant.AMOUNT_TO_BE_PAID);
                startActivity(payconfirmation);
                */
            //Payment Done
            //1.update order status create transaction
            //for wallet if discount id provided
            //updtae trasaction
            //
//                String approvalno = "UPI : " + approvalRefNo;
//                String mode = "UPI APP " + getString(R.string.upi_vendor_vpa);
//                createTransaction(orderid, orderuid, approvalno, mode);
            Toast.makeText(getApplicationContext() , ""+approvalRefNo , Toast.LENGTH_SHORT).show();

        } else if ("Payment cancelled by user.".equals(paymentCancel)) {
            Toast.makeText(getApplicationContext(), "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
//                showChocoBar.showAlert("Payment cancelled by user");
        } else {
//                showChocoBar.showAlert("Payment cancelled by user");
            Toast.makeText(getApplicationContext(), "Payment cancel", Toast.LENGTH_SHORT).show();
        }

    }

    //    @Override
//    public void onBackPressed() {
//        if (getFragmentManager().getBackStackEntryCount() == 0) {
//            this.finish();
//        } else {
//            getFragmentManager().popBackStack();
//        }
//    }
}
