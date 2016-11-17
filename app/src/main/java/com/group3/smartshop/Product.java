package com.group3.smartshop;

/**
 * Created by Isabella on 2016/11/11.
 */

public class Product {
    private String name;
    private Double money;
    private String img;

    public Product() {
    }

    public Product(String name, Double money, String img) {
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

    public void setHowMuch(Double m) {
        this.money = m;
    }

    public Double getHowMuch() {
        return money;
    }

    public void setTImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }
}
