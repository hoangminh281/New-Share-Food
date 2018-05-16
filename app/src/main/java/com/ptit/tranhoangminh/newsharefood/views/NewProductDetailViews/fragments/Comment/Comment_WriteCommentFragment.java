package com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.Product;
import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments.Comment.CommentMA;

/**
 * Created by TramLuc on 5/13/2018.
 */

@SuppressLint("ValidFragment")
public class Comment_WriteCommentFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    EditText tieude, binhluan;
    Button btnDang;
    Product productkey;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();;
    @SuppressLint("ValidFragment")
    public Comment_WriteCommentFragment(Product productkey) {
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
        mData = FirebaseDatabase.getInstance().getReference();
        binhluan = view.findViewById(R.id.txtBinhLuan);
        btnDang = view.findViewById(R.id.btnDang);
        btnDang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                writeCmt();
            }
        });
        return view;
    }
    public void writeCmt(){

        CommentMA cmt = new CommentMA(productkey.getId(),"Cy95MWqeTGbeqe9vNIHZVUkpe5i2",tieude.getText().toString(),binhluan.getText().toString(),"Nguyen Cuong",0);
        mData.child("Binhluanmonans").push().setValue(cmt);
    }
}
