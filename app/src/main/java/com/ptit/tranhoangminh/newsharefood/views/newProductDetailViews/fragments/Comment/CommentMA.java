package com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment;

import android.graphics.Bitmap;
import java.util.HashMap;

/**
 * Created by TramLuc on 5/14/2018.
 */

public class CommentMA {

    String productId;
    String memberId;
    String tieude;
    String binhluan;
    String membername;
    HashMap<String,String> listLike= new HashMap<>();
    int like;

    public HashMap<String, String> getListLike() {
        return listLike;
    }

    public void setListLike(HashMap<String, String> listLike) {
        this.listLike = listLike;
    }
    public CommentMA(){
    }
    public CommentMA(String id, String uid, String tieude, String binhluan, String username, Bitmap imgUser, int i) {
        //mặc định của firebase dùng để nhận dữ liệu
    }

    public CommentMA(String productId, String memberId, String tieude, String binhluan,
                     String membername, int like) {
        this.productId = productId;
        this.memberId = memberId;
        this.tieude = tieude;
        this.binhluan = binhluan;
        this.membername = membername;
        this.like = like;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getBinhluan() {
        return binhluan;
    }

    public void setBinhluan(String binhluan) {
        this.binhluan = binhluan;
    }
}
