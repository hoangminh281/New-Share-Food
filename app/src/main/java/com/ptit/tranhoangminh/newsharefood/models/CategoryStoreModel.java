package com.ptit.tranhoangminh.newsharefood.models;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.tranhoangminh.newsharefood.presenters.menuStorePresenters.MenuInterface;

import java.util.ArrayList;
import java.util.List;

public class CategoryStoreModel {
    String cstegory_id;
    String category_name;
    List<MenuStoreModel> menuStoreModelListl;

    public CategoryStoreModel() {
    }

    public CategoryStoreModel(String cstegory_id, String category_name) {
        this.cstegory_id = cstegory_id;
        this.category_name = category_name;
    }

    public String getCstegory_id() {
        return cstegory_id;
    }

    public void setCstegory_id(String cstegory_id) {
        this.cstegory_id = cstegory_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public List<MenuStoreModel> getMenuStoreModelListl() {
        return menuStoreModelListl;
    }

    public void setMenuStoreModelListl(List<MenuStoreModel> menuStoreModelListl) {
        this.menuStoreModelListl = menuStoreModelListl;
    }

    //get ra menu của quán ăn
    public void getMenuStore(final MenuInterface menuInterface, String key_store) {
        DatabaseReference mrefMenu = FirebaseDatabase.getInstance().getReference().child("MenuStore").child(key_store);
        mrefMenu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                // Log.d("menu",dataSnapshot+"");
                //1 quán có nhiều thuc don
                final List<CategoryStoreModel>listMenu=new ArrayList<>();
                for (DataSnapshot valueMenu : dataSnapshot.getChildren()) {
                    final CategoryStoreModel categoryStoreModel = new CategoryStoreModel();
                    DatabaseReference mrefCategory = FirebaseDatabase.getInstance().getReference().child("CategoriesStore").child(valueMenu.getKey());
                    mrefCategory.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotCategory) {
                            //Log.d("category",dataSnapshot+"");
                            String category_id = dataSnapshotCategory.getKey();
                            categoryStoreModel.setCstegory_id(category_id);
                            categoryStoreModel.setCategory_name(dataSnapshotCategory.getValue(String.class));
                            //Log.d("product",category_id+"");
                            //1 thuc don co nhieu mon an
                            List<MenuStoreModel> product_list = new ArrayList<>();
                            for (DataSnapshot valueProduct : dataSnapshot.child(category_id).getChildren()) {
                                MenuStoreModel product = valueProduct.getValue(MenuStoreModel.class);
                                product.setProduct_id(valueProduct.getKey());
                                product_list.add(product);
                                // Log.d("product",product.getName()+"");

                            }
                            categoryStoreModel.setMenuStoreModelListl(product_list);
                            listMenu.add(categoryStoreModel);
                            menuInterface.getMenuStore(listMenu);
                            //Log.d("menu",dataSnapshot.child(category_id).getValue()+"");

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
