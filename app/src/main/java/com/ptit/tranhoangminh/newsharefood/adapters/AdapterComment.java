package com.ptit.tranhoangminh.newsharefood.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.CommentModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {

    Context context;
    int layout;
    List<CommentModel> commentModelList;
    List<Bitmap>bitmapList;


    public AdapterComment(Context context, int layout, List<CommentModel> commentModelList){
        this.context = context;
        this.layout = layout;
        this.commentModelList = commentModelList;
        bitmapList=new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView txtTieuDeBinhLuan,txtNoiDungBinhLuan,txtSoDiem;
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.imgUser);
            txtTieuDeBinhLuan = (TextView) itemView.findViewById(R.id.txtTieude);
            txtNoiDungBinhLuan = (TextView) itemView.findViewById(R.id.txtNoidung);
            txtSoDiem = (TextView) itemView.findViewById(R.id.txtDiem1);
            recyclerView=itemView.findViewById(R.id.recycleViewHinhBL);
        }
    }

    @Override
    public AdapterComment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterComment.ViewHolder holder, int position) {
        final CommentModel binhLuanModel = commentModelList.get(position);
        holder.txtTieuDeBinhLuan.setText(binhLuanModel.getTieude());
        holder.txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
        holder.txtSoDiem.setText(binhLuanModel.getChamdiem() + "");
        setHinhAnhBinhLuan(holder.circleImageView,binhLuanModel.getMemberModel().getHinhanh());

        //duyệt mảng string hình comment-- bỏ vào list bitmap--truyền qua adapter recycle view image commment
        for(String linkhinh:binhLuanModel.getListImageComment())
        {
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("images").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    bitmapList.add(bitmap);
                    if(bitmapList.size()==binhLuanModel.getListImageComment().size())
                    {
                        AdpaterRecycleViewImageComment adapter=new AdpaterRecycleViewImageComment(context,R.layout.custom_layout_image_comment,bitmapList,binhLuanModel,false                            );
                        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(context,2);
                        holder.recyclerView.setLayoutManager(layoutManager);
                        holder.recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }
            });
        }

    }

    //quán ăn có trên 2 bl thì chỉ hiện thị 2 bl !

    @Override
    public int getItemCount() {
        int soBinhLuan = commentModelList.size();
        if(soBinhLuan > 2){
            return 2;
        }else{
            return commentModelList.size();
        }
    }

    //load hình từ firebase
    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh){
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
}
