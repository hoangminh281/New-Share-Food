package com.ptit.tranhoangminh.newsharefood.models;

public class MenuStoreModel {
    String product_id;
    String image,name;
    long price;

    public MenuStoreModel() {
    }

    public MenuStoreModel(String product_id, String image, String name, long price) {
        this.product_id = product_id;
        this.image = image;
        this.name = name;
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
