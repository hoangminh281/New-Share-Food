package com.ptit.tranhoangminh.newsharefood.views.HomePageApp;




import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ViewFlipper;


import com.ptit.tranhoangminh.newsharefood.LoginActivity;
import com.ptit.tranhoangminh.newsharefood.MainActivity;
import com.ptit.tranhoangminh.newsharefood.R;

import com.ptit.tranhoangminh.newsharefood.models.StoreModel;
import com.ptit.tranhoangminh.newsharefood.views.SearchViews.SeachViewActivity;

import java.util.ArrayList;
import java.util.List;

;


public class HomePageAppActivity extends Fragment  {

    List<StoreModel> list;
    EditText find;
    ViewFlipper viewFlipper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_trangchu_layout,container,false);

        AddControl(view);

        find.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_NEXT ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    Intent in = new Intent(getActivity(), SeachViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("find", find.getText().toString());
                    in.putExtras(bundle);
                    startActivity(in);
                }
                return true;
            }
        });
//        find.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                Intent in = new Intent(getActivity(), SeachViewActivity.class);
//                startActivity(in);
//                return true;
//            }
//        });

        SetFilpper();
        return view;
    }
    private void AddControl(View view) {
        list = new ArrayList<>();
        viewFlipper=view.findViewById(R.id.viewFilpper);
        find = view.findViewById(R.id.edtfind);
    }
    private void SetFilpper() {

        int images[] = {R.drawable.bread, R.drawable.vegitarian, R.drawable.egg};

        for(int image : images) {
            flipperImage(image);
        }
    }
    private void flipperImage(int image) {
        ImageView imageView = new ImageView(getActivity());
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
      /*  Animation animationReight=AnimationUtils.loadAnimation(getActivity(),R.anim.slide_banner);
        Animation animationLeft= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_banner_left);
        viewFlipper.setInAnimation(animationReight);
        viewFlipper.setOutAnimation(animationLeft);*/
    }

}
