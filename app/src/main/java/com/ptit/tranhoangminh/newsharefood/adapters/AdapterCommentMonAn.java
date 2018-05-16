package com.ptit.tranhoangminh.newsharefood.adapters;

import android.app.Activity;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.CommentMA;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.commentListener;

import java.util.ArrayList;

/**
 * Created by TramLuc on 5/14/2018.
 */

public class AdapterCommentMonAn extends BaseAdapter {
    Activity context;
    int Layout;
    ArrayList<CommentMA> cmtArr;
    ArrayList<String> listLike;
    commentListener Callback;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public AdapterCommentMonAn(Activity context,int layout, ArrayList<CommentMA> cmtArr, ArrayList<String> listLike, commentListener callback){
        this.context = context;
        Layout =layout;
        this.cmtArr =cmtArr;
        Callback = callback;
    }

    public ArrayList<CommentMA> getCmtArr() {
        return cmtArr;
    }

    public void setCmtArr(ArrayList<CommentMA> cmtArr) {
        this.cmtArr = cmtArr;
    }

    public ArrayList<String> getListLike() {
        return listLike;
    }

    public void setListLike(ArrayList<String> listLike) {
        this.listLike = listLike;
    }

    @Override
    public int getCount() {
        if(cmtArr.isEmpty()){
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

    class viewHolder{
        TextView txtusername, txtcomment, txtlike;
        Button btnlike ;
        ImageView imgUser;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder;
        try {
            if (view == null) {
                holder = new viewHolder();
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                view = layoutInflater.inflate(Layout, null);
                holder.txtcomment = view.findViewById(R.id.txtComment);
                holder.txtusername = view.findViewById(R.id.txtUsername);
                holder.txtlike = view.findViewById(R.id.txtLike);
                holder.btnlike = view.findViewById(R.id.btnLike);
                holder.imgUser = view.findViewById(R.id.imgUser);
                view.setTag(holder);
            } else {
                holder = (viewHolder) view.getTag();
            }

            final CommentMA cmt = cmtArr.get(i);
            final int position = i;
            holder.txtusername.setText(cmt.getMembername());
            holder.txtcomment.setText(cmt.getTieude() + cmt.getBinhluan());
            holder.txtlike.setText(String.valueOf(cmt.getLike()));
            holder.imgUser.setImageBitmap(cmt.getImgUser());

            if(user != null){
                if (listLike.contains(user.getUid())) {
                    holder.btnlike.setText("Bỏ Thích");
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
            }else{
                holder.btnlike.setVisibility(View.INVISIBLE);
            }
        }catch(Exception e){
        }
        return view;
    }
}
