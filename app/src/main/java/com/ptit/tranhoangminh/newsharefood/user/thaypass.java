package com.ptit.tranhoangminh.newsharefood.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ptit.tranhoangminh.newsharefood.R;

public class thaypass extends Fragment  {
    View view;
    private TextView txthoten, txtngaysinh, txtgioitinh, txtsothich;
    public thaypass() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.changepassword, null);
        //anh xa
        txthoten = view.findViewById(R.id.txtHoTen);
        txtngaysinh = view.findViewById(R.id.txtNgaySinh);
        txtgioitinh = view.findViewById(R.id.txtGioiTinh);
        txtsothich = view.findViewById(R.id.txtSoThich);
        return view;
    }

    public void setText(Member member) {
        txthoten.setText(member.getHoten());
        txtgioitinh.setText(member.getGioitinh());
        txtsothich.setText(member.getPhone());
        txtngaysinh.setText(member.getNgaysinh());
    }
}
