package com.ptit.tranhoangminh.newsharefood.views.AddComment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.CommentModel;
import com.ptit.tranhoangminh.newsharefood.models.StoreModel;
import com.ptit.tranhoangminh.newsharefood.presenters.saveCommentForStorePresenters.SaveCommentStorePresenterLogic;

//public class AddCommentActivity extends AppCompatActivity implements View.OnClickListener,AddCommentImp,RatingBar.OnRatingBarChangeListener{
public class AddCommentActivity extends AppCompatActivity implements View.OnClickListener,RatingBar.OnRatingBarChangeListener,AddCommentImp {
    ImageButton  txtPost_comment;
    Toolbar toolbar;
    RatingBar ratingBar;
    ImageView imageView;
    String key_store;
    EditText edtTitle,edtContent;
    SharedPreferences sharedPreferences;
    SaveCommentStorePresenterLogic saveCommentStorePresenterLogic;
    String link_image;
    public static int REQUEST_CODE_SET_COMMENT=998;
    long chamdiem=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_store_layout);
        AddControl();
        AddEvent();

        key_store = getIntent().getStringExtra("key_store");
        sharedPreferences=getSharedPreferences("userId_login",MODE_PRIVATE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void AddEvent() {
        imageView.setOnClickListener(this);
        txtPost_comment.setOnClickListener(this);
        ratingBar.setOnRatingBarChangeListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    private void AddControl() {

        toolbar = findViewById(R.id.toolbar_comment);
        imageView = findViewById(R.id.imgHinhABC);
        txtPost_comment = findViewById(R.id.txtPost_comment);
        edtTitle=findViewById(R.id.edtTitle_comment);
        edtContent=findViewById(R.id.edtContent_comment);
        ratingBar=findViewById(R.id.ratingbar_danhgia);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgHinhABC:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Chọn hình"), 11);
                break;
            case R.id.txtPost_comment:
                CommentModel commentModel=new CommentModel();
                String useId=sharedPreferences.getString("user_id","");
                commentModel.setTieude(edtTitle.getText().toString());
                commentModel.setNoidung(edtContent.getText().toString());
                commentModel.setChamdiem(chamdiem);
                commentModel.setMauser(useId);

                if(edtTitle.getText().toString()==null||edtTitle.getText().toString().equals(""))
                {
                    Toast.makeText(this, "Tiêu đề chưa thêm", Toast.LENGTH_SHORT).show();
                }else if(edtContent.getText().toString()==null||edtContent.getText().toString().equals("")){
                    Toast.makeText(this, "Nội dung chưa thêm", Toast.LENGTH_SHORT).show();
                }

                else if (imageView==null){
                    Toast.makeText(this, "bạn chưa thêm hình", Toast.LENGTH_SHORT).show();
                }else{
                    saveCommentStorePresenterLogic=new SaveCommentStorePresenterLogic(AddCommentActivity.this);
                    saveCommentStorePresenterLogic.saveComment(key_store,commentModel,imageView);
                }

                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            if (resultCode == RESULT_OK) {
                link_image=data.getData().toString();
                Log.d("dulieu",link_image);
                imageView.setImageURI(data.getData());
            }
        }

    }

    @Override
    public void getresult(String s) {
        Toast.makeText(this, "Thêm bình luận thành công..", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent();
        intent.putExtra("result","Thêm thành công..");
        setResult(REQUEST_CODE_SET_COMMENT,intent);
        finish();
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {
        if(rating==0)
        {
            chamdiem=0;
        }else if(rating==0.5)
        {
            chamdiem=1;
        }else if(rating==1.0)
        {
            chamdiem=2;
        }
        else if(rating==1.5)
        {
            chamdiem=3;
        }
        else if(rating==2.0)
        {
            chamdiem=4;
        }
        else if(rating==2.5)
        {
            chamdiem=5;
        }
        else if(rating==3.0)
        {
            chamdiem=6;
        }
        else if(rating==3.5)
        {
            chamdiem=7;
        }
        else if(rating==4.0)
        {
            chamdiem=8;
        }
        else if(rating==4.5)
        {
            chamdiem=9;
        }
        else if(rating==5.0)
        {
            chamdiem=10;
             Toast.makeText(this, ""+chamdiem, Toast.LENGTH_SHORT).show();
        }

    }
}
