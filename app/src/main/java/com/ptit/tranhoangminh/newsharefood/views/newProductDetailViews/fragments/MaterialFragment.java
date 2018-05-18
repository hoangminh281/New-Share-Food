package com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptit.tranhoangminh.newsharefood.R;

public class MaterialFragment extends Fragment {
    TextView tvMaterials;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_materials, null);
        tvMaterials = view.findViewById(R.id.textViewNewMaterials);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tvMaterials.setText(bundle.getString("materials"));
        }
        return view;
    }

    public void setMaterials(String materials) {
        if (tvMaterials != null) {
            String[] arr = materials.split("\n\n");
            String st = "Nguyên liệu cần dùng cho món ăn gồm:\n";
            int i = 1;
            for (String item : arr) {
                String[] arr1 = item.split("\t");
                st += "\t" + i++ + ". ";
                if (arr1.length < 2)
                    st += arr1[0] + "\n";
                else
                    st += arr1[1] + ": số lượng " + arr1[0] + "\n";
            }
            tvMaterials.setText(st);
        }
    }
}
