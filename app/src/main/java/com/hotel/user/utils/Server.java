/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.utils;

public class Server {
    private static String SERVER_URL = "http://192.168.43.10/hotel/";
    private static String ROOT_URL = SERVER_URL + "user_app/api/";
    public static String ACTIVE_BOOKING = ROOT_URL + "activeBookings.php";
    public static String PAYMENTS_BY_BOOKING = ROOT_URL + "paymentsByBooking.php";
    public static String PAYMENTS_BY_BOOKING_DATE = ROOT_URL + "paymentsByBookingByDate.php";
    public static String CHAT = ROOT_URL + "chat.php";
    public static String USER_CHECK_NEW = ROOT_URL + "checkUserStatus.php";
    public static String BOOKINGS = ROOT_URL + "bookings.php";
    public static String COMPLAINTS_CAT = ROOT_URL + "complaintsCategory.php";
    public static String COMPLAINTS_SUB_CAT = ROOT_URL + "complaintsSubCategory.php";
    public static String FREE_TIME_SLOT = ROOT_URL + "getFreeTimeSlot.php";
    public static String CREATE_HOUSE_KEEPING = ROOT_URL + "createHouseKeeping.php";
    public static String RAZOR_PAY_PAYMENT_UPDATE = ROOT_URL + "updatePaymentFromRazorPayStatus.php";
    public static String LOCATION = ROOT_URL + "location.php";
    public static String SEARCH_PROPERTY = ROOT_URL + "searchproperty.php";
    public static String WISHLIST_TOGGLE = ROOT_URL + "userwish.php";
    public static String DATE_SELECTOR = ROOT_URL + "getdate.php";
    public static String CREATE_BOOKING = ROOT_URL + "createbooking.php";
    public static String VERIFY = ROOT_URL + "verify_mobile.php";



    public static String ORDER_GEN_RAZOR_PAY = SERVER_URL + "razorpay/neworder.php";




    public static String PRIVACY_POLICY = "https://www.flatsondemand.in/?view=privacy-policy";
    public static String TERMS_COND = "https://www.flatsondemand.in/?view=terms";
    public static String HERE_MAP_PLACE = "https://discover.search.hereapi.com/v1/discover";




//    public static String ORDER_GEN_RAZOR_PAY = SERVER_URL + "razorpay/neworder.php";


}
