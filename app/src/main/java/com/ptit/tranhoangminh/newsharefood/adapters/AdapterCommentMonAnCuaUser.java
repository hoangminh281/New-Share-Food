package com.ptit.tranhoangminh.newsharefood.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by TramLuc on 5/14/2018.
 */

public class AdapterCommentMonAnCuaUser extends BaseAdapter {
    Activity context;
    int Layout;
    ArrayList<CommentMA> cmtArr;
    String username = "";
    commentListener Callback;
    private DatabaseReference mData = FirebaseDatabase.getInstance().getReference("members");

    public AdapterCommentMonAnCuaUser(Activity context, int layout, ArrayList<CommentMA> cmtArr, commentListener callback) {
        this.context = context;
        Layout = layout;
        this.cmtArr = cmtArr;
        this.Callback = callback;
    }

    public ArrayList<CommentMA> getCmtArr() {
        return cmtArr;
    }

    public void setCmtArr(ArrayList<CommentMA> cmtArr) {
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
        TextView txtusername, txtcomment, txtcountlike;
        Button btndelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            final viewHolder holder;
            if (view == null) {
                holder = new viewHolder();
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                view = layoutInflater.inflate(Layout, null);
                holder.txtcomment = view.findViewById(R.id.txtComment);
                holder.txtcountlike = view.findViewById(R.id.txtcountlike);
                holder.txtusername = view.findViewById(R.id.txtUsername);
                holder.btndelete = view.findViewById(R.id.btndelete);
                view.setTag(holder);
            } else {
                holder = (viewHolder) view.getTag();
            }

            final CommentMA cmt = cmtArr.get(i);
            final int position = i;
            holder.txtusername.setText(username);
            holder.txtcomment.setText("Tiêu đề: " + cmt.getTieude() + "\n" + cmt.getBinhluan());
            holder.txtcountlike.setText(String.valueOf(cmt.getLike()));
         mData.child(cmt.getMemberId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String userName = dataSnapshot.child("hoten").getValue(String.class);
                    holder.txtusername.setText(userName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
            holder.btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Callback.delete(position);
                }
            });

        return view;
    }
}

