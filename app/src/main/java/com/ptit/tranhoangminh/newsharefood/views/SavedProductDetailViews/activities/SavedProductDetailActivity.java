package com.ptit.tranhoangminh.newsharefood.views.SavedProductDetailViews.activities;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;
import com.ptit.tranhoangminh.newsharefood.models.ProductSQLite;
import com.ptit.tranhoangminh.newsharefood.presenters.savedProductDetailPresenters.SavedProductDetailPresenter;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.MaterialFragment;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.RecipeFragment;

import java.util.ArrayList;
import java.util.List;

public class SavedProductDetailActivity extends AppCompatActivity implements SavedProductDetailView {
    TabLayout tabLayout;
    ViewPager viewPager;
    ProductSQLite productKey;
    ProgressBar pgbNewProductDetail;
    TextView tvName;
    ImageView imgProductDetail;
    SavedProductDetailPresenter savedProductDetailPresenter;
    MaterialFragment materialFragment;
    RecipeFragment recipeFragment;
    RelativeLayout relativeLayout;
    Toolbar toolbar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_product_detail_layout);
        productKey = (ProductSQLite) getIntent().getSerializableExtra("objectKey");
        setControl();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initPresenter();
        savedProductDetailPresenter.loadSavedProductDetail(productKey.getId());
    }

    void setControl() {
        relativeLayout = findViewById(R.id.relativeLayoutPD);
        relativeLayout.setVisibility(View.GONE);
        tabLayout = findViewById(R.id.tablayoutPDetail);
        viewPager = findViewById(R.id.viewpagerPDetail);
        pgbNewProductDetail = findViewById(R.id.progressBarNewProductDetail);
        tvName = findViewById(R.id.textViewNameDetail);
        imgProductDetail = findViewById(R.id.imageViewProductDetail);
        materialFragment = new MaterialFragment();
        recipeFragment = new RecipeFragment();
        toolbar = findViewById(R.id.toolbar);
    }

    private void initPresenter() {
        savedProductDetailPresenter = new SavedProductDetailPresenter(this, this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(materialFragment, "Materials");
        adapter.addFragment(recipeFragment, "Recipe");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setText("Nguyên liệu");
        tabLayout.getTabAt(1).setText("Công thức");
    }

    @Override
    public void showProgress() {
        pgbNewProductDetail.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pgbNewProductDetail.setVisibility(View.GONE);
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayProductDetail(ProductDetail productDetail) {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        tvName.setText(productKey.getName());
        imgProductDetail.setImageBitmap(productKey.getByteasBitmap());
        Bundle materialBundle = new Bundle();
        materialBundle.putString("materials",productDetail.getMaterials());
        materialFragment.setArguments(materialBundle);
        Bundle recipeBundle = new Bundle();
        recipeBundle.putString("recipe", productDetail.getRecipe());
        recipeFragment.setArguments(recipeBundle);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
