package com.ptit.tranhoangminh.newsharefood.presenters.addEditProductPresenters;


import android.graphics.Bitmap;

import com.ptit.tranhoangminh.newsharefood.models.Category;
import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;
import com.ptit.tranhoangminh.newsharefood.views.AddEditProductViews.activities.AddEditProductView;

import java.util.List;

public class AddEditProductPresenter implements LoadAddEditProductListener {
    private AddEditProductView addEditProductView;
    private AddEditProductInteractor addEditProductInteractor;

    public AddEditProductPresenter(AddEditProductView addEditProductView) {
        this.addEditProductView = addEditProductView;
        this.addEditProductInteractor = new AddEditProductInteractor(this);
    }

    public void loadCategory(){
        addEditProductView.hideProgress();
        this.addEditProductInteractor.createCategoryList();
    }

    @Override
    public void onLoadCategorySuccess(List<Category> categories) {
        addEditProductView.displayCategoryList(categories);
    }

    @Override
    public void onLoadCategoryFailure(String message) {
        addEditProductView.displayMessage(message);
        addEditProductView.hideProgress();
    }

    public void loadEditProduct(String id, String image_id) {
        addEditProductView.showProgress();
        addEditProductInteractor.createEditProduct(id, image_id);
    }

    @Override
    public void onLoadEditProductSuccess(ProductDetail productDetail, Bitmap bitmap) {
        addEditProductView.displayEditProduct(productDetail, bitmap);
        addEditProductView.hideProgress();
    }

    @Override
    public void onLoadEditProductFailure(String message) {
        addEditProductView.displayMessage(message);
        addEditProductView.hideProgress();
    }


    public void saveNewProduct(Product product,ProductDetail productDetail, Bitmap bitmap) {
        addEditProductView.showProgress();
        addEditProductInteractor.pushNewProduct(product,productDetail,bitmap);
    }

    @Override
    public void onPushNewProductSuccess() {
        addEditProductView.displayMessage("Thêm sản phẩm thành công");
        addEditProductView.hideProgress();
        addEditProductView.backActivity();
    }

    @Override
    public void onPushNewProductFailure(String message) {
        addEditProductView.displayMessage(message);
        addEditProductView.hideProgress();
    }

    public void saveOldProduct(Product product, ProductDetail productDetail) {
        addEditProductView.showProgress();
        addEditProductInteractor.setOldProduct(product,productDetail);
    }

    public void saveOldProduct(Product product, ProductDetail productDetail, Bitmap bitmap) {
        addEditProductView.showProgress();
        addEditProductInteractor.setOldProduct(product,productDetail,bitmap);
    }

    @Override
    public void onSetOldProductSuccess() {
        addEditProductView.displayMessage("Sửa sản phẩm thành công");
        addEditProductView.hideProgress();
        addEditProductView.backActivity();
    }

    @Override
    public void onSetOldProductFailure(String message) {
        addEditProductView.displayMessage(message);
        addEditProductView.hideProgress();
    }

    @Override
    public void onCancel() {
        addEditProductView.displayMessage("Hủy thành công");
        addEditProductView.backActivity();
    }
}
