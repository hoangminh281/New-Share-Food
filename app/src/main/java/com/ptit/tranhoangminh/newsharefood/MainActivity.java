package com.ptit.tranhoangminh.newsharefood;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.ptit.tranhoangminh.newsharefood.user.UserActivity;
import com.ptit.tranhoangminh.newsharefood.views.AddEditProductViews.activities.NewModifyProductActivity;
import com.ptit.tranhoangminh.newsharefood.views.CategoryViews.fragments.CategoryFragment;
import com.ptit.tranhoangminh.newsharefood.views.HomePageApp.HomePageAppActivity;
import com.ptit.tranhoangminh.newsharefood.views.HomePageRes.HomePageResActivity;
import com.ptit.tranhoangminh.newsharefood.views.ProductViews.activities.ProductActivity;
import com.ptit.tranhoangminh.newsharefood.views.SavedProductViews.activities.SavedProductActivity;
import com.ptit.tranhoangminh.newsharefood.views.SavedProductViews.fragments.SavedProductFragment;
import com.ptit.tranhoangminh.newsharefood.views.SearchViews.SeachViewActivity;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Quota;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setControls();

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    void setControls() {
        toolbar = findViewById(R.id.toolbarHome);
        tabLayout = findViewById(R.id.tablayoutHome);
        viewPager = findViewById(R.id.viewPagerHome);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            menu.findItem(R.id.menuSignIn).setVisible(false);
        } else {
            menu.findItem(R.id.menuSignOut).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSignIn:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.menuSignOut:
                FirebaseAuth.getInstance().signOut();
                recreate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomePageAppActivity(), "Home");
        adapter.addFragment(new HomePageResActivity(), "Store");
        adapter.addFragment(new CategoryFragment(), "Category");
        adapter.addFragment(new SavedProductFragment(), "SavedProduct");
        adapter.addFragment(new UserActivity(), "User");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i=0; i< tabLayout.getTabCount(); i++) {
            if (i==tabLayout.getSelectedTabPosition()) continue;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tabLayout.getTabAt(i).getIcon().setTint(getResources().getColor(R.color.gray_tint));
            }
        }
    }

    private void setupTabIcons() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabLayout.getTabAt(0).setIcon(R.mipmap.ic_home).getIcon().setTint(getResources().getColor(R.color.themeApp));
            tabLayout.getTabAt(1).setIcon(R.mipmap.ic_store).getIcon();
            tabLayout.getTabAt(2).setIcon(R.mipmap.ic_cook).getIcon();
            tabLayout.getTabAt(3).setIcon(R.mipmap.ic_save).getIcon();
            tabLayout.getTabAt(4).setIcon(R.mipmap.ic_person).getIcon();
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tab.getIcon().setTint(getResources().getColor(R.color.themeApp));
                }
                if (tab.getPosition() == 2) {
                    toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar));
                    tabLayout.setBackgroundColor(getResources().getColor(R.color.toolbar));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tab.getIcon().setTint(getResources().getColor(R.color.gray_tint));
                }
                if (tab.getPosition() == 2) {
                    toolbar.setBackgroundColor(getResources().getColor(R.color.themeApp));
                    tabLayout.setBackgroundColor(getResources().getColor(R.color.tablayout));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
            return "";
            //return mFragmentTitleList.get(position);
        }

    }
}
