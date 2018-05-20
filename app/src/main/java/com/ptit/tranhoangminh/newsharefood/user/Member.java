package com.ptit.tranhoangminh.newsharefood.user;

import java.io.Serializable;

public class Member implements Serializable {
    public String hoten, gioitinh, ngaysinh, phone, hinhanh;

    public Member() {
    }

    public Member(String hoten, String gioitinh, String ngaysinh, String sothich, String hinhanh) {
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.phone = sothich;
        this.hinhanh = hinhanh;
    }

    public Member(String hoten, String gioitinh, String ngaysinh, String sothich) {
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.phone = sothich;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getPhone() {
        return phone;
    }

    public void setSothich(String phone) {
        this.phone = phone;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
