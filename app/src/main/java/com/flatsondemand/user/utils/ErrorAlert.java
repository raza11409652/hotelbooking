/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.flatsondemand.user.R;

public class ErrorAlert extends ActivityCompat {
    Context context;
    AlertDialog alertDialog;

    public ErrorAlert(Context context) {
        this.context = context;
    }

    public void show(String msg) {
        try {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(context, R.style.customLoader);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.alert_error, null);
        Button ok = view.findViewById(R.id.ok);
        TextView errorMsg = view.findViewById(R.id.errorMsg);
        errorMsg.setText(msg);
        alert.setView(view);
        alert.setCancelable(false);
        alertDialog = alert.create();

        alertDialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }

    public void dismiss() {
        alertDialog.dismiss();
    }


}
