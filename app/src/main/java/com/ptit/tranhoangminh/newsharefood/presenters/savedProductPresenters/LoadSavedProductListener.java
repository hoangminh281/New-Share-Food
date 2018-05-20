package com.ptit.tranhoangminh.newsharefood.presenters.savedProductPresenters;

import com.ptit.tranhoangminh.newsharefood.models.ProductSQLite;

import java.util.List;

public interface LoadSavedProductListener {
    void onLoadSavedProductsSuccess(List<ProductSQLite> productArrayList);

    void onLoadSavedProductsFailure(String message);
}
