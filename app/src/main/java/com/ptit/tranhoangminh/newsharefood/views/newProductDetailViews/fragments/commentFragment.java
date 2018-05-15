package com.ptit.tranhoangminh.newsharefood.views.newProductDetailViews.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.google.firebase.auth.FirebaseAuth;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.models.ProductDetail;
import com.ptit.tranhoangminh.newsharefood.views.newProductDetailViews.activities.NewProductDetailActivity;
import com.ptit.tranhoangminh.newsharefood.views.newProductDetailViews.activities.ProductDetailView;
import com.ptit.tranhoangminh.newsharefood.views.newProductDetailViews.fragments.Comment.Comment_FullCommentFragment;
import com.ptit.tranhoangminh.newsharefood.views.newProductDetailViews.fragments.Comment.Comment_MyCommentFragment;
import com.ptit.tranhoangminh.newsharefood.views.newProductDetailViews.fragments.Comment.Comment_WriteCommentFragment;
import com.ptit.tranhoangminh.newsharefood.views.productViews.activities.ProductActivity;

import java.util.ArrayList;
import java.util.List;

public class commentFragment extends Fragment {
   FirebaseAuth firebaseAuth;
    Comment_FullCommentFragment fullCommentFragment;
    Comment_MyCommentFragment myCommentFragment;
    Comment_WriteCommentFragment writeCommentFragment;
    TabLayout cmt ;
    ViewPager viewPager;
    Activity context;
    Product Productkey;
    @SuppressLint("ValidFragment")
    public commentFragment(Activity context, Product productkey){
        this.context = context;
        this.Productkey = productkey;
        Comment_FullCommentFragment fullCommentFragment = new Comment_FullCommentFragment(context,Productkey);
        Comment_MyCommentFragment myCommentFragment = new Comment_MyCommentFragment(context,Productkey, "Cy95MWqeTGbeqe9vNIHZVUkpe5i2");
        Comment_WriteCommentFragment writeCommentFragment = new Comment_WriteCommentFragment(Productkey);
    }
    public commentFragment() {
    }

//    FragmentPagerAdapter fragmentPagerAdapter;
//    NewProductDetailActivity.ViewPagerAdapter adapter;
//    @SuppressLint("ValidFragment")
//    public commentFragment(NewProductDetailActivity.ViewPagerAdapter adapter) {
//        super();
//        this.adapter = adapter;
//    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments,null);
        cmt = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewpagerComment);
        setupViewPager(viewPager);
        cmt.setupWithViewPager(viewPager);
        setupTabIcons();
        firebaseAuth= FirebaseAuth.getInstance();

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter1 adapter = new ViewPagerAdapter1(getChildFragmentManager());
        adapter.addFragment(fullCommentFragment,"abc");

      //  try{
        //    if(firebaseAuth.getCurrentUser() != null){
                adapter.addFragment(myCommentFragment,"abc");
                adapter.addFragment(writeCommentFragment,"abc");
          //  }
        //}
        //catch (Exception e){

        //}
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    private void setupTabIcons() {
        cmt.getTabAt(0).setText("Bình luận");
     //   try {
         //   if (firebaseAuth.getCurrentUser() != null) {
                cmt.getTabAt(1).setText("Bình luận của bạn");
                cmt.getTabAt(2).setText("Viết bình luận");
           // }
       // } catch (Exception e) {

        //}
    }
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class ViewPagerAdapter1 extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter1(FragmentManager manager) {
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
