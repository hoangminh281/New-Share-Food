package com.ptit.tranhoangminh.newsharefood.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.BranchModel;
import com.ptit.tranhoangminh.newsharefood.models.CommentModel;
import com.ptit.tranhoangminh.newsharefood.models.StoreModel;
import com.ptit.tranhoangminh.newsharefood.views.AddComment.AddCommentActivity;
import com.ptit.tranhoangminh.newsharefood.views.Orderfood.MyCartActivity;
import com.ptit.tranhoangminh.newsharefood.views.StoreDetail.ChiTietQuanAnActivity;
import com.ptit.tranhoangminh.newsharefood.views.StoreDetail.StoreDetailActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Dell on 5/6/2018.
 */

public class AdapterRecycleViewStore extends RecyclerView.Adapter<AdapterRecycleViewStore.ViewHolder> {
    List<StoreModel> storeModelList;
    int layout;
    Context context;

    public AdapterRecycleViewStore(List<StoreModel> storeModelList, int layout, Context context) {
        this.storeModelList = storeModelList;
        this.layout = layout;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final StoreModel storeModel = storeModelList.get(position);

        holder.txtTenQuanAn.setText(storeModel.getTenquanan());

        //store có giao hàng thì button order display-------------
        if (storeModel.isGiaohang()) {
            holder.btnorder.setVisibility(View.VISIBLE);
        }

        //nếu như store đó có hình ảnh thì load---------------


        if (storeModel.getHinhanh().size() > 0) {

            StorageReference storageReferenceImage = FirebaseStorage.getInstance().getReference().child("images").child(storeModel.getHinhanh().get(0));
            Log.d("hung", storageReferenceImage.child("images").child(storeModel.getHinhanh().get(0)).getPath());
            try {
                final File localFile = File.createTempFile("images", "jpg");

                storageReferenceImage.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                final Bitmap[] bitmap = new Bitmap[1];

                                bitmap[0] = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                holder.imgHinh.setImageBitmap(bitmap[0]);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
            } catch (IOException e) {

            }

        }
        //kiểm tra store có comment thì hiển thị ra-----------------



        if (storeModel.getCommentModelList().size() > 0) {
           /* CommentModel commentModel = storeModel.getCommentModelList().get(0);
            holder.txtTieude.setText(commentModel.getTieude());
            holder.txtNoidung.setText(commentModel.getNoidung());
            holder.txtDiem1.setText(String.valueOf(commentModel.getChamdiem())+" point");
            Log.d("kt2", commentModel.getTieude());
            setImageUserComment(holder.circleImageView1, commentModel.getMemberModel().getHinhanh());
            if (storeModel.getCommentModelList().size() > 2) {
                CommentModel commentMode2 = storeModel.getCommentModelList().get(1);
                holder.txtTieude2.setText(commentMode2.getTieude());
                holder.txtNoidung2.setText(commentMode2.getNoidung());
                holder.txtDiem2.setText(String.valueOf(commentMode2.getChamdiem())+" point");
                setImageUserComment(holder.circleImageView2, commentMode2.getMemberModel().getHinhanh());
            }*/
            holder.txtTongBL.setText(storeModel.getCommentModelList().size() + "");
            int toatl_image_comment = 0;
            double total_point_comment = 0;
            for (CommentModel commentModel1 : storeModel.getCommentModelList()) {
                toatl_image_comment += commentModel1.getListImageComment().size();
                total_point_comment += commentModel1.getChamdiem();
            }
            double total = total_point_comment / storeModel.getCommentModelList().size();
            holder.txtDTB.setText(String.format("%.1f", total));
            if (toatl_image_comment > 0) {
                holder.txtTongHinh.setText(toatl_image_comment + "");
            } else {
                holder.txtTongHinh.setText("0");
            }


        } else {
            holder.txtTongHinh.setText("0");
            holder.txtTongBL.setText("0");
        }
        //get addree and distance----------
        if (storeModel.getBranchModelList().size() > 0) {
            BranchModel branchModel_tamp = storeModel.getBranchModelList().get(0);
            for (BranchModel b : storeModel.getBranchModelList()) {
                if (branchModel_tamp.getDistance() > b.getDistance()) {
                    branchModel_tamp = b;
                }
            }
            holder.txtDiaChi.setText(branchModel_tamp.getDiachi());
            holder.txtKC.setText(String.format("%.1f", branchModel_tamp.getDistance()) + " km");
        } else {

        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, storeModel.getTenquanan(), Toast.LENGTH_SHORT).show();
                Intent iChitiet = new Intent(context, ChiTietQuanAnActivity.class);
                iChitiet.putExtra("store", storeModel);
                context.startActivity(iChitiet);


            }
        });

    }

  /*  public void setImageUserComment(final CircleImageView circleImageView, String image_link) {
        StorageReference storageReference1 = FirebaseStorage.getInstance().getReference().child("thanhvien").child(image_link);
        long ONE_MEGABYTE = 1024 * 1024;
        storageReference1.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }*/

    @Override
    public int getItemCount() {
        return storeModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTenQuanAn, txtTieude, txtTieude2, txtNoidung, txtNoidung2, txtDiem1, txtDiem2, txtTongBL, txtTongHinh, txtDTB, txtDiaChi, txtKC;
        Button btnorder;
        ImageView imgHinh;
        CircleImageView circleImageView1, circleImageView2;
        LinearLayout bl1, bl2;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTenQuanAn = itemView.findViewById(R.id.txtTenQuanAn);
            btnorder = itemView.findViewById(R.id.btnOrder);
            imgHinh = itemView.findViewById(R.id.imgHinhQuanAn);
            //txtTieude = itemView.findViewById(R.id.txtTieude);
            //txtNoidung = itemView.findViewById(R.id.txtNoidung);
            //txtTieude2 = itemView.findViewById(R.id.txtTieude2);
           // txtNoidung2 = itemView.findViewById(R.id.txtNoidung2);
           // circleImageView1 = itemView.findViewById(R.id.imgUser);
           // circleImageView2 = itemView.findViewById(R.id.imgUser2);
           // bl1 = itemView.findViewById(R.id.linearBL1);
          //  bl2 = itemView.findViewById(R.id.linearBL2);
            //txtDiem1 = itemView.findViewById(R.id.txtDiem1);
           // txtDiem2 = itemView.findViewById(R.id.txtDiem2);
            txtTongBL = itemView.findViewById(R.id.txtTongBL);
            txtTongHinh = itemView.findViewById(R.id.txtTongHinh);
            txtDTB = itemView.findViewById(R.id.txtDTB);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            txtKC = itemView.findViewById(R.id.txtKhoangCach);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }

}
