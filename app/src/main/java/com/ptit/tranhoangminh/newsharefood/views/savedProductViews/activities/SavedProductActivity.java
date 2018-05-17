package com.ptit.tranhoangminh.newsharefood.views.SavedProductViews.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.adapters.SavedProductAdapter;
import com.ptit.tranhoangminh.newsharefood.models.ProductSQLite;
import com.ptit.tranhoangminh.newsharefood.presenters.savedProductPresenters.SavedProductPresenter;
import com.ptit.tranhoangminh.newsharefood.views.SavedProductDetailViews.activities.SavedProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class SavedProductActivity extends AppCompatActivity implements SavedProductView {
    private List<ProductSQLite> productArrayList;
    private SavedProductAdapter myAdapter;
    private GridView gridView;
    private ProgressBar pgbSavedProduct;
    private SavedProductPresenter savedProductPresenter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_layout);
        setControls();

        initPresenter();
        savedProductPresenter.loadAllSavedProducts();

        registerForContextMenu(gridView);
        setEvents();
    }

    void setEvents() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailProductIntent = new Intent(SavedProductActivity.this, SavedProductDetailActivity.class);
                detailProductIntent.putExtra("objectKey", productArrayList.get(position));
                startActivity(detailProductIntent);
            }
        });
    }

    private void setControls() {
        gridView = findViewById(R.id.gridview);
        pgbSavedProduct = findViewById(R.id.progressBarProduct);
        productArrayList = new ArrayList<>();
    }

    private void initPresenter() {
        savedProductPresenter = new SavedProductPresenter(this, this);
    }

    @Override
    public void showProgress() {
        pgbSavedProduct.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pgbSavedProduct.setVisibility(View.GONE);
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displaySavedProducts(List<ProductSQLite> savedProductList) {
        this.productArrayList = savedProductList;
        this.myAdapter = new SavedProductAdapter(this, R.layout.dong_products, productArrayList);
        gridView.setAdapter(myAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        menu.setHeaderTitle("Chọn chức năng");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final ProductSQLite pd = myAdapter.getItem((int) info.id);
        switch (item.getItemId()) {
            case R.id.menuXoa:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SavedProductActivity.this);
                alertDialog.setTitle("Xác nhận xóa");
                alertDialog.setMessage("Bạn thật sự muốn xóa sản phẩm này?");

                alertDialog.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        savedProductPresenter.destroyProductOnSQLite(pd.getId());
                    }
                }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
