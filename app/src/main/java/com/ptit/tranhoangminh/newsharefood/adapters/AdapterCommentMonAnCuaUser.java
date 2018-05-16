package com.ptit.tranhoangminh.newsharefood.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.CommentMA;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.commentListener;

import java.util.ArrayList;

/**
 * Created by TramLuc on 5/14/2018.
 */

public class AdapterCommentMonAnCuaUser extends BaseAdapter {
    Activity context;
    int Layout;
    ArrayList<CommentMA> cmtArr;
    ArrayList<String> listLike;
    String username;
    commentListener Callback;

    public AdapterCommentMonAnCuaUser(Activity context,int layout, ArrayList<CommentMA> cmtArr,String username,ArrayList<String> listLike, commentListener callback){
        this.context = context;
        Layout =layout;
        this.cmtArr = cmtArr;
        this.username = username;
        this.Callback = callback;
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
        TextView txtusername, txtcomment,txtcountlike;
        Button btnlike, btndelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            viewHolder holder;
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
            holder.txtusername.setText(cmt.getMembername());
            holder.txtcomment.setText(cmt.getTieude() + cmt.getBinhluan());
            holder.txtcountlike.setText(String.valueOf(cmt.getLike()));


            holder.btndelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Callback.delete(position);
                }
            });
        }catch (Exception e){}
        return view;
    }
}

