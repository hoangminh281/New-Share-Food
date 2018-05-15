package com.ptit.tranhoangminh.newsharefood.presenters.searchProductPresenters;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ptit.tranhoangminh.newsharefood.database.DatabaseHelper;
import com.ptit.tranhoangminh.newsharefood.models.Product;

import java.util.ArrayList;

/**
 * Created by Harrik on 5/9/2018.
 */

public class SearchProductInteractor {
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    private DatabaseHelper db;
    private LoadSearchProductListener listener;

    public SearchProductInteractor(LoadSearchProductListener listener, Context context) {
        this.listener = listener;
        db = new DatabaseHelper(context);
    }

    public void createSearchProductList(final String name) {
        final ArrayList<Product> productList = new ArrayList<>();
        myRef.child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //productList.clear();
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot item : iterable) {
                    Product pd = item.getValue(Product.class);
                    if(pd.getName().toLowerCase().startsWith(name.toLowerCase())) {
                        productList.add(pd);
                    }
                }
                listener.onLoadSearchProductsSuccess(productList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onLoadSearchProductsFailure("Failed to read products. " + databaseError.toException());
            }
        });
    }
}
