package com.ptit.tranhoangminh.newsharefood.presenters.menuStorePresenters;

import android.util.Log;

import com.ptit.tranhoangminh.newsharefood.models.CategoryStoreModel;
import com.ptit.tranhoangminh.newsharefood.views.StoreDetail.MenuImp;

import java.util.List;

public class MenuStorePresenterLogic implements MenuStoreImp {
    MenuImp menuImp;
    CategoryStoreModel categoryStoreModel;

    public MenuStorePresenterLogic(MenuImp menuImp) {
        this.menuImp = menuImp;
        categoryStoreModel = new CategoryStoreModel();
    }

    @Override
    public void getMenu(String key_store) {
        MenuInterface menuInterface = new MenuInterface() {
            @Override
            public void getMenuStore(List<CategoryStoreModel> list) {
                menuImp.getMenu(list);
                //Log.d("size_menu", list.size() + "");
            }
        };
        categoryStoreModel.getMenuStore(menuInterface, key_store);

    }
}
