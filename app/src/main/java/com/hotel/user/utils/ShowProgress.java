/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.hotel.user.R;

public class ShowProgress {
    Context context;
    AlertDialog alertDialog;

    public ShowProgress(Context context) {
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
        View view = layoutInflater.inflate(R.layout.custom_progress_circle, null);
        TextView errorMsg = view.findViewById(R.id.msg);
        errorMsg.setText(msg);
        alert.setView(view);
        alert.setCancelable(false);
        alertDialog = alert.create();
        alertDialog.show();

    }

    public boolean isShowing() {
        try {
            if (alertDialog.isShowing()) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void dismiss() {
        alertDialog.dismiss();
    }
}
