package com.group3.smartshop;

/**
 * Created by Isabella on 2016/10/31.
 */

public class Product {
    private String name;
    private double money;
    private int thumbnail;

    public Product() {
    }

    public Product(String name, double money, int thumbnail) {
        this.name = name;
        this.money = money;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHowMuch() {
        return money;
    }

    public void setHowMuch(double m) {
        this.money = m;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}

