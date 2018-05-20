package com.ptit.tranhoangminh.newsharefood.user;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ptit.tranhoangminh.newsharefood.LoginActivity;
import com.ptit.tranhoangminh.newsharefood.MainActivity;
import com.ptit.tranhoangminh.newsharefood.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class UserActivity extends Fragment {
    //user
    private DatabaseReference mDatabase;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private TextView txthoten, txtngaysinh, txtgioitinh, txtsothich;
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private thaypass fragmentThongTin;
    private chinhsua fragmentChinhSua;
    private cacmondatao fragmentCacMonDaTao;
    private ImageView avatar;
    final int REQUEST_CODE_TAKEPHOTO = 1;
    final int REQUEST_CODE_PICKPHOTO = 2;
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    private Member member;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.active_user, null);
        AnhXa(view);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //user
        tabLayout =  view.findViewById(R.id.tablayout_id);
        appBarLayout =  view.findViewById(R.id.appbarid);
        viewPager =  view.findViewById(R.id.viewpaper_id);
        ViewPageAdapter adapter = new ViewPageAdapter(getActivity().getSupportFragmentManager());
        //adding Fragment
        adapter.AddFragment(fragmentThongTin, "Thông Tin");
        adapter.AddFragment(fragmentChinhSua, "Chỉnh Sửa");
        adapter.AddFragment(fragmentCacMonDaTao, "Món Đã Tạo");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        registerForContextMenu(avatar);
        //lay user
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            userID = user.getUid();
            mDatabase.child("members").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //gan vao layout
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        if (userID.equals(ds.getKey())) {
                            member = ds.getValue(Member.class);
                            txthoten.setText(member.getHoten());
                            txtgioitinh.setText(member.getGioitinh());
                            fragmentThongTin.setText(member);
                            fragmentChinhSua.setText(member);
                            try {
                                setImageFromFireBase(mStorageRef.child("thanhvien").child(member.getHinhanh()), member.getHinhanh(), ".png", avatar);
                            } catch (Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
        return view;
    }


    public void AnhXa(View view) {
        txthoten = view.findViewById(R.id.txtHoTen);
        txtgioitinh = view.findViewById(R.id.txtGioiTinh);
        avatar = view.findViewById(R.id.avatar);
        fragmentThongTin = new thaypass();
        fragmentChinhSua = new chinhsua();
        fragmentCacMonDaTao = new cacmondatao();

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.img_avatar:
                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePhoto, REQUEST_CODE_TAKEPHOTO);
                break;
            case R.id.chup_avatar:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, REQUEST_CODE_PICKPHOTO);
                break;
        }
        return super.onContextItemSelected(item);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.avatar_menu, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Calendar c = Calendar.getInstance();
        String d = c.getTimeInMillis() + ".png";
        switch (requestCode) {
            case REQUEST_CODE_TAKEPHOTO:
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    avatar.setImageBitmap(bitmap);

                    uploadImageToFirebase(getActivity(), mStorageRef.child("thanhvien").child(d), avatar);
                    mRef.child("members").child(userID).child("hinhanh").setValue(d);
                }
                break;
            case REQUEST_CODE_PICKPHOTO:

                if (resultCode == RESULT_OK && data != null) {
                    Uri selectedImage = data.getData();
                    avatar.setImageURI(selectedImage);
                    uploadImageToFirebase(getActivity(), mStorageRef.child("thanhvien").child(d), avatar);
                    mRef.child("members").child(userID).child("hinhanh").setValue(d);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static void uploadImageToFirebase(final Activity activity, StorageReference mStorageRef, final ImageView img) {
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache();
        Bitmap bitmap = img.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mStorageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("AAAa", "uploadImageToFirebase: success!");
                Uri downloadURL = taskSnapshot.getDownloadUrl();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("AAAAA", "uploadImageToFirebase: fail! " + e.getMessage());
                Toast.makeText(activity, "Không thể upload ảnh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void setImageFromFireBase(StorageReference mStorageRef, String prefix, String suffix, final ImageView img) {
        try {
            final File localFile = File.createTempFile(prefix, suffix);
            Log.d("AA", "createTempFile: success!");
            mStorageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            final Bitmap[] bitmap = new Bitmap[1];
                            Log.d("AA", "setImageFromFireBase: success!");
                            bitmap[0] = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            img.setImageBitmap(bitmap[0]);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("AA", "setImageFromFireBase: fail! " + exception.getMessage());
                }
            });
        } catch (IOException e) {
            Log.e("AA", "createTempFile: fail! " + e.toString());
        }
    }
}
