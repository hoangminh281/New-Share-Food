package com.ptit.tranhoangminh.newsharefood.views.ProductViews.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ptit.tranhoangminh.newsharefood.views.AddEditProductViews.activities.NewModifyProductActivity;
import com.ptit.tranhoangminh.newsharefood.views.SearchViews.SeachViewActivity;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.activities.NewProductDetailActivity;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.presenters.productPresenters.ProductPresenter;
import com.ptit.tranhoangminh.newsharefood.adapters.ProductAdapter;
import com.ptit.tranhoangminh.newsharefood.views.SavedProductViews.activities.SavedProductActivity;

import java.util.ArrayList;

/**
 * Created by Dell on 3/12/2018.
 */

public class ProductActivity extends AppCompatActivity implements ProductView {
    GridView gridView;
    Toolbar toolbar;
    String cate_id;
    String cate_name;
    Button btnBepchien;
    ProgressBar pgbProduct;
    ProductPresenter productPresenter;
    ArrayList<Product> productArrayList;
    ProductAdapter myAdapter;
    public final static int ADD_MODE = 0;
    public final static int EDIT_MODE = 1;
    FirebaseUser user;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_layout);
        setControl();
        initPresenter();

        Bundle bundle = getIntent().getExtras();
        cate_id = bundle.getString("id");
        cate_name = bundle.getString("name");

        productPresenter.loadProducts(cate_id);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MÓN ĂN BA MIỀN");
        toolbar.setTitleTextColor(Color.BLACK);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registerForContextMenu(gridView);
        SuKien();
    }

    private void setControl() {
        gridView = findViewById(R.id.gridview);
        toolbar = findViewById(R.id.toolbarLoaiMonAn);
        btnBepchien = findViewById(R.id.buttonBepchien);
        pgbProduct = findViewById(R.id.progressBarProduct);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void initPresenter() {
        productPresenter = new ProductPresenter(this);
    }

    private void SuKien() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailProductIntent = new Intent(ProductActivity.this, NewProductDetailActivity.class);
                detailProductIntent.putExtra("objectKey", productArrayList.get(i));
                startActivity(detailProductIntent);
            }
        });
        gridView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.menuThemmon:
                if (user!=null) {
                    Intent intent = new Intent(ProductActivity.this, NewModifyProductActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("mode", ADD_MODE);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 1111);
                }
                else {
                    //show log in activity
                }
                break;
            case R.id.search:
                Intent intent = new Intent(ProductActivity.this, SeachViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cate_id", cate_id);
                intent.putExtras(bundle);
                startActivity(intent);
            case R.id.menuDaluu:
                intent = new Intent(ProductActivity.this, SavedProductActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
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
        final Product pd = myAdapter.getItem((int) info.id);
        switch (item.getItemId()) {
            case R.id.menuSua:
                Intent intent = new Intent(ProductActivity.this, NewModifyProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("mode", EDIT_MODE);
                bundle.putString("cate_id", cate_id);
                bundle.putString("cate_name", cate_name);
                bundle.putSerializable("product", pd);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1111);
                break;
            case R.id.menuXoa:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductActivity.this);
                alertDialog.setTitle("Xác nhận xóa");
                alertDialog.setMessage("Bạn thật sự muốn xóa sản phẩm này?");

                alertDialog.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        productPresenter.destroyProductOnFirebase(pd.getId(), pd.getImage());
                        productPresenter.loadProducts(cate_id);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1111) {
            productPresenter.loadProducts(cate_id);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showProgress() {
        pgbProduct.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pgbProduct.setVisibility(View.GONE);
    }

    @Override
    public void displayProducts(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
        this.myAdapter = new ProductAdapter(ProductActivity.this, R.layout.dong_products, productArrayList);
        gridView.setAdapter(myAdapter);
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
