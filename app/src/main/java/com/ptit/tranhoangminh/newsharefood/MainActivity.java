package com.ptit.tranhoangminh.newsharefood;


import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.View;
import android.widget.ImageView;

import com.ptit.tranhoangminh.newsharefood.views.CategoryViews.fragments.CategoryFragment;
import com.ptit.tranhoangminh.newsharefood.views.SavedProductViews.fragments.SavedProductFragment;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Quota;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;


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

    void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CategoryFragment(), "Category");
        adapter.addFragment(new SavedProductFragment(), "SavedProduct");
        adapter.addFragment(new SavedProductFragment(), "SavedProduct");
        adapter.addFragment(new SavedProductFragment(), "SavedProduct");
        adapter.addFragment(new SavedProductFragment(), "SavedProduct");
        adapter.addFragment(new SavedProductFragment(), "SavedProduct");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(6);
    }


    private void setupTabIcons() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tabLayout.getTabAt(0).setIcon(R.mipmap.ic_home).getIcon().setTint(getResources().getColor(R.color.themeApp));
            tabLayout.getTabAt(1).setIcon(R.mipmap.ic_store).getIcon();
            tabLayout.getTabAt(2).setIcon(R.mipmap.ic_search).getIcon();
            tabLayout.getTabAt(3).setIcon(R.mipmap.ic_cook).getIcon();
            tabLayout.getTabAt(4).setIcon(R.mipmap.ic_save).getIcon();
            tabLayout.getTabAt(5).setIcon(R.mipmap.ic_person).getIcon();
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tab.getIcon().setTint(getResources().getColor(R.color.themeApp));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tab.getIcon().setTint(getResources().getColor(R.color.gray_tint));
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
