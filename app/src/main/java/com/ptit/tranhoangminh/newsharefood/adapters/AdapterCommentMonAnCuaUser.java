package com.ptit.tranhoangminh.newsharefood.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.views.newProductDetailViews.fragments.Comment.CommentMA;

import java.util.ArrayList;

/**
 * Created by TramLuc on 5/14/2018.
 */

public class AdapterCommentMonAnCuaUser extends BaseAdapter {
    Activity context;
    int Layout;
    ArrayList<CommentMA> cmtArr;
    String username;

    public AdapterCommentMonAnCuaUser(Activity context,int layout, ArrayList<CommentMA> cmtArr,String username){
        this.context = context;
        Layout =layout;
        this.cmtArr = cmtArr;
        this.username = username;
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
            holder.txtcountlike = view.findViewById(R.id.txtcountlike);
            holder.txtusername = view.findViewById(R.id.txtUsername);
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
        holder.txtcountlike.setText(cmt.getLike());

        return view;
    }
}
