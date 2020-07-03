/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.utils;

public class Server {
    private static String SERVER_URL = "http://192.168.1.13/fod/";
    private static String ROOT_URL = SERVER_URL + "user_app/api/";
    public static String ACTIVE_BOOKING = ROOT_URL + "activeBookings.php";
    public static String PAYMENTS_BY_BOOKING = ROOT_URL + "paymentsByBooking.php";
    public static String PAYMENTS_BY_BOOKING_DATE = ROOT_URL + "paymentsByBookingByDate.php";
    public static String BOOKINGS = ROOT_URL + "bookings.php";
    public static String COMPLAINTS_CAT = ROOT_URL + "complaintsCategory.php";
    public static String COMPLAINTS_SUB_CAT = ROOT_URL + "complaintsSubCategory.php";
    public static String FREE_TIME_SLOT = ROOT_URL + "getFreeTimeSlot.php";
    public static String CREATE_HOUSE_KEEPING = ROOT_URL + "createHouseKeeping.php";
    public static String RAZOR_PAY_PAYMENT_UPDATE = ROOT_URL + "updatePaymentFromRazorPayStatus.php";
    public static String ORDER_GEN_RAZOR_PAY = SERVER_URL + "razorpay/neworder.php";
//    public static String ORDER_GEN_RAZOR_PAY = SERVER_URL + "razorpay/neworder.php";


}
