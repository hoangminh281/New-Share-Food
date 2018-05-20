package com.ptit.tranhoangminh.newsharefood.views.StoreDetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.adapters.AdapterComment;
import com.ptit.tranhoangminh.newsharefood.adapters.AdapterRecycleViewMenuStore;
import com.ptit.tranhoangminh.newsharefood.models.CategoryStoreModel;
import com.ptit.tranhoangminh.newsharefood.models.StoreModel;
import com.ptit.tranhoangminh.newsharefood.models.UtilitiesModel;
import com.ptit.tranhoangminh.newsharefood.presenters.menuStorePresenters.MenuStorePresenterLogic;
import com.ptit.tranhoangminh.newsharefood.views.AddComment.AddCommentActivity;
import com.ptit.tranhoangminh.newsharefood.views.DirectionMap.DirectionMapStoreActivity;
import com.ptit.tranhoangminh.newsharefood.views.Orderfood.MyCartActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChiTietQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, MenuImp {
    TextView txtTenQuan, txtDiaChi, txtHinh, txtCheckin, txtThoiGianHD, txtTongBL, txtTongLuu, txtTrangThaiHD;
    ImageView imgHinhQuanAn;
    StoreModel storeModel;
    RecyclerView recyclerView, recyclerViewMenu;
    AdapterComment adapterComment;
    AdapterRecycleViewMenuStore adapterRecycleViewMenuStore;
    NestedScrollView nestedScrollViewCT;
    GoogleMap googleMap;
    MapFragment mapFragment;
    LinearLayout linearLayoutTienIch;
    View view;
    Button btnComment, btnAddCart;
    MenuStorePresenterLogic menuStorePresenterLogic;
    public static int REQUEST_CODE_COMMENT = 999;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_quanan_layout);

        AddControl();
        storeModel = getIntent().getParcelableExtra("store");

        DisplayStoreDetail();


        view.setOnClickListener(this);
        btnComment.setOnClickListener(this);
        btnAddCart.setOnClickListener(this);

    }

    private void DisplayStoreDetail() {
        //xu li thoi gian mo/dong cua
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String current_time = simpleDateFormat.format(calendar.getTime());
        String open_time = storeModel.getGiomocua();
        String close_time = storeModel.getGiodongcua();

        try {
            Date date_current_time = simpleDateFormat.parse(current_time);
            Date date_open_time = simpleDateFormat.parse(open_time);
            Date date_close_time = simpleDateFormat.parse(close_time);
            if (date_current_time.after(date_open_time) && date_current_time.before(date_close_time)) {
                txtTrangThaiHD.setText("Đang mở cửa");
            } else {
                txtTrangThaiHD.setText("Đóng cửa");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //kết thúc xử lí thời gian đóng mở cửa

        //Hiển thị địa chỉ,thời gian HD,số hình ảnh cũng như tổng bình luận
        //txtTenQuan.setText(storeModel.getTenquan());
        txtDiaChi.setText(storeModel.getBranchModelList().get(0).getDiachi());
        txtThoiGianHD.setText(storeModel.getGiomocua() + " - " + storeModel.getGiodongcua());
        txtHinh.setText(storeModel.getHinhanh().size() + "");
        txtTongBL.setText(storeModel.getCommentModelList().size() + "");


        //dowload hình tiện ích của quán
        DowloadImage();

        //hiển thị google map
        mapFragment.getMapAsync(this);

        //set image
        StorageReference storageReferenceImage = FirebaseStorage.getInstance().getReference().child("images").child(storeModel.getHinhanh().get(0));
        final long ONE_MEGABYTE = 1024 * 1024;
        storageReferenceImage.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgHinhQuanAn.setImageBitmap(bitmap);
            }
        });


        //load comment
        setAdapter();

        //scrollview luôn hiển thi trên cùng khi load xong data
        nestedScrollViewCT.smoothScrollBy(0, 0);
    }

    private void AddControl() {
        imgHinhQuanAn = findViewById(R.id.imgHinhChiTiet);
        txtDiaChi = findViewById(R.id.txtDiaChiCT);
        txtHinh = findViewById(R.id.txttongSoHinhAnhCT);
        txtCheckin = findViewById(R.id.txttongSoCheckInCT);
        txtThoiGianHD = findViewById(R.id.txtThoiGianHoatDong);
        txtTongBL = findViewById(R.id.txttongSoBinhLuanCT);
        txtTongLuu = findViewById(R.id.txtTongSoLuuLaiCT);
        txtTrangThaiHD = findViewById(R.id.txtTrangThaiHoatDong);
        recyclerView = findViewById(R.id.recyclerBinhLuanChiTietQuanAn);
        nestedScrollViewCT = findViewById(R.id.netScrollViewCT);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        linearLayoutTienIch = findViewById(R.id.linerTienIch);
        view = findViewById(R.id.khungchuyenhuongMap);
        btnComment = findViewById(R.id.btnComment);
        btnAddCart = findViewById(R.id.btnAddCart);
        recyclerViewMenu = findViewById(R.id.recycleviewMenu);
        menuStorePresenterLogic = new MenuStorePresenterLogic(ChiTietQuanAnActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapterComment = new AdapterComment(this, R.layout.custom_layout_comment, storeModel.getCommentModelList());
        recyclerView.setAdapter(adapterComment);
        adapterComment.notifyDataSetChanged();
        menuStorePresenterLogic.getMenu(storeModel.getMaquanan());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng latLng = new LatLng(storeModel.getBranchModelList().get(0).getLatitude(), storeModel.getBranchModelList().get(0).getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(storeModel.getTenquanan());
        googleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        googleMap.moveCamera(cameraUpdate);
    }

    //dowload hinh tiện ích của quán ăn rồi hiển thị lên linear layout
    private void DowloadImage() {
        for (String matienich : storeModel.getTienich()) {
            final DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("quanlitienich").child(matienich);
            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UtilitiesModel utilitiesModel = dataSnapshot.getValue(UtilitiesModel.class);
                    StorageReference storageReferenceImage = FirebaseStorage.getInstance().getReference().child("utilities").child(utilitiesModel.getHinhtienich());
                    final long ONE_MEGABYTE = 1024 * 1024;
                    storageReferenceImage.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ImageView imageView = new ImageView(ChiTietQuanAnActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(70, 70);
                            layoutParams.setMargins(10, 10, 10, 10);
                            imageView.setLayoutParams(layoutParams);

                            imageView.setImageBitmap(bitmap);
                            linearLayoutTienIch.addView(imageView);

                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.khungchuyenhuongMap:
                Intent iDirection = new Intent(ChiTietQuanAnActivity.this, DirectionMapStoreActivity.class);
                iDirection.putExtra("latitute", storeModel.getBranchModelList().get(0).getLatitude());
                iDirection.putExtra("longtitute", storeModel.getBranchModelList().get(0).getLongitude());
                Log.d("check", storeModel.getBranchModelList().get(0).getLatitude() + "-" + storeModel.getBranchModelList().get(0).getLongitude());
                startActivity(iDirection);
                break;
            case R.id.btnComment:
                Toast.makeText(this, storeModel.getMaquanan(), Toast.LENGTH_SHORT).show();
                Intent iComment = new Intent(ChiTietQuanAnActivity.this, AddCommentActivity.class);
                iComment.putExtra("key_store", storeModel.getMaquanan());
                startActivityForResult(iComment, REQUEST_CODE_COMMENT);
                break;
            case R.id.btnAddCart:
                Intent iAddcart = new Intent(ChiTietQuanAnActivity.this, MyCartActivity.class);
                startActivity(iAddcart);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_COMMENT) {
            if (resultCode == 998) {
                String result = data.getStringExtra("result");
                Toast.makeText(this, "Thêm bình luận thành công", Toast.LENGTH_SHORT).show();
                setAdapter();
            }
        }
    }


    @Override
    public void getMenu(List<CategoryStoreModel> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewMenu.setLayoutManager(layoutManager);
        adapterRecycleViewMenuStore = new AdapterRecycleViewMenuStore(this, list);
        recyclerViewMenu.setAdapter(adapterRecycleViewMenuStore);
        adapterRecycleViewMenuStore.notifyDataSetChanged();
    }
}



