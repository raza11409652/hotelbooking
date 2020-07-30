/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Property  implements Serializable {
    String id , name  , price , image   , type , address , lat , lng;
    int room ;
    boolean isMapped ;

    public Property(String id, String name, String price, String image, String type, String address, String lat, String lng, int room, boolean isMapped) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.type = type;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.room = room;
        this.isMapped = isMapped;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public boolean isMapped() {
        return isMapped;
    }

    public void setMapped(boolean mapped) {
        isMapped = mapped;
    }
}
