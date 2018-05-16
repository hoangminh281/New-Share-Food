package com.ptit.tranhoangminh.newsharefood.views.CategoryViews.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.ContactActivity;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.UserActivity;
import com.ptit.tranhoangminh.newsharefood.models.Category;
import com.ptit.tranhoangminh.newsharefood.presenters.categoryPresenters.CategoryPresenter;
import com.ptit.tranhoangminh.newsharefood.adapters.CategoryAdapter;
import com.ptit.tranhoangminh.newsharefood.views.ProductViews.activities.ProductActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryView {

    ListView lvMonAn;
    ProgressBar pgbCategory;
    CategoryPresenter categoryPresenter;
    private List<Category> categoryList;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_layout);
        NavigationView nav_view = (NavigationView) findViewById(R.id.navView);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.userinformation) {
                    Intent intent = new Intent(CategoryActivity.this, UserActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.contact) {
                    Intent intent = new Intent(CategoryActivity.this, ContactActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.logout) {
                    Toast.makeText(CategoryActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        setControl();
        initPresenter();
        categoryPresenter.loadCategories();
        setEvents();
    }

    private void setEvents() {
        lvMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CategoryActivity.this, ProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", categoryList.get(i).getId());
                bundle.putString("name", categoryList.get(i).getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        lvMonAn = (ListView) findViewById(R.id.lvMonAn);
        pgbCategory = findViewById(R.id.progressBarCategories);
    }

    private void initPresenter() {
        categoryPresenter = new CategoryPresenter(this);
    }

    @Override
    public void displayCategories(ArrayList<Category> cateList) {
        this.categoryList = cateList;
        lvMonAn.setAdapter(new CategoryAdapter(this, R.layout.dong_categories, cateList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showProgress() {
        pgbCategory.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pgbCategory.setVisibility(View.GONE);
    }

    @Override
    public void displayMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
