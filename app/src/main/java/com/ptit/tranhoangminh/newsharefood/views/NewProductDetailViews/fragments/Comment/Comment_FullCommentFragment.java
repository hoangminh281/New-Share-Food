package com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.adapters.AdapterCommentMonAn;
import com.ptit.tranhoangminh.newsharefood.models.Product;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by TramLuc on 5/13/2018.
 */

public class Comment_FullCommentFragment  extends Fragment implements commentListener {
    ListView listView;
    Product productkey;
    ArrayList<String> listLike = new ArrayList<>();
    AdapterCommentMonAn adapter;
    ArrayList<CommentMA> listCMT = new ArrayList<CommentMA>();
    ArrayList<String> listKey = new ArrayList<>();
    Activity context;
    DatabaseReference mData;
    public Comment_FullCommentFragment() {
        mData = FirebaseDatabase.getInstance().getReference();
    }

    public void setContent(Activity context, Product productkey){
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
        adapter = new AdapterCommentMonAn(context,R.layout.fullcomment_ma_layout,listCMT,listLike,this);

        getCommentProduct();
        listView.setAdapter(adapter);
        return view;
    }
    public void getCommentProduct(){
        mData.child("Binhluanmonans").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<CommentMA> arr = new ArrayList<CommentMA>();
                ArrayList<String> arrKey = new ArrayList<>();
                for (DataSnapshot item: dataSnapshot.getChildren()) {
                    System.out.println(item.getValue());
                    if(item.child("productId").getValue(String.class).equals(productkey.getId())){
                        arr.add(item.getValue(CommentMA.class));
                        Collection<String> key = item.getValue(CommentMA.class).getListLike().values();
                        listLike = new ArrayList<String>(key);
                        arrKey.add(item.getKey());
                    }
                }
                setListCMTnKey(arr,arrKey);
                System.out.println(arr);
                adapter.setListLike(new ArrayList<String>(listLike));
                adapter.setCmtArr(new ArrayList<CommentMA>(arr));
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setListCMTnKey(ArrayList<CommentMA> arr, ArrayList<String> arrKey){
        this.listCMT = arr;
        this.listKey = arrKey;
    }

    @Override
    public void likeAction(int position) {

        listCMT.get(position).setLike(listCMT.get(position).getLike()+1);
        mData.child("Binhluanmonans/"+listKey.get(position)).push().setValue(listCMT.get(position));
        mData.child("Binhluanmonans/listLike/").push().setValue(FirebaseAuth.getInstance().getCurrentUser());
    }


    @Override
    public void unlikeAction(int position) {
        listCMT.get(position).setLike(listCMT.get(position).getLike()-1);
        mData.child("Binhluanmonans/"+listKey.get(position)).push().setValue(listCMT.get(position));
        mData.child("Binhluanmonans/listLike/" + FirebaseAuth.getInstance().getUid()).removeValue();
    }

    @Override
    public void delete(int position) {    }
}
