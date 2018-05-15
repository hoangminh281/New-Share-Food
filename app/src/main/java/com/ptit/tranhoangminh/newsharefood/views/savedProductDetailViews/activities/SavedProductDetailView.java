package com.ptit.tranhoangminh.newsharefood.views.SavedProductDetailViews.activities;

import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;

public interface SavedProductDetailView {
    public void showProgress();

    public void hideProgress();

    public void displayMessage(String message);

    public void displayProductDetail(ProductDetail productDetail);
}
