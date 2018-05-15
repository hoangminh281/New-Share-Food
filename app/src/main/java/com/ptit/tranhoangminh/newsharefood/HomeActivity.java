package com.ptit.tranhoangminh.newsharefood;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.views.fragment.Home1Fragment;
import com.ptit.tranhoangminh.newsharefood.views.fragment.Home2Fragment;
import com.ptit.tranhoangminh.newsharefood.views.fragment.Home3Fragment;
import com.ptit.tranhoangminh.newsharefood.views.fragment.Home4Fragment;
import com.ptit.tranhoangminh.newsharefood.views.fragment.HomeSearchFragment;

import java.util.ArrayList;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    Button btnhome1, btnhome2, btnhome3, btnhome4;
    ImageButton btnvoice;
    EditText edtSearch;
    ViewPager viewPager;

    String textSearch = "";
    Boolean mIsEmpty = false;

    final int REQUEST_CODE_VOICE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnhome1 = (Button) findViewById(R.id.btnhome1);
        btnhome2 = (Button) findViewById(R.id.btnhome2);
        btnhome3 = (Button) findViewById(R.id.btnhome3);
        btnhome4 = (Button) findViewById(R.id.btnhome4);
        edtSearch = (EditText) findViewById(R.id.edtfind);
        viewPager = (ViewPager) findViewById(R.id.vp);
        btnvoice = (ImageButton) findViewById(R.id.btnvoice);

        btnhome1.performClick();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    textSearch = s.toString();
                    mIsEmpty = false;
                    AddFragment(null);
                }
                else {
                    mIsEmpty = true;
                    AddFragment(null);
                }
            }
        });

        btnvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Xin Chao!!");

                if(intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_CODE_VOICE);
                } else {
                    Toast.makeText(HomeActivity.this, "Thiết bị này không hỗ trợ nhận diện giọng nói", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_VOICE:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    edtSearch.setText(result.get(0));
                }
                break;
        }
    }

    public void AddFragment(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;

        if(view==null){
            fragment = new HomeSearchFragment();

            if(mIsEmpty == false) {
                Bundle bundle = new Bundle();
                bundle.putString("keySearch", textSearch);
                fragment.setArguments(bundle);
            }

        } else {
            switch (view.getId()) {
                case R.id.btnhome1:
                    fragment = new Home1Fragment();
                    break;
                case R.id.btnhome2:
                    fragment = new Home2Fragment();
                    break;
                case R.id.btnhome3:
                    fragment = new Home3Fragment();
                    break;
                case R.id.btnhome4:
                    fragment = new Home4Fragment();
                    break;
            }
        }

        fragmentTransaction.replace(R.id.frameContent, fragment);
        fragmentTransaction.commit();
    }
}
