package com.ptit.tranhoangminh.newsharefood.presenters.categoryPresenters;

import com.ptit.tranhoangminh.newsharefood.models.Category;
import com.ptit.tranhoangminh.newsharefood.views.CategoryViews.activities.CategoryView;

import java.util.ArrayList;

/**
 * Created by Lãng on 26/4/2018.
 */

public class CategoryPresenter implements LoadCategoriesListener {
    private CategoryView categoryView;
    private CategoryInteractor categoryInteractor;
    private ArrayList<Category> cateList;

    public CategoryPresenter(CategoryView categoryView) {
        this.categoryView = categoryView;
        this.categoryInteractor = new CategoryInteractor(this);
    }

    public void loadCategories() {
        categoryView.showProgress();
        categoryInteractor.createCategoryList();
    }

    @Override
    public void onLoadCategoriesSuccess(ArrayList<Category> cateList) {
        this.cateList = cateList;
        categoryView.displayCategories(cateList);
        categoryView.hideProgress();
    }

    @Override
    public void onLoadCategoriesFailure(String message) {
        categoryView.displayMessage(message);
        categoryView.hideProgress();
    }
}
