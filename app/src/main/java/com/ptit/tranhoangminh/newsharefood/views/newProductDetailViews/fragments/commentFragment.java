package com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.Comment_FullCommentFragment;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.Comment_MyCommentFragment;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.Comment_WriteCommentFragment;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    Comment_FullCommentFragment fullCommentFragment;
    Comment_MyCommentFragment myCommentFragment;
    Comment_WriteCommentFragment writeCommentFragment;
    TabLayout cmt;
    ViewPager viewPager;
    Activity context;
    Product Productkey;
    @SuppressLint("ValidFragment")
    public CommentFragment(Activity context, Product productkey){
        this.context = context;
        this.Productkey = productkey;
        Comment_FullCommentFragment fullCommentFragment = new Comment_FullCommentFragment();
        Comment_MyCommentFragment myCommentFragment = new Comment_MyCommentFragment();
        Comment_WriteCommentFragment writeCommentFragment = new Comment_WriteCommentFragment();
    }
    public CommentFragment() {
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
        View view = inflater.inflate(R.layout.fragment_comment,null);
//
//        System.out.println(view.toString());
        cmt = view.findViewById(R.id.tabs);
        viewPager = view.findViewById(R.id.viewPagerCmt);
//
        setupViewPager(viewPager);
//        System.out.println("555555555555");
        cmt.setupWithViewPager(viewPager);
//        System.out.println("66666666666");
        setupTabIcons();
//        System.out.println("7777777777777");
        return view;

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter1 adapter = new ViewPagerAdapter1(getChildFragmentManager());
        Comment_FullCommentFragment fullcmt = new Comment_FullCommentFragment();
        fullcmt.setContent(context,Productkey);
        adapter.addFragment(fullcmt,"tieude");
        try{
            if(firebaseAuth.getCurrentUser() != null){
                Comment_MyCommentFragment mycmt= new Comment_MyCommentFragment();
                mycmt.setContent(context,Productkey, Productkey.getId());
                adapter.addFragment(mycmt,"MyComment");
                Comment_WriteCommentFragment writecmt = new Comment_WriteCommentFragment();
                writecmt.setContent(context,Productkey);
                adapter.addFragment(writecmt,"WriteComment");
            }
        }
        catch (Exception e){}
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    private void setupTabIcons() {
        cmt.getTabAt(0).setText("Bình luận");
        try {
            if (firebaseAuth.getCurrentUser() != null) {
                cmt.getTabAt(1).setText("Bình luận của bạn");
                cmt.getTabAt(2).setText("Viết bình luận");
            }
        } catch (Exception e) {}
    }
    public void onPointerCaptureChanged(boolean hasCapture) {    }


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
