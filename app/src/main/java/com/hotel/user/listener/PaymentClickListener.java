/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.listener;

public interface PaymentClickListener {
    void onPaymentReceive(String amount, String paymentId);
    void onDetailsClick(String paymentId);
}
