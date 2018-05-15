package com.ptit.tranhoangminh.newsharefood.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.views.newProductDetailViews.fragments.Comment.CommentMA;

import java.util.ArrayList;

/**
 * Created by TramLuc on 5/14/2018.
 */

public class AdapterCommentMonAn extends BaseAdapter {
    Activity context;
    int Layout;
    ArrayList<CommentMA> cmtArr;

    public AdapterCommentMonAn(Activity context,int layout, ArrayList<CommentMA> cmtArr){
        this.context = context;
        Layout =layout;
        this.cmtArr =cmtArr;
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
        Button btnlike, btnreply;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder holder;
        if(view == null) {
            holder = new viewHolder();
            LayoutInflater layoutInflater =LayoutInflater.from(context);
            view = layoutInflater.inflate(Layout,null);
            holder.txtcomment = view.findViewById(R.id.txtComment);
            holder.txtusername = view.findViewById(R.id.txtUsername);
            holder.txtlike = view.findViewById(R.id.txtLike);
            holder.btnlike = view.findViewById(R.id.btnLike);
            holder.btnreply = view.findViewById(R.id.btnreply);
            view.setTag(holder);
        }
        else{
            holder = (viewHolder) view.getTag();
        }

        final CommentMA cmt = cmtArr.get(i);
        holder.txtusername.setText(cmt.getMembername());
        holder.txtcomment.setText(cmt.getTieude() + cmt.getBinhluan());
        holder.txtlike.setText(cmt.getLike() + cmt.getLike());
        return view;
    }
}
