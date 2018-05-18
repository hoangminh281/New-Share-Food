package com.ptit.tranhoangminh.newsharefood;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.ptit.tranhoangminh.newsharefood.views.CategoryViews.activities.CategoryActivity;
import com.ptit.tranhoangminh.newsharefood.presenters.Mail.SendMail;

/**
 * Created by Dell on 3/12/2018.
 */

public class Splashscreen extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Button btnVaobep, btnDangNhap;
    GoogleApiClient googleApiClient;
    public static final int REQUEST_PERMISSION_LOCATION = 1;
    SharedPreferences sharedPreferences;

    FloatingActionButton fab, fab2, fab3;
    Boolean isOpen = false;
    final static String SHARED_PREFERENCES_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen_layout);
        if (getFirstApp()) {
            Intent intent = new Intent(Splashscreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            setFirstApp();
        }
        AnhXa();

        // test send mail
        sendEmail();

        //tạo 1 api yeu cau truy cap location service
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        //xin quyen .kiem tra xem nguoi dung da cap quyen chua.chua thi hien thi bang deny or allow
        int checkPermissionCoarseLocaltion = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int checkPermissionFineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (checkPermissionCoarseLocaltion != PackageManager.PERMISSION_GRANTED && checkPermissionFineLocation != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);
        } else {
            googleApiClient.connect();
        }

        //float action button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen) {
                    fab2.hide();
                    fab3.hide();
                    fab2.setClickable(false);
                    fab3.setClickable(false);
                    isOpen = false;
                }else {
                    fab2.show();
                    fab3.show();
                    fab2.setClickable(true);
                    fab3.setClickable(true);
                    isOpen = true;
                }
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Splashscreen.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    //kiem tra vs request code tren.neu allow thi connect
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    googleApiClient.connect();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        @SuppressLint("MissingPermission")
        Location vitriHienTai = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        //Log.d("vt", vitriHienTai.getLatitude() + "");
        if (vitriHienTai != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("latitude", String.valueOf(vitriHienTai.getLatitude()));
            editor.putString("longitude", String.valueOf(vitriHienTai.getLongitude()));
            editor.commit();
            Log.d("vitri", vitriHienTai.getLatitude() + " - " + vitriHienTai.getLongitude());
        }
        btnVaobep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDangNhap = new Intent(Splashscreen.this, MainActivity.class);
                startActivity(iDangNhap);
                finish();
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDangNhap = new Intent(Splashscreen.this, LoginActivity.class);
                startActivity(iDangNhap);
                finish();
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void AnhXa() {
        btnVaobep = findViewById(R.id.btnVaobep);
        btnDangNhap = findViewById(R.id.btnDangnhap);
        sharedPreferences = getSharedPreferences("toado", MODE_PRIVATE);

        // float action button
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
    }

    private void sendEmail() {
        //Creating SendMail object
        SendMail sm = new SendMail(this, "abc@gmail.com", "test mail", "hello");

        //Executing sendmail to send email
        sm.execute();
    }

    private void setFirstApp() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IS_FIRTS_LAUNCHER",true);
        if (!editor.commit()) {
            Toast.makeText(this, "Cannot save first time app", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean getFirstApp() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("IS_FIRTS_LAUNCHER",false);
    }
}
