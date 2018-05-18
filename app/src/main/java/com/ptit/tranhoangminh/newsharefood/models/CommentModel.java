package com.ptit.tranhoangminh.newsharefood.models;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ptit.tranhoangminh.newsharefood.presenters.saveCommentForStorePresenters.GetNotificationInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Dell on 5/7/2018.
 */

public class CommentModel implements Parcelable {
    long chamdiem;
    String noidung;
    String tieude;
    String mauser;
    MemberModel memberModel;
    List<String> listImageComment;

    public CommentModel() {
    }

    public CommentModel(long chamdiem, String noidung, String tieude, MemberModel memberModel) {

        this.chamdiem = chamdiem;

        this.noidung = noidung;
        this.tieude = tieude;
        this.memberModel = memberModel;
    }

    protected CommentModel(Parcel in) {
        chamdiem = in.readLong();
        noidung = in.readString();
        tieude = in.readString();
        mauser = in.readString();
        listImageComment = in.createStringArrayList();
        memberModel = in.readParcelable(MemberModel.class.getClassLoader());
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
        parcel.writeString(noidung);
        parcel.writeString(tieude);
        parcel.writeString(mauser);
        parcel.writeStringList(listImageComment);
        parcel.writeParcelable(memberModel, i);
    }

    public void AddComment(final GetNotificationInterface getNotificationInterface, String key_store, CommentModel commentModel, final ImageView img) {
       // Log.d("hinh",link_image);
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("binhluans");
        String key= mref.child(key_store).push().getKey();
        mref.child(key_store).child(key).setValue(commentModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Drawable drawable=img.getDrawable();
                    BitmapDrawable bitmapDrawable=(BitmapDrawable)drawable;
                    Bitmap bitmap=bitmapDrawable.getBitmap();

                    ByteArrayOutputStream byteArrayOutputStrea=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStrea);
                    byte[]data=byteArrayOutputStrea.toByteArray();

                    Calendar calendar = Calendar.getInstance();
                    String imageName = "image" + calendar.getTimeInMillis() + ".jpg";

                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/").child(imageName);
                    storageReference.putBytes(data);

                    String notification="Thêm bình luận thành công";
                    getNotificationInterface.getNotification(notification);

                }
            }
        });
    }
    public void uploadImage(ImageView img,StorageReference mStorageRef)
    {
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache();   //lay ra 1 cai bitmap
        Bitmap bitmap = img.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mStorageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
