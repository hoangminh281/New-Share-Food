package com.ptit.tranhoangminh.newsharefood.presenters.productDetailPresenters;


import android.content.Context;
import android.graphics.Bitmap;

import com.ptit.tranhoangminh.newsharefood.models.MemberModel;
import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.activities.ProductDetailView;

public class ProductDetailPresenter implements LoadProductDetailListener {
    private ProductDetailView productDetailView;
    private ProductDetailInteractor productDetailInteractor;

    public ProductDetailPresenter(ProductDetailView productDetailView, Context context) {
        this.productDetailView = productDetailView;
        this.productDetailInteractor = new ProductDetailInteractor(this, context);
    }

    public void loadProductDetail(String id, String image_id, String owner_id) {
        productDetailView.showProgress();
        productDetailInteractor.loadProductDetail(id);
        productDetailInteractor.loadImageProductDetail(image_id);
        productDetailInteractor.loadOwnerDetail(owner_id);
        productDetailView.setCheckedSave(productDetailInteractor.isExistItemSQlite(id));
    }

    @Override
    public void onLoadImageProductDetailSuccess(Bitmap bitmap) {
        productDetailView.displayImageProductDetail(bitmap);
    }

    @Override
    public void onLoadProductDetailSuccess(ProductDetail productDetail) {
        productDetailView.displayProductDetail(productDetail);
        productDetailView.hideProgress();
    }

    public void onLoadOwnerDetailSuccess(MemberModel memberModel) {
        productDetailView.displayOwnerProductDetail(memberModel);
    }

    public void onLoadImageOwnerSuccess(Bitmap bitmap) {
        productDetailView.displayImageOwner(bitmap);
    }

    @Override
    public void onLoadProgressedFailure(String message) {
        productDetailView.displayMessage(message);
        productDetailView.hideProgress();
    }

    @Override
    public void onLoadUnProgressFailure(String message) {
        productDetailView.displayMessage(message);
    }

    public void saved(Product product, ProductDetail pDetail, Bitmap bitmap) {
        productDetailInteractor.addProductSqlite(product, pDetail, bitmap);
        productDetailInteractor.setView(product.getId(), 1);
    }

    public void unSave(String id) {
        productDetailInteractor.removeProductSqlite(id);
        productDetailInteractor.setView(id, -1);
    }

    @Override
    public void onSaveSuccess(int i) {
        productDetailView.setView(i);
    }

}
