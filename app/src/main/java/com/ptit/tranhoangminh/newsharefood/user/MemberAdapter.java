package com.ptit.tranhoangminh.newsharefood.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.Product;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MemberAdapter extends BaseAdapter{
    private Context context;
    private int layout;
    private List<Product> productList;

    public MemberAdapter() {
    }

    public MemberAdapter(Context context, int layout, List<Product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        //anh xa view
        TextView txtTen = view.findViewById(R.id.txtTenMon);
        TextView txtNguoiTao = view.findViewById(R.id.txtnguoitao);
        ImageView imghinh = view.findViewById(R.id.imagemonan);
        //gan gia tri
        Product product = productList.get(i);
        txtTen.setText(product.getName());
        //txtNguoiTao.setText(product.getNguoitao());
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        setImageFromFireBase(mStorageRef.child("Products").child(product.getImage()), product.getId(), ".png", imghinh);
        return view;

    }
    void setImageFromFireBase(StorageReference mStorageRef, String prefix, String suffix, final ImageView img) {
        try {
            final File localFile = File.createTempFile(prefix, suffix);
            Log.d("FirebaseStorage", "createTempFile: success!");
            mStorageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            final Bitmap[] bitmap = new Bitmap[1];
                            Log.d("FirebaseStorage", "setImageFromFireBase: success!");
                            bitmap[0] = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            img.setImageBitmap(bitmap[0]);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("FirebaseStorage", "setImageFromFireBase: fail! " + exception.getMessage());
                }
            });
        } catch (IOException e) {
            Log.e("FirebaseStorage", "createTempFile: fail! " + e.toString());
        }
    }
}
