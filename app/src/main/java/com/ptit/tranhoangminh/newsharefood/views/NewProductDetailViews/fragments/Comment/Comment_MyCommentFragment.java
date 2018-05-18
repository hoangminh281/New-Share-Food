package com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
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

public class Comment_MyCommentFragment extends Fragment implements commentListener {
    ListView listView;
    Product productkey;
    String idUser = FirebaseAuth.getInstance().getUid();
    Activity context;
    ArrayList<CommentMA> listCMT = new ArrayList<CommentMA>();
    ArrayList<String> listKey = new ArrayList<>();
    AdapterCommentMonAnCuaUser adapter;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();;

    public void setContent(Activity context, Product productkey){
        this.context = context;
        this.productkey = productkey;
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
        adapter = new AdapterCommentMonAnCuaUser(context,R.layout.mycomment_ma_layout,listCMT,this);
        getCommentUserProduct();
        listView.setAdapter(adapter);

        return view;
    }


    public void getCommentUserProduct(){
        mData.child("Binhluanmonans").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listCMT.clear();
                listKey.clear();
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    if(item.child("productId").getValue(String.class).equals(productkey.getId()) && item.child("memberId").getValue(String.class).equals(idUser)){
                        listCMT.add(item.getValue(CommentMA.class));
                        listKey.add(item.getKey());
                    }
                }
                adapter.setCmtArr(new ArrayList<CommentMA>(listCMT));
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void likeAction(int position) {
    }


    @Override
    public void unlikeAction(int position) {
    }

    @Override
    public void delete(int position) {
        mData.child("Binhluanmonans/"+ listKey.get(position)).removeValue();
    }


}
