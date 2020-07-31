/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.model;

public class Payments {
    String id , time , startDate , endDate , electricity , electricityRef ,rent , others , status , submittedOn ,payMode ,
    payModeRef , amount,room , dueDate  , fine;
    Boolean isPaid ;

    public Payments(String id, String time, String startDate, String endDate, String electricity, String electricityRef, String rent, String others, String status, String submittedOn, String payMode, String payModeRef, String amount, String room, String dueDate, String fine, Boolean isPaid) {
        this.id = id;
        this.time = time;
        this.startDate = startDate;
        this.endDate = endDate;
        this.electricity = electricity;
        this.electricityRef = electricityRef;
        this.rent = rent;
        this.others = others;
        this.status = status;
        this.submittedOn = submittedOn;
        this.payMode = payMode;
        this.payModeRef = payModeRef;
        this.amount = amount;
        this.room = room;
        this.dueDate = dueDate;
        this.fine = fine;
        this.isPaid = isPaid;
    }

//    public Payments(String id, String time, String startDate, String endDate, String electricity, String electricityRef, String rent, String others, String status, String submittedOn, String payMode, String payModeRef, String amount, String room, Boolean isPaid) {
//        this.id = id;
//        this.time = time;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.electricity = electricity;
//        this.electricityRef = electricityRef;
//        this.rent = rent;
//        this.others = others;
//        this.status = status;
//        this.submittedOn = submittedOn;
//        this.payMode = payMode;
//        this.payModeRef = payModeRef;
//        this.amount = amount;
//        this.room = room;
//        this.isPaid = isPaid;
//    }

    public String getDueDate() {
        return dueDate;
    }

    public String getFine() {
        return fine;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getElectricityRef() {
        return electricityRef;
    }

    public void setElectricityRef(String electricityRef) {
        this.electricityRef = electricityRef;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubmittedOn() {
        return submittedOn;
    }

    public void setSubmittedOn(String submittedOn) {
        this.submittedOn = submittedOn;
    }

    public String getPayMode() {
        return payMode;
    }

    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    public String getPayModeRef() {
        return payModeRef;
    }

    public void setPayModeRef(String payModeRef) {
        this.payModeRef = payModeRef;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
