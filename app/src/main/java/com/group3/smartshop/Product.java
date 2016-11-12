package com.group3.smartshop;

/**
 * Created by Isabella on 2016/11/11.
 */

public class Product {
    private String name;
    private double money;
    private int img;

    public Product() {
    }

    public Product(String name, double money, int img) {
        this.name = name;
        this.money = money;
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHowMuch(double m) {
        this.money = m;
    }

    public double getHowMuch() {
        return money;
    }

    public void setTImg(int img) {
        this.img = img;
    }

    public int getImg() {
        return img;
    }
}
