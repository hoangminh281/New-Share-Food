package com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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

public class Comment_FullCommentFragment  extends Fragment implements commentListener {
    ListView listView;
    Product productkey;
    ArrayList<String> listUsername = new ArrayList<>();
    ArrayList<String> listImgId = new ArrayList<>();
    AdapterCommentMonAn adapter;
    ArrayList<CommentMA> listCMT = new ArrayList<CommentMA>();
    ArrayList<String> listKey = new ArrayList<>();
    Activity context;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mDataUser = FirebaseDatabase.getInstance().getReference("members");
    int newPosi = 0;

    public Comment_FullCommentFragment() {
        mData = FirebaseDatabase.getInstance().getReference();
    }

    public void setContent(Activity context, Product productkey) {
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
        adapter = new AdapterCommentMonAn(context, R.layout.fullcomment_ma_layout, listCMT,this);

        getCommentProduct();
        listView.setAdapter(adapter);
        return view;
    }

    public void getCommentProduct() {
        try {
            mData.child("Binhluanmonans").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newPosi = listView.getFirstVisiblePosition();
                    listImgId.clear();
                    listUsername.clear();
                    listCMT.clear();
                    listKey.clear();
                    ArrayList<CommentMA> arr = new ArrayList<>();
                    ArrayList<String> arrKey = new ArrayList<>();
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        if (item.child("productId").getValue(String.class).equals(productkey.getId())) {
                            CommentMA temp = item.getValue(CommentMA.class);
                            listCMT.add(temp);
                            listKey.add(item.getKey());
                        }
                    }
                    adapter.setNewData(listCMT);
                    adapter.notifyDataSetChanged();
                    if (arr.size() >= newPosi + 1) {
                        listView.setSelection(newPosi);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void likeAction(int position) {

        listCMT.get(position).setLike(listCMT.get(position).getLike() + 1);
        mData.child("Binhluanmonans/" + listKey.get(position)).setValue(listCMT.get(position));
        mData.child("Binhluanmonans/" + listKey.get(position) + "/listLike/" + FirebaseAuth.getInstance().getUid()).setValue(FirebaseAuth.getInstance().getUid());
    }


    @Override
    public void unlikeAction(int position) {
        listCMT.get(position).setLike(listCMT.get(position).getLike() - 1);
        mData.child("Binhluanmonans/" + listKey.get(position)).setValue(listCMT.get(position));
        mData.child("Binhluanmonans/" + listKey.get(position) + "/listLike/" + FirebaseAuth.getInstance().getUid()).removeValue();

    }
    @Override
    public void delete(int position) {

    }
}

