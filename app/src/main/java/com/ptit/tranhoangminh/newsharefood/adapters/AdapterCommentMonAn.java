package com.ptit.tranhoangminh.newsharefood.adapters;

import android.app.Activity;
import android.media.Image;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseArray;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.reference.FirebaseReference;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.CommentMA;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.commentListener;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.CommentMA;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by TramLuc on 5/14/2018.
 */

public class AdapterCommentMonAn extends BaseAdapter {
    Activity context;
    int Layout;
    ArrayList<CommentMA> cmtArr;
    ArrayList<String> listLike;
    commentListener Callback;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    public AdapterCommentMonAn(Activity context, int layout, ArrayList<CommentMA> cmtArr, commentListener callback) {
        this.context = context;
        this.Layout = layout;
        this.cmtArr = cmtArr;
        this.Callback = callback;
    }

    public ArrayList<CommentMA> getCmtArr() {
        return cmtArr;
    }

    public void setNewData(ArrayList<CommentMA> cmtArr) {
        this.cmtArr = cmtArr;
    }

    @Override
    public int getCount() {
        if (cmtArr.isEmpty()) {
            return 0;
        }
        return cmtArr.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    class viewHolder {
        TextView txtusername, txtcomment, txtlike;
        Button btnlike;
        ImageView imgUser;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final viewHolder holder;
        final CommentMA cmt = cmtArr.get(i);
        Collection<String> key = cmt.getListLike().values();
        listLike = new ArrayList<>(key);
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(Layout, null);
            holder = new viewHolder();
            holder.txtcomment = view.findViewById(R.id.txtComment);
            holder.txtusername = view.findViewById(R.id.txtUsername);
            holder.txtlike = view.findViewById(R.id.txtLike);
            holder.btnlike = view.findViewById(R.id.btnLike);
            holder.imgUser = view.findViewById(R.id.imgUser);

            view.setTag(holder);
        }
        else {
            holder = (viewHolder) view.getTag();
        }
        holder.txtcomment.setText(cmt.getTieude() + "\n" + cmt.getBinhluan());
        holder.txtlike.setText(String.valueOf(cmt.getLike()));
        mData.child("members/" + cmt.getMemberId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userName = dataSnapshot.child("hoten").getValue(String.class);
                    String imgId = dataSnapshot.child("hinhanh").getValue(String.class);
                    holder.txtusername.setText(userName);
                    FirebaseReference.setImageFromFireBase(mStorageRef.child("thanhvien/" + imgId), imgId, ".png", holder.imgUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final int position = i;
        if (user != null) {
            if (listLike.contains(user.getUid())) {
                holder.btnlike.setText("Bỏ thích");
                holder.btnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Callback.unlikeAction(position);
                    }

                });
            } else {
                holder.btnlike.setText("Thích");
                holder.btnlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Callback.likeAction(position);
                    }
                });
            }
        } else {
            holder.btnlike.setVisibility(View.INVISIBLE);
        }
        return view;
    }
}
