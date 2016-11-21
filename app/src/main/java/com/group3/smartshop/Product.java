package com.group3.smartshop;

/**
 * Created by Isabella on 2016/11/11.
 */

public class Product {
    private String name;
    private Double money;
    private String img;
    private String link;

    public Product() {
    }

    public Product(String name, Double money, String img, String link) {
        this.name = name;
        this.money = money;
        this.img = img;
        this.link = link;
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

    public String getLink() {return link;}

    public void setTImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }
}
