/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.model;

import java.util.ArrayList;

public class PaymentsDate {
    String date;
    ArrayList<Payments>paymemts ;

    public PaymentsDate(String date, ArrayList<Payments> paymemts) {
        this.date = date;
        this.paymemts = paymemts;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Payments> getPaymemts() {
        return paymemts;
    }

    public void setPaymemts(ArrayList<Payments> paymemts) {
        this.paymemts = paymemts;
    }
}
