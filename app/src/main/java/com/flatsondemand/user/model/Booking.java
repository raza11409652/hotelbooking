/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.model;

import java.io.Serializable;
@SuppressWarnings("serial")
public class Booking implements Serializable {

    String id, number, bookingDate, startDate, endDate, status, amount, roomId, roomNumber,
            propertyId, propertyName , propertyCoverImage;


    public Booking(String id, String number, String bookingDate, String startDate, String endDate, String status, String amount, String roomId, String roomNumber, String propertyId, String propertyName) {
        this.id = id;
        this.number = number;
        this.bookingDate = bookingDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.amount = amount;
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.propertyId = propertyId;
        this.propertyName = propertyName;
    }

    public Booking(String id, String number, String bookingDate, String startDate, String endDate, String status, String amount, String roomId, String roomNumber, String propertyId, String propertyName, String propertyCoverImage) {
        this.id = id;
        this.number = number;
        this.bookingDate = bookingDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.amount = amount;
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.propertyId = propertyId;
        this.propertyName = propertyName;
        this.propertyCoverImage = propertyCoverImage;
    }

    public String getPropertyCoverImage() {
        return propertyCoverImage;
    }

    public void setPropertyCoverImage(String propertyCoverImage) {
        this.propertyCoverImage = propertyCoverImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
