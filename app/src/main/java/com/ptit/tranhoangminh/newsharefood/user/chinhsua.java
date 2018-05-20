package com.ptit.tranhoangminh.newsharefood.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.tranhoangminh.newsharefood.R;

import java.util.HashMap;

public class chinhsua extends Fragment {
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    View view;
    Button btnSua;
    EditText edthoten, edtngaysinh, edtgioitinh, edtsothich;
    Switch gt;
    String sex;
    RadioGroup rdoSex;
    RadioButton nam,nu;
    public chinhsua() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit,container,false);
        //anhxa
        edthoten = view.findViewById(R.id.editTextHoTen);
//        edtgioitinh = view.findViewById(R.id.editTextGioiTinh);
        edtngaysinh = view.findViewById(R.id.editTextNgaySinh);
        edtsothich = view.findViewById(R.id.editTextSoThich);
        btnSua = view.findViewById(R.id.btnSua);
        rdoSex = view.findViewById(R.id.rdoSex);
        nam =  view.findViewById(R.id.radio_nam);
        nu = view.findViewById(R.id.radio_nu);


//        gt = view.findViewById(R.id.switch1);
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user !=null) {
            userID = user.getUid();
            rdoSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.radio_nam) {
                        sex = "Nam";
                    } else {
                        sex = "Nữ";
                    }
                }
            });

            btnSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatabase.child("members").child(userID).child("hoten").setValue(edthoten.getText().toString());
                    mDatabase.child("members").child(userID).child("gioitinh").setValue(sex);
                    mDatabase.child("members").child(userID).child("phone").setValue(edtsothich.getText().toString());
                    mDatabase.child("members").child(userID).child("ngaysinh").setValue(edtngaysinh.getText().toString());
                    Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //lay user
    }

    public void setText(Member member){
        edthoten.setText(member.getHoten());
        try {
            String gt = member.getGioitinh();
            if (gt.equals("Nam")) {
                nam.setChecked(true);
            } else {
                nu.setChecked(true);
            }
        } catch(Exception e) {

        }
        edtngaysinh.setText(member.getNgaysinh());
        edtsothich.setText(member.getPhone());
    }


}
