package com.ptit.tranhoangminh.newsharefood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.tranhoangminh.newsharefood.models.MemberModel;
import com.ptit.tranhoangminh.newsharefood.views.HomePageRes.HomePageResActivity;
import com.ptit.tranhoangminh.newsharefood.views.Register.RegisterActivity;

/**
 * Created by Dell on 3/12/2018.
 */

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FirebaseAuth.AuthStateListener {
    Button btnGoogle, btnLogin, btnDK;
    GoogleApiClient apiClient;
    public static int CODE_GG = 261;
    public static int CHECK_AUTHENTICATION = 0;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    EditText edtEmail, edtPass;
    TextView tvReset;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login);
        firebaseAuth.signOut();
        SetControl();
        btnGoogle.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvReset.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("userId_login", MODE_PRIVATE);
        CreateClientLoginGG();
    }

    private void SetControl() {
        btnGoogle = findViewById(R.id.btnGoogleLogin);
        edtEmail = findViewById(R.id.edtEmailLogin);
        edtPass = findViewById(R.id.edtPassLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvReset = findViewById(R.id.idResetPassword);
//        btnDK=findViewById(R.id.btnDangKi);
    }

    public void CreateClientLoginGG() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    public void LoginGG(GoogleApiClient apiClient) {
        CHECK_AUTHENTICATION = 1;
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(intent, CODE_GG);
    }

    public void FirebaseLoginAuthentication(String token_id) {
        if (CHECK_AUTHENTICATION == 1) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(token_id, null);
            firebaseAuth.signInWithCredential(authCredential);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_GG) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                final GoogleSignInAccount signInAccount = googleSignInResult.getSignInAccount();
                final String tokenID = signInAccount.getIdToken();
                FirebaseLoginAuthentication(tokenID);
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnGoogleLogin:
                LoginGG(apiClient);
                break;
            case R.id.btnLogin:
                Login();
                break;
            case R.id.btnDangKi:
                Intent iDK = new Intent(this, RegisterActivity.class);
                startActivity(iDK);
                break;
            case R.id.idResetPassword:
                String mail = edtEmail.getText().toString();
                firebaseAuth.sendPasswordResetEmail(mail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Email empty or not exists", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }

    private void Login() {
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        if (email.length()==0) {
            edtEmail.setError("Empty email");
        }
        else if (pass.length() == 0) {
            edtPass.setError("Empty email");
        }
        else {
            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //method: know status of user login or log out
    @Override
    public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            //luu user_id
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_id", firebaseUser.getUid());
            editor.commit();
            mRef.child("members/" + firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        mRef.child("members/" + firebaseUser.getUid()).setValue(new MemberModel(firebaseUser.getDisplayName(), "user.png", firebaseUser.getEmail(), "", "", ""));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, "Cannot create member", Toast.LENGTH_SHORT).show();
                }
            });
            Intent in = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(in);
            finish();
        }
    }

    public void setOnclickRegisterListener(View view) {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }

    public void setOnclickHomeListener(View view) {
        Intent register = new Intent(this, MainActivity.class);
        startActivity(register);
        finish();
    }
}
