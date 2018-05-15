package com.ptit.tranhoangminh.newsharefood.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ptit.tranhoangminh.newsharefood.presenters.saveCommentForStorePresenters.GetNotificationInterface;

import java.io.File;
import java.util.List;

/**
 * Created by Dell on 5/7/2018.
 */

public class CommentModel implements Parcelable {
    long chamdiem,luotthich;
    String noidung;
    String tieude;
    String mauser;
    MemberModel memberModel;
    List<String>listImageComment;



    public CommentModel() {
    }

    public CommentModel(long chamdiem, long luotthich, String noidung, String tieude, MemberModel memberModel) {

        this.chamdiem = chamdiem;
        this.luotthich = luotthich;
        this.noidung = noidung;
        this.tieude = tieude;
        this.memberModel = memberModel;
    }

    protected CommentModel(Parcel in) {
        chamdiem = in.readLong();
        luotthich = in.readLong();
        noidung = in.readString();
        tieude = in.readString();
        mauser = in.readString();
        listImageComment = in.createStringArrayList();
        memberModel=in.readParcelable(MemberModel.class.getClassLoader());
    }

    public static final Creator<CommentModel> CREATOR = new Creator<CommentModel>() {
        @Override
        public CommentModel createFromParcel(Parcel in) {
            return new CommentModel(in);
        }

        @Override
        public CommentModel[] newArray(int size) {
            return new CommentModel[size];
        }
    };

    public long getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(long chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public MemberModel getMemberModel() {
        return memberModel;
    }

    public void setMemberModel(MemberModel memberModel) {
        this.memberModel = memberModel;
    }
    public List<String> getListImageComment() {
        return listImageComment;
    }

    public void setListImageComment(List<String> listImageComment) {
        this.listImageComment = listImageComment;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(chamdiem);
        parcel.writeLong(luotthich);
        parcel.writeString(noidung);
        parcel.writeString(tieude);
        parcel.writeString(mauser);
        parcel.writeStringList(listImageComment);
        parcel.writeParcelable(memberModel,i);
    }

    public void AddComment(final GetNotificationInterface getNotificationInterface, String key_store, CommentModel commentModel, final String link_image) {
        Log.d("hinh",link_image);
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("binhluans");
        String key= mref.child(key_store).push().getKey();
        mref.child(key_store).child(key).setValue(commentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Uri uri = Uri.fromFile(new File(link_image));
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/"+ uri.getLastPathSegment());
                    storageReference.putFile(uri);
                    String notification="Thêm bình luận thành công";
                    getNotificationInterface.getNotification(notification);

                }
            }
        });
        //FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(key).push().setValue(link_image);
    }
}
