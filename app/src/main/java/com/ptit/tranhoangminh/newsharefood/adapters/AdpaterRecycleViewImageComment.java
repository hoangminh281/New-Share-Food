package com.ptit.tranhoangminh.newsharefood.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.CommentModel;
import com.ptit.tranhoangminh.newsharefood.views.CommentDetail.ChiTietBinhLuanActivity;

import org.w3c.dom.Text;

import java.util.List;

public class AdpaterRecycleViewImageComment extends RecyclerView.Adapter<AdpaterRecycleViewImageComment.ViewHolder> {
    Context context;
    int layout;
    List<Bitmap> bitmapList;
    CommentModel commentModel;
    boolean isCommentDetail;

    public AdpaterRecycleViewImageComment(Context context, int layout, List<Bitmap> listImage, CommentModel commentModel, boolean s) {
        this.context = context;
        this.layout = layout;
        this.bitmapList = listImage;
        this.commentModel = commentModel;
        this.isCommentDetail = s;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new AdpaterRecycleViewImageComment.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(bitmapList.get(position));
        //nếu là hình ở vt cuối cùng thì hiển thị text + số hình còn lại trong list
        //kiểm tra: đang ở page Chitiet_binhluan hay page chitiet_quanan
        //truyen commentmodel qua page chitiet_binhhluan
        if (!isCommentDetail) {
            if (position == 3) {

                int count = bitmapList.size() - 4;
                if (count > 0) {
                    holder.txt.setVisibility(View.VISIBLE);
                    holder.txt.setText("+" + count);
                    holder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent iChitiet = new Intent(context, ChiTietBinhLuanActivity.class);
                            iChitiet.putExtra("comment_model", commentModel);
                            context.startActivity(iChitiet);
                        }
                    });
                }

            }

        }

    }

    //4 hình --commment
    @Override
    public int getItemCount() {
        if (!isCommentDetail) {
            return 4;
        } else {
            return bitmapList.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txt;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgBinhLuan);
            txt = itemView.findViewById(R.id.txtSoHinhBL);
        }
    }
}
