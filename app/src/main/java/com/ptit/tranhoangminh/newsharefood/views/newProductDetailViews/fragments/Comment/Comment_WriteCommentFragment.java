package com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.reference.FirebaseReference;

/**
 * Created by TramLuc on 5/13/2018.
 */

@SuppressLint("ValidFragment")
public class Comment_WriteCommentFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    EditText tieude, binhluan;
    Button btnDang;
    Product productkey;
    String username;
    Activity context;
    ImageView imgView;
    public Comment_WriteCommentFragment() {
    }

    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();

    public void setContent(Activity context, Product productkey) {
        this.context = context;
        this.productkey = productkey;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cmt_fragment_writecomment, null);
        tieude = view.findViewById(R.id.txtTieude);
        imgView = view.findViewById(R.id.imgUser);
        mData = FirebaseDatabase.getInstance().getReference();

        binhluan = view.findViewById(R.id.txtBinhLuan);
        btnDang = view.findViewById(R.id.btnDang);
        mData.child("members/" + FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                username = dataSnapshot.child("hoten").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btnDang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                writeCmt();

            }
        });
        return view;
    }

    public void writeCmt() {

        CommentMA cmt = new CommentMA(productkey.getId(), FirebaseAuth.getInstance().getUid(), tieude.getText().toString(), binhluan.getText().toString(), username, 0);
        // CommentMA cmt = new CommentMA(productkey.getId(), "Cy95MWqeTGbeqe9vNIHZVUkpe5i2",tieude.getText().toString(),binhluan.getText().toString(), username,0);
        mData.child("Binhluanmonans").push().setValue(cmt);
        tieude.setText("");
        binhluan.setText("");
    }
}
