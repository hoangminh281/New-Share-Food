package com.ptit.tranhoangminh.newsharefood.models;

public class BillModel {
    String user_id;
    int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public BillModel(String user_id) {
        this.user_id = user_id;
    }

    public BillModel() {

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
