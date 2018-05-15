package com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.activities;

import android.graphics.Bitmap;

import com.ptit.tranhoangminh.newsharefood.models.MemberModel;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;

public interface ProductDetailView {
    public void showProgress();

    public void hideProgress();

    public void displayMessage(String message);

    public void displayProductDetail(ProductDetail productDetail);

    public void displayImageProductDetail(Bitmap bitmap);

    public void displayOwnerProductDetail(MemberModel memberModel);

    public void displayImageOwner(Bitmap bitmap);

    public void setView(int i);

    public void setCheckedSave(boolean b);
}
