package com.ptit.tranhoangminh.newsharefood.views.AddEditProductViews.activities;

import android.graphics.Bitmap;

import com.ptit.tranhoangminh.newsharefood.models.Category;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;

import java.util.List;

public interface AddEditProductView {
    public void showProgress();

    public void hideProgress();

    public void displayMessage(String message);

    public void displayEditProduct(ProductDetail productDetail, Bitmap bitmap);

    public void displayCategoryList(List<Category> categories);

    public void backActivity();

    public void setCategory(Category category);
}
