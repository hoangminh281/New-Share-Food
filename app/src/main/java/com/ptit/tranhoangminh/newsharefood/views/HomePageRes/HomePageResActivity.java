package com.ptit.tranhoangminh.newsharefood.views.HomePageRes;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.ptit.tranhoangminh.newsharefood.adapters.AdapterRecycleViewStore;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.StoreModel;
import com.ptit.tranhoangminh.newsharefood.presenters.DisplayStore.DisplayStorePresenterLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 5/5/2018.
 */

public class HomePageResActivity extends AppCompatActivity implements StoreImp {
    StoreModel storeModel;
    RecyclerView recyclerView;
    AdapterRecycleViewStore adapterRecycleViewStore;
    List<StoreModel> list;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_res_layout);
        AddControl();


        //get location
        sharedPreferences = getSharedPreferences("toado", MODE_PRIVATE);
        Log.d("vt", sharedPreferences.getString("latitude", ""));
        Location current_location = new Location("");
        current_location.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        current_location.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));

        //thuc thi get quan an
        DisplayStorePresenterLogic d = new DisplayStorePresenterLogic(HomePageResActivity.this);
        d.DisplayListStore(current_location);
    }

    private void AddControl() {
        recyclerView = findViewById(R.id.recycleViewStore);
        progressBar = findViewById(R.id.progress);
        list = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void GetStore(StoreModel storeModel) {
//       Log.d("kiemtra",storeModel.getTenquan());
        list.add(storeModel);
        Log.d("size", list.size() + "");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomePageResActivity.this);
        adapterRecycleViewStore = new AdapterRecycleViewStore(list, R.layout.custom_res_layout, HomePageResActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterRecycleViewStore);
        adapterRecycleViewStore.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }
}
