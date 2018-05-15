package com.ptit.tranhoangminh.newsharefood.models;

public class HomeSearch {
    String name, image, id;
    int parent_id;

    public HomeSearch() {

    }

    public HomeSearch(String name, String image, String id, int parent_id) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
