package com.ptit.tranhoangminh.newsharefood;


import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.ptit.tranhoangminh.newsharefood.views.CategoryViews.fragments.CategoryFragment;
import com.ptit.tranhoangminh.newsharefood.views.SavedProductViews.fragments.SavedProductFragment;

import java.util.ArrayList;
import java.util.List;

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
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setText("");
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_not_saved);
        //tabLayout.getTabAt(0).
        tabLayout.getTabAt(1).setText("");

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
