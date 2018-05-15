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
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.CommentModel;
import com.ptit.tranhoangminh.newsharefood.presenters.saveCommentForStorePresenters.SaveCommentStorePresenterLogic;

public class AddCommentActivity extends AppCompatActivity implements View.OnClickListener,AddCommentImp {
    TextView txtNameStore, txtAddressStore, txtPost_comment;
    Toolbar toolbar;
    ImageButton image_Comment;
    ImageView imageView;
    String key_store;
    EditText edtTitle,edtContent;
    SharedPreferences sharedPreferences;
    SaveCommentStorePresenterLogic saveCommentStorePresenterLogic;
    String link_image;
    public static int REQUEST_CODE_SET_COMMENT=998;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_store_layout);
        AddControl();

        key_store = getIntent().getStringExtra("key_store");
        String name_store = getIntent().getStringExtra("name_store");
        String address_store = getIntent().getStringExtra("address_store");

        sharedPreferences=getSharedPreferences("userId_login",MODE_PRIVATE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txtNameStore.setText(name_store);
        txtAddressStore.setText(address_store);

        image_Comment.setOnClickListener(this);
        imageView.setOnClickListener(this);
        txtPost_comment.setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
    private void AddControl() {
        txtNameStore = findViewById(R.id.txtTenQuan_comment);
        txtAddressStore = findViewById(R.id.txtDiachi_comment);
        toolbar = findViewById(R.id.toolbar_comment);
        image_Comment = findViewById(R.id.image_comment);
        imageView = findViewById(R.id.imgHinhABC);
        txtPost_comment = findViewById(R.id.txtPost_comment);
        edtTitle=findViewById(R.id.edtTitle_comment);
        edtContent=findViewById(R.id.edtContent_comment);
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
                commentModel.setChamdiem(0);
                commentModel.setLuotthich(0);
                commentModel.setMauser(useId);
                saveCommentStorePresenterLogic=new SaveCommentStorePresenterLogic(AddCommentActivity.this);
                saveCommentStorePresenterLogic.saveComment(key_store,commentModel,link_image);
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
        intent.putExtra("result","Thêm thành công");
        setResult(REQUEST_CODE_SET_COMMENT,intent);
        finish();
    }
}
