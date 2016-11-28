package com.group3.smartshop;

/**
 * Created by Isabella on 2016/11/11.
 */

public class Product {
    private String name;
    private Double money;
    private String img;
    private String link;
    private String origin;
    private String expensive;

    public Product() {
    }

    public Product(String name, Double money, String img, String link, String origin) {
        this.name = name;
        this.money = money;
        this.img = img;
        this.link = link;
        this.origin = origin;
    }

    public Product (String name, String expensive, String img, String link, String origin){
        this.name = name;
        this.expensive = expensive;
        this.img = img;
        this.link = link;
        this.origin = origin;
    }


    public void setExpensive (String exp){
        this.expensive = exp;
    }

    public String getExpensive (){
        return this.expensive;
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

    public Double getHowMuch() {return money; }

    public void setLink(String link) {this.link = link;}

    public String getLink() {return link;}

    public void setOrigin(String origin) {this.origin = origin;}

    public String getOrigin() {return origin;}

    public void setTImg(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }
}
