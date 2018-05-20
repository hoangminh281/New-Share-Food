package com.ptit.tranhoangminh.newsharefood.presenters.savedProductDetailPresenters;

import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;
import com.ptit.tranhoangminh.newsharefood.models.ProductSQLite;

public interface LoadSavedProductDetailListener {
    void onLoadSavedProductDetailSuccess(ProductDetail pDetail, ProductSQLite productSQLite);

    void onLoadSavedProductDetailFailure(String message);
}
