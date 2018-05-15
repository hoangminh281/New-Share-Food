package com.ptit.tranhoangminh.newsharefood.views.SearchViews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.adapters.ProductAdapter;
import com.ptit.tranhoangminh.newsharefood.models.Product;

import com.ptit.tranhoangminh.newsharefood.presenters.searchProductPresenters.SearchProductPresenter;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.activities.NewProductDetailActivity;


import java.util.List;

/**
 * Created by Harrik on 5/9/2018.
 */

public class SeachViewActivity extends AppCompatActivity implements SearchViewImp {
    SearchView searchView;
    GridView gridView;
    String cate_id;
    SearchProductPresenter searchProductPresenter;
    ProductAdapter myAdapter;
    private List<Product> productArrayList;
    private ProgressBar pgbSearchProduct;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_product);
        setControl();
        initPresenter();
        Bundle bundle = getIntent().getExtras();
        cate_id = bundle.getString("cate_id");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null) {
                    searchProductPresenter.loadAllSearchProducts( newText);
                }

                return true;
            }
        });
        registerForContextMenu(gridView);
        SuKien();

    }


        private void SuKien() {
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent detailProductIntent = new Intent(SeachViewActivity.this, NewProductDetailActivity.class);
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

    private void initPresenter(){
        searchProductPresenter = new SearchProductPresenter(this, this);
    }

    private void setControl() {
        searchView = (SearchView) findViewById(R.id.searchview);
        gridView = (GridView) findViewById(R.id.search);
        pgbSearchProduct = (ProgressBar)findViewById(R.id.progressBarSearch);
    }
    @Override
    public void showProgress() {
        pgbSearchProduct.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pgbSearchProduct.setVisibility(View.GONE);
    }
    @Override
    public void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void displaySearchProducts(List<Product> searchProduct) {
        this.productArrayList = searchProduct;
        this.myAdapter = new ProductAdapter(this, R.layout.dong_products, productArrayList);
        gridView.setAdapter(myAdapter);
    }



}
