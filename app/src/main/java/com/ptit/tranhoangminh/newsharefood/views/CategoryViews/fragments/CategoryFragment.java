package com.ptit.tranhoangminh.newsharefood.views.CategoryViews.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.adapters.CategoryAdapter;
import com.ptit.tranhoangminh.newsharefood.models.Category;
import com.ptit.tranhoangminh.newsharefood.presenters.categoryPresenters.CategoryPresenter;
import com.ptit.tranhoangminh.newsharefood.views.CategoryViews.activities.CategoryActivity;
import com.ptit.tranhoangminh.newsharefood.views.CategoryViews.activities.CategoryView;
import com.ptit.tranhoangminh.newsharefood.views.ProductViews.activities.ProductActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment implements CategoryView {
    ListView lvMonAn;
    ProgressBar pgbCategory;
    CategoryPresenter categoryPresenter;
    private List<Category> categoriesList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_layout, null);
        setControl(view);
        initPresenter();
        categoryPresenter.loadCategories();
        setEvents();
        return view;
    }


    private void setControl(View view) {
        lvMonAn = view.findViewById(R.id.lvMonAn);
        pgbCategory = view.findViewById(R.id.progressBarCategories);
    }

    private void initPresenter() {
        categoryPresenter = new CategoryPresenter(this);
    }

    private void setEvents() {
        lvMonAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", categoriesList.get(i).getId());
                bundle.putString("name", categoriesList.get(i).getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
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
    public void displayMessage(String message) {
        if (getActivity()!=null) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void displayCategories(ArrayList<Category> categoriesList) {
        this.categoriesList = categoriesList;
        lvMonAn.setAdapter(new CategoryAdapter(getActivity(), R.layout.dong_categories, categoriesList));
    }
}
