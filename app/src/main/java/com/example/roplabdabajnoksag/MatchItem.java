package com.example.roplabdabajnoksag;

import android.provider.ContactsContract;

public class MatchItem {


    public MatchItem(String name, String info, String price, int ImageResource){
        this.name= name;
        this.info= info;
        this.price = price;
        this.imageResource = ImageResource;
    }
    public MatchItem(){}
    private String info;
    private String price;
    private int imageResource;

    private String name;

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResource() {
        return imageResource;
    }



}
