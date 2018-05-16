package com.ptit.tranhoangminh.newsharefood.views.newProductDetailViews.fragments.Comment;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.adapters.AdapterCommentMonAnCuaUser;
import com.ptit.tranhoangminh.newsharefood.models.Product;

import java.util.ArrayList;

/**
 * Created by TramLuc on 5/13/2018.
 */

public class Comment_MyCommentFragment extends Fragment {
    ListView listView;
    Product productkey;
    String idUser;
    Activity context;
    ArrayList<CommentMA> listCMT = new ArrayList<CommentMA>();
    AdapterCommentMonAnCuaUser adapter;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();;

    @SuppressLint("ValidFragment")
    public Comment_MyCommentFragment(Activity context,Product productkey, String idUser) {
        this.context = context;
        this.productkey = productkey;
        this.idUser = idUser;
    }

    public Comment_MyCommentFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cmt_fragment_mycomments, null);
        listView = view.findViewById(R.id.listViewMyCmt);
        adapter = new AdapterCommentMonAnCuaUser(context,R.layout.mycomment_ma_layout,listCMT,idUser);
        listView.setAdapter(adapter);
        return view;
    }
    public void getCommentUserProduct(){
        mData.child("Binhluanmonans").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<CommentMA> arr = new ArrayList<CommentMA>();
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    if(item.child("productId").getValue().equals(productkey.getId()) && item.child("memberId").equals(idUser)){
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
