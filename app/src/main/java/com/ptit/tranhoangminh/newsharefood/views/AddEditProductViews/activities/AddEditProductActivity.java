package com.ptit.tranhoangminh.newsharefood.views.AddEditProductViews.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.Category;
import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;
import com.ptit.tranhoangminh.newsharefood.presenters.addEditProductPresenters.AddEditProductPresenter;
import com.ptit.tranhoangminh.newsharefood.views.ProductViews.activities.ProductActivity;

import java.util.Calendar;
import java.util.List;

public class AddEditProductActivity extends Activity implements AddEditProductView {

    EditText edtTenmon, edtLoaimon, edtNguyenlieu, edtCachnau;
    ImageView imgThemanh;
    Button btnChupanh, btnChonanh, btnOk, btnHuy;
    ProgressBar pgbAddEdit;
    String cate_id, cate_name;
    int mode;
    final int REQUEST_CODE_TAKEPHOTO = 1;
    final int REQUEST_CODE_PICKPHOTO = 2;
    Product pd;
    ProductDetail productDetail;
    AddEditProductPresenter addEditProductPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addeditproduct_layout);
        Bundle bundle = getIntent().getExtras();
        mode = bundle.getInt("mode");
        cate_id = bundle.getString("cate_id");
        cate_name = bundle.getString("cate_name");
        setControl();
        edtLoaimon.setText(cate_name);
        switch (mode) {
            case ProductActivity.ADD_MODE:
                hideProgress();
                break;
            case ProductActivity.EDIT_MODE:
                pd = (Product) bundle.getSerializable("product");
                edtTenmon.setText(pd.getName());
                addEditProductPresenter.loadEditProduct(pd.getId(),pd.getImage());
                break;
        }
        sukien();
    }

    void sukien() {
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenmon = edtTenmon.getText().toString();
                String nguyenlieu = edtNguyenlieu.getText().toString();
                String cachnau = edtCachnau.getText().toString();

                if (tenmon.matches("")) {
                    edtTenmon.setError("Tên món không được để trống");
                    return;
                }
                if (nguyenlieu.matches("")) {
                    edtNguyenlieu.setError("Nguyên liệu không được để trống");
                    return;
                }
                if (cachnau.matches("")) {
                    edtCachnau.setError("Cách nấu không được để trống");
                    return;
                }
                imgThemanh.setDrawingCacheEnabled(true);
                imgThemanh.buildDrawingCache();
                Bitmap bitmap = imgThemanh.getDrawingCache();
                switch (mode) {
                    case ProductActivity.ADD_MODE:
                        Calendar calendar = Calendar.getInstance();
                        String imageName = "image" + calendar.getTimeInMillis() + ".png";
                        Product newProduct = new Product("", cate_id,tenmon, imageName);
                        ProductDetail newProductDetail = new ProductDetail(0, 0, nguyenlieu, cachnau, "notfound", "I like it");
                        addEditProductPresenter.saveNewProduct(newProduct, newProductDetail, bitmap);
                        break;
                    case ProductActivity.EDIT_MODE:
                        pd.setName(edtTenmon.getText().toString());
                        productDetail.setMaterials(edtNguyenlieu.getText().toString());
                        productDetail.setRecipe(edtCachnau.getText().toString());
                        addEditProductPresenter.saveOldProduct(pd, productDetail, bitmap);
                        break;
                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditProductPresenter.onCancel();
            }
        });
        btnChupanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhoto, REQUEST_CODE_TAKEPHOTO);
            }
        });
        btnChonanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_CODE_PICKPHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_TAKEPHOTO:
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imgThemanh.setImageBitmap(bitmap);
                }
                break;
            case REQUEST_CODE_PICKPHOTO:
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imgThemanh.setImageBitmap(bitmap);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void setControl() {
        edtTenmon = findViewById(R.id.editTextTenmon);
        edtLoaimon = findViewById(R.id.editTextLoaimon);
        edtLoaimon.setEnabled(false);
        edtNguyenlieu = findViewById(R.id.editTextNguyenlieu);
        edtCachnau = findViewById(R.id.editTextCachnau);
        imgThemanh = findViewById(R.id.imageViewThemanh);
        btnOk = findViewById(R.id.buttonOk);
        btnHuy = findViewById(R.id.buttonHuy);
        pgbAddEdit = findViewById(R.id.progressBarAddEditProduct);
        btnChupanh = findViewById(R.id.buttonChupanh);
        btnChonanh = findViewById(R.id.buttonChonanh);
        addEditProductPresenter = new AddEditProductPresenter(this);
    }

    @Override
    public void showProgress() {
        pgbAddEdit.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pgbAddEdit.setVisibility(View.GONE);
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayEditProduct(ProductDetail productDetail, Bitmap bitmap) {
        this.productDetail = productDetail;
        edtNguyenlieu.setText(productDetail.getMaterials());
        edtCachnau.setText(productDetail.getRecipe());
        imgThemanh.setImageBitmap(bitmap);
    }

    @Override
    public void displayCategoryList(List<Category> categories) {

    }

    @Override
    public void backActivity() {
        AddEditProductActivity.super.onBackPressed();
    }

    @Override
    public void setCategory(Category category) {

    }
}
