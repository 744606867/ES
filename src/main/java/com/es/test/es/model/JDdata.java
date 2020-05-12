package com.es.test.es.model;

public class JDdata {


   private String price;
   private String name;
    private String img;


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "JDdata{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
