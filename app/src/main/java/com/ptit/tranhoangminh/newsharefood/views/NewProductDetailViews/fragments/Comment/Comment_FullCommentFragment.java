package com.ptit.tranhoangminh.newsharefood.views.newProductDetailViews.fragments.Comment;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.adapters.AdapterCommentMonAn;
import com.ptit.tranhoangminh.newsharefood.models.Product;

import java.util.ArrayList;

/**
 * Created by TramLuc on 5/13/2018.
 */

public class Comment_FullCommentFragment  extends Fragment {
    ListView listView;
    Product productkey;
    AdapterCommentMonAn adapter;
    ArrayList<CommentMA> listCMT = new ArrayList<CommentMA>();
    Activity context;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    public Comment_FullCommentFragment() {
    }

    @SuppressLint("ValidFragment")
    public Comment_FullCommentFragment(Activity context, Product productkey) {
        this.context = context;
        this.productkey = productkey;
    }
    //@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cmt_fragment_fullcomments, null);
        listView = view.findViewById(R.id.listViewCmt);
        adapter = new AdapterCommentMonAn(context,R.layout.fullcomment_ma_layout,listCMT);
        getCommentProduct();
        listView.setAdapter(adapter);
        return view;
    }
    public void getCommentProduct(){
        mData.child("Binhluanmonans").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<CommentMA> arr = new ArrayList<CommentMA>();
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    if(item.child("productId").getValue().equals(productkey.getId())){
                        arr.add(item.getValue(CommentMA.class));
                    }
                }
                setListCMT(arr);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setListCMT(ArrayList<CommentMA> arr){
        this.listCMT = arr;
    }
}
