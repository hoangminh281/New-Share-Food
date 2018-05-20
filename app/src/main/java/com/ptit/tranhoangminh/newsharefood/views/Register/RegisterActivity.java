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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ptit.tranhoangminh.newsharefood.LoginActivity;
import com.ptit.tranhoangminh.newsharefood.MainActivity;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.Splashscreen;
import com.ptit.tranhoangminh.newsharefood.models.MemberModel;
import com.ptit.tranhoangminh.newsharefood.presenters.registerPresenter.RegisterPresenterLogic;

/**
 * Created by Dell on 5/7/2018.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RegisterViewImp {
    Button btnDangKy;
    EditText edEmailDK, edPasswordDK, edPasswordConfirmDK, edUsername, edPhone;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    RegisterPresenterLogic registerPresenterLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        btnDangKy = findViewById(R.id.btnRegister);
        edEmailDK = findViewById(R.id.edtEmailRegister);
        edPasswordDK = findViewById(R.id.edtPasswordLogin);
        edPasswordConfirmDK = findViewById(R.id.edtPasswordConfirm);
        edUsername = findViewById(R.id.edtUsername);
        edPhone = findViewById(R.id.edtPhone);
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
        final String phone = edPhone.getText().toString();
        if (username.trim().length() == 0) {
            Toast.makeText(this, "Username required", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (email.trim().length() == 0) {
            Toast.makeText(this, "Email required", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (matkhau.trim().length() == 0) {
            Toast.makeText(this, "Pass required", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (!nhaplaimatkhau.equals(matkhau)) {
            Toast.makeText(this, "Password not same", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else if (phone.trim().length() == 0) {
            Toast.makeText(this, "Phone required", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        MemberModel memberModel = new MemberModel(username, "user.png", email, phone, "", "");
                        registerPresenterLogic = new RegisterPresenterLogic(RegisterActivity.this);
                        registerPresenterLogic.AddInfoMember(memberModel, firebaseAuth.getCurrentUser().getUid());
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent login = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(login);
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setOnclickLoginListener(View view) {
        finish();
    }

    public void setOnclickBackListener(View view) {
        Intent login = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(login);
        finish();
    }
}
