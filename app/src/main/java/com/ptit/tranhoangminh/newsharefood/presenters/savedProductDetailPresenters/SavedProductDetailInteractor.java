package com.ptit.tranhoangminh.newsharefood.presenters.savedProductDetailPresenters;

import android.content.Context;

import com.ptit.tranhoangminh.newsharefood.database.DatabaseHelper;

public class SavedProductDetailInteractor {
    private DatabaseHelper db;
    private LoadSavedProductDetailListener listener;

    public SavedProductDetailInteractor(LoadSavedProductDetailListener listener, Context context) {
        this.listener = listener;
        db = new DatabaseHelper(context);
    }

    public void createSavedProductDetail(String id) {
        listener.onLoadSavedProductDetailSuccess(db.getProductDetail(id), db.getProduct(id));
    }
}
