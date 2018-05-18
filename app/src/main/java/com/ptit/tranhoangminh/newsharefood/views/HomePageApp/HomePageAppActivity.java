package com.ptit.tranhoangminh.newsharefood.views.HomePageApp;




import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ViewFlipper;


import com.ptit.tranhoangminh.newsharefood.R;

import com.ptit.tranhoangminh.newsharefood.models.StoreModel;

import java.util.ArrayList;
import java.util.List;

;


public class HomePageAppActivity extends Fragment  {

    List<StoreModel> list;
    ViewFlipper viewFlipper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_trangchu_layout,container,false);

        AddControl(view);
        SetFilpper();
        return view;
    }


    private void AddControl(View view) {
        list = new ArrayList<>();
        viewFlipper=view.findViewById(R.id.viewFilpper);
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
