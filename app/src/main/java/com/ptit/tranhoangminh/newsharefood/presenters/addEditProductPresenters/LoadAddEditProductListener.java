package com.ptit.tranhoangminh.newsharefood.presenters.addEditProductPresenters;

import android.graphics.Bitmap;

import com.ptit.tranhoangminh.newsharefood.models.Category;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;

import java.util.List;

public interface LoadAddEditProductListener {
    public void onLoadCategorySuccess(List<Category> categories);

    public void onLoadCategoryFailure(String message);

    void onLoadEditProductSuccess(ProductDetail productDetail, Bitmap bitmap);

    void onLoadEditProductFailure(String message);

    void onPushNewProductSuccess();

    void onPushNewProductFailure(String message);

    void onSetOldProductSuccess();

    void onSetOldProductFailure(String message);

    void onCancel();
}
