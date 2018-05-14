package com.ptit.tranhoangminh.newsharefood.presenters.DisplayStore;

import android.location.Location;
import android.util.Log;

import com.ptit.tranhoangminh.newsharefood.models.StoreModel;
import com.ptit.tranhoangminh.newsharefood.views.HomePageRes.StoreImp;

import java.util.List;

/**
 * Created by Dell on 5/6/2018.
 */

public class DisplayStorePresenterLogic implements DisplayStoreImp{
    StoreImp storeImp;
    StoreModel storeModel;
    public DisplayStorePresenterLogic(StoreImp storeImp)
    {
        this.storeImp=storeImp;
        storeModel=new StoreModel();
    }

    @Override
    public void DisplayListStore(Location current_location) {

            StoreInterface storeInterface=new StoreInterface() {
                @Override
                public void GetListStore(StoreModel storeModel) {

                    storeImp.GetStore(storeModel);
                }
            };
            storeModel.GetDanhSachQuanAn(storeInterface,current_location);
    }
}
