package com.ptit.tranhoangminh.newsharefood.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Dell on 5/7/2018.
 */

public class MemberModel implements Parcelable {
    String hoten, hinhanh;
    String mauser;

    //not default
    private DatabaseReference databaseReference;


    public MemberModel(String hoten, String hinhanh) {
        this.hoten = hoten;
        this.hinhanh = hinhanh;
    }



    public MemberModel() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("members");
    }

    protected MemberModel(Parcel in) {
        hoten = in.readString();
        hinhanh = in.readString();
        mauser = in.readString();
    }

    public static final Creator<MemberModel> CREATOR = new Creator<MemberModel>() {
        @Override
        public MemberModel createFromParcel(Parcel in) {
            return new MemberModel(in);
        }

        @Override
        public MemberModel[] newArray(int size) {
            return new MemberModel[size];
        }
    };

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public void AddMember(MemberModel memberModel, String uid) {
        databaseReference.child(uid).setValue(memberModel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hoten);
        parcel.writeString(hinhanh);
        parcel.writeString(mauser);
    }
}
