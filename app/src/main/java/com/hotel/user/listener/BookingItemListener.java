/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.listener;

import com.hotel.user.model.Booking;

public interface BookingItemListener {
    public  void onPaymentButtonClick(String bookingId  , String bookingNumber) ;
    public  void onCheckInClick(Booking booking);
}
