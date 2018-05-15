package com.ptit.tranhoangminh.newsharefood.views.CategoryViews.activities;

import com.ptit.tranhoangminh.newsharefood.models.Category;

import java.util.ArrayList;

/**
 * Created by Lãng on 26/4/2018.
 */

public interface CategoryView {
    public void showProgress();

    public void hideProgress();

    public void displayMessage(String message);

    public void displayCategories(ArrayList<Category> categoriesList);
}
