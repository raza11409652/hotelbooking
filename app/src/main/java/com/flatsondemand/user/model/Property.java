/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.model;

public class Property {
    String id , name  , price , image  ;
    int room ;
    boolean isMapped ;

    public Property(String id, String name, String price, String image, int room, boolean isMapped) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.room = room;
        this.isMapped = isMapped;
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
