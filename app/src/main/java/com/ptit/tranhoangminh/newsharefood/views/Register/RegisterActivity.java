package com.ptit.tranhoangminh.newsharefood.views.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ptit.tranhoangminh.newsharefood.LoginActivity;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.Splashscreen;
import com.ptit.tranhoangminh.newsharefood.models.MemberModel;
import com.ptit.tranhoangminh.newsharefood.presenters.registerPresenter.RegisterPresenterLogic;

/**
 * Created by Dell on 5/7/2018.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener,RegisterViewImp {
    Button btnDangKy;
    EditText edEmailDK,edPasswordDK,edPasswordConfirmDK,edUsername;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    RegisterPresenterLogic registerPresenterLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnDangKy = (Button) findViewById(R.id.btnRegister);
        edEmailDK = (EditText) findViewById(R.id.edtEmailRegister);
        edPasswordDK = (EditText) findViewById(R.id.edtPasswordLogin);
        edPasswordConfirmDK = (EditText) findViewById(R.id.edtPasswordConfirm);
        edUsername = (EditText)findViewById(R.id.edtUsername) ;

        btnDangKy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);

        progressDialog.show();
        final String username = edUsername.getText().toString();
        final String email = edEmailDK.getText().toString();
        final String matkhau = edPasswordDK.getText().toString();
        String nhaplaimatkhau = edPasswordConfirmDK.getText().toString();
        Log.d("A1",username);
        Log.d("A2",email);
        Log.d("A3",matkhau);
        Log.d("A4",nhaplaimatkhau);
        if(username.trim().length() == 0 ){
            Toast.makeText(this,"Username required",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
        else if(email.trim().length() == 0 ){
            Toast.makeText(this,"Email required",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else if(matkhau.trim().length() == 0){
            Toast.makeText(this,"Pass required",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else if(!nhaplaimatkhau.equals(matkhau)){
            Toast.makeText(this,"Pass2 must equal pass1",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }else{

            firebaseAuth.createUserWithEmailAndPassword(email,matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        MemberModel memberModel=new MemberModel();
                        memberModel.setHoten(username);
                        memberModel.setHinhanh(email);
//                        memberModel.setHinhanh("user.png");
                        String uid=task.getResult().getUser().getUid();
                        registerPresenterLogic=new RegisterPresenterLogic(RegisterActivity.this);
                        registerPresenterLogic.AddInfoMember(memberModel,uid);
                        Toast.makeText(RegisterActivity.this,"Success",Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(login);
                    }
                }
            });
        }
    }

    public void setOnclickLoginListener(View view) {
        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(login);
    }
    public void setOnclickBackListener(View view){
        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(login);
    }
}
