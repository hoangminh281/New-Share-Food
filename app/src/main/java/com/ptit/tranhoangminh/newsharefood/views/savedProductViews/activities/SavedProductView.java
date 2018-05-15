package com.ptit.tranhoangminh.newsharefood.views.SavedProductViews.activities;

import com.ptit.tranhoangminh.newsharefood.models.ProductSQLite;

import java.util.List;

public interface SavedProductView {
    public void showProgress();

    public void hideProgress();

    public void displayMessage(String message);

    public void displaySavedProducts(List<ProductSQLite> savedProductList);
}
