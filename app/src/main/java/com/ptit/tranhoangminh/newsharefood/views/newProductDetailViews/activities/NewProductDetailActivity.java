package com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.MemberModel;
import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;
import com.ptit.tranhoangminh.newsharefood.presenters.productDetailPresenters.ProductDetailPresenter;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.Comment_FullCommentFragment;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.Comment_MyCommentFragment;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.Comment_WriteCommentFragment;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.CommentFragment;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.MaterialFragment;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.RecipeFragment;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.VideoFragment;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.CommentFragment;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.MaterialFragment;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.RecipeFragment;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.VideoFragment;

import java.util.ArrayList;
import java.util.List;

public class NewProductDetailActivity extends AppCompatActivity implements ProductDetailView {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    Product productKey;
    ProductDetail pDetail;
    ProgressBar pgbNewProductDetail;
    TextView tvView, tvName, tvOwnerName;
    ImageView imgProductDetail, imgOwner;
    ProductDetailPresenter productDetailPresenter;
    MaterialFragment materialFragment;
    RecipeFragment recipeFragment;
    VideoFragment videoFragment;
    CommentFragment commentFragment;
    CheckBox cbSave;
    Button btnShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_product_detail_layout);
        productKey = (Product) getIntent().getSerializableExtra("objectKey");

        setControls();

        initPresenter();
        productDetailPresenter.loadProductDetail(productKey.getId(), productKey.getImage(), productKey.getMember_id());

        setEvents();

        setSupportActionBar(toolbar);
    }

    private void initPresenter() {
        productDetailPresenter = new ProductDetailPresenter(this, this);
    }

    private void setControls() {
        toolbar = findViewById(R.id.toolbarPDetail);
        tabLayout = findViewById(R.id.tablayoutPDetail);
        viewPager = findViewById(R.id.viewpagerPDetail);
        pgbNewProductDetail = findViewById(R.id.progressBarNewProductDetail);
        tvView = findViewById(R.id.textViewView);
        tvName = findViewById(R.id.textViewNameDetail);
        imgProductDetail = findViewById(R.id.imageViewProductDetail);
        materialFragment = new MaterialFragment();
        recipeFragment = new RecipeFragment();
        videoFragment = new VideoFragment();
        commentFragment = new CommentFragment(this,productKey);
        cbSave = findViewById(R.id.checkboxSave);
        btnShare = findViewById(R.id.buttonShare);
        tvOwnerName = findViewById(R.id.textviewOwnerName);
        imgOwner = findViewById(R.id.imageViewOwner);
    }
    void setEvents() {
        cbSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    productDetailPresenter.saved(productKey, pDetail, ((BitmapDrawable) imgProductDetail.getDrawable()).getBitmap());
                    cbSave.setText("Đã lưu");
                } else {
                    productDetailPresenter.unSave(productKey.getId());
                    cbSave.setText("Lưu");
                }
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody = "your body here";
                String shareSub = "your subject here";
                intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(intent, "Sharing via:"));
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(materialFragment, "Materials");
        adapter.addFragment(recipeFragment, "Recipe");
        adapter.addFragment(videoFragment, "Video");
        adapter.addFragment(commentFragment, "Comments");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setText("Nguyên liệu");
        tabLayout.getTabAt(1).setText("Công thức");
        tabLayout.getTabAt(2).setText("Video");
        tabLayout.getTabAt(3).setText("Bình luận");
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
        pDetail = productDetail;
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        
        tvName.setText(productKey.getName());
        tvView.setText(productDetail.getLike() + "");
        materialFragment.setMaterials(productDetail.getMaterials());
        recipeFragment.setRecipe(productDetail.getRecipe());
        videoFragment.setVideoId(productDetail.getVideo());
    }

    public void displayImageProductDetail(Bitmap bitmap) {
        imgProductDetail.setImageBitmap(bitmap);
    }

    @Override
    public void displayOwnerProductDetail(MemberModel memberModel) {
        tvOwnerName.setText(memberModel.getHoten());
    }

    @Override
    public void displayImageOwner(Bitmap bitmap) {
        imgOwner.setImageBitmap(bitmap);
    }

    @Override
    public void setView(int i) {
        tvView.setText(i + "");
    }

    @Override
    public void setCheckedSave(boolean b) {
        if (b) {
            cbSave.setText("Đã lưu");
        } else {
            cbSave.setText("Lưu");
        }
        cbSave.setChecked(b);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

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
