package com.ptit.tranhoangminh.newsharefood.models;

public class OrderFoodModel {
    String tenmon;
    int soluong;
    long giatien;

    public long getGiatien() {
        return giatien;
    }

    public void setGiatien(long giatien) {
        this.giatien = giatien;
    }

    public OrderFoodModel() {
    }

    public OrderFoodModel(String tenmon, int soluong) {
        this.tenmon = tenmon;
        this.soluong = soluong;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    @Override
    public String toString() {
        return
                "tenmon='" + tenmon + '\'' +
                ", soluong='" + soluong +'\''+
                ",giatien="+giatien;

    }
}
