package com.ptit.tranhoangminh.newsharefood.views.AddStore;



import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.AddMenuModel;
import com.ptit.tranhoangminh.newsharefood.models.BranchModel;
import com.ptit.tranhoangminh.newsharefood.models.CategoryStoreModel;
import com.ptit.tranhoangminh.newsharefood.models.MenuStoreModel;
import com.ptit.tranhoangminh.newsharefood.models.StoreModel;
import com.ptit.tranhoangminh.newsharefood.models.UtilitiesModel;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddStoreActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    final int RESULT_IMG1 = 111;
    final int RESULT_IMG2 = 112;
    final int RESULT_IMG3 = 113;
    final int RESULT_IMG4 = 114;
    final int RESULT_IMG5 = 115;
    final int RESULT_IMG6 = 116;
    final int RESULT_IMGTHUCDON = 117;
    final int RESULT_VIDEO = 200;

    Button btnGioMoCua,btnGioDongCua,btnThemQuanAn;
    Spinner spinnerKhuVuc;
    LinearLayout khungTienIch,khungChiNhanh,khungChuaChiNhanh,khungChuaThucDon;
    String gioMoCua,gioDongCua,khuvuc;
    RadioGroup rdgTrangThai;
    EditText edTenQuanAn;

    List<CategoryStoreModel> thucDonModelList;
    List<String> selectedTienIchList;
    List<String> khuVucList,thucDonList;
    List<String> chiNhanhList;
    List<AddMenuModel> themThucDonModelList;
    List<Bitmap> hinhDaChup;
    List<Uri> hinhQuanAn;
    Uri videoSelected;

    ArrayAdapter<String> adapterKhuVuc;
    ImageView imgTam,imgHinhQuan1,imgHinhQuan2,imgHinhQuan3,imgHinhQuan4,imgHinhQuan5,imgHinhQuan6,imgVideo;
    VideoView videoView;

    String maQuanAn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_store_layout);

        btnGioDongCua =  findViewById(R.id.btnGioDongCua);
        btnGioMoCua =  findViewById(R.id.btnGioMoCua);
        spinnerKhuVuc =  findViewById(R.id.spinnerKhuVuc);
        khungTienIch =  findViewById(R.id.khungTienTich);
        khungChuaChiNhanh =  findViewById(R.id.khungChuaChiNhanh);
        khungChuaThucDon = findViewById(R.id.khungChuaThucDon);
        imgHinhQuan1 = findViewById(R.id.imgHinhQuan1);
        imgHinhQuan2 =  findViewById(R.id.imgHinhQuan2);
        imgHinhQuan3 =  findViewById(R.id.imgHinhQuan3);
        imgHinhQuan4 =  findViewById(R.id.imgHinhQuan4);
        imgHinhQuan5 =  findViewById(R.id.imgHinhQuan5);
        imgHinhQuan6 =  findViewById(R.id.imgHinhQuan6);
        imgVideo =  findViewById(R.id.imgVideo);
        videoView =  findViewById(R.id.videoView);
        btnThemQuanAn =findViewById(R.id.btnThemQuanAn);
        rdgTrangThai =  findViewById(R.id.rdgTrangThai);
        edTenQuanAn =  findViewById(R.id.edTenQuan);

        thucDonModelList = new ArrayList<>();
        khuVucList = new ArrayList<>();
        thucDonList = new ArrayList<>();
        selectedTienIchList = new ArrayList<>();
        chiNhanhList = new ArrayList<>();
        themThucDonModelList = new ArrayList<>();
        hinhDaChup = new ArrayList<>();
        hinhQuanAn = new ArrayList<>();

        adapterKhuVuc = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,khuVucList);
        spinnerKhuVuc.setAdapter(adapterKhuVuc);
        adapterKhuVuc.notifyDataSetChanged();

        CloneChiNhanh();
        CloneThucDon();

        LayDanhSachKhuVuc();
        LayDanhSachTienIch();

        btnGioMoCua.setOnClickListener(this);
        btnGioDongCua.setOnClickListener(this);
        spinnerKhuVuc.setOnItemSelectedListener(this);
        imgHinhQuan1.setOnClickListener(this);
        imgHinhQuan2.setOnClickListener(this);
        imgHinhQuan3.setOnClickListener(this);
        imgHinhQuan4.setOnClickListener(this);
        imgHinhQuan5.setOnClickListener(this);
        imgHinhQuan6.setOnClickListener(this);
        imgVideo.setOnClickListener(this);
        btnThemQuanAn.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case RESULT_IMG1:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan1.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;

            case RESULT_IMG2:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan2.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;

            case RESULT_IMG3:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan3.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;

            case RESULT_IMG4:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan4.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;

            case RESULT_IMG5:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan5.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;

            case RESULT_IMG6:
                if(RESULT_OK == resultCode){
                    Uri uri = data.getData();
                    imgHinhQuan6.setImageURI(uri);
                    hinhQuanAn.add(uri);
                }
                break;

            case RESULT_IMGTHUCDON:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgTam.setImageBitmap(bitmap);
                hinhDaChup.add(bitmap);
                break;

            case RESULT_VIDEO:
                if(RESULT_OK == resultCode){
                    imgVideo.setVisibility(View.GONE);
                    Uri uri = data.getData();
                    videoSelected = uri;
                    videoView.setVideoURI(uri);
                    videoView.start();

                }
                break;
        }

    }

    private void CloneThucDon(){
        View view = LayoutInflater.from(AddStoreActivity.this).inflate(R.layout.menu_layout,null);
        final Spinner spinnerThucDon = (Spinner) view.findViewById(R.id.spinnerThucDon);
        Button btnThemThucDOn = (Button) view.findViewById(R.id.btnThemThucDon);
        final EditText edTenMon = (EditText) view.findViewById(R.id.edTenMon);
        final EditText edGiaTien = (EditText) view.findViewById(R.id.edGiaTien);
        ImageView imageChupHinh = (ImageView) view.findViewById(R.id.imgChupHinh);
        imgTam = imageChupHinh;

        ArrayAdapter<String> adapterThucDon = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,thucDonList);
        spinnerThucDon.setAdapter(adapterThucDon);
        adapterThucDon.notifyDataSetChanged();
        if(thucDonModelList.size() == 0){
            LayDanhSachThucDon(adapterThucDon);
        }

        imageChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,RESULT_IMGTHUCDON);
            }
        });

        btnThemThucDOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);

                long thoigian = Calendar.getInstance().getTimeInMillis();
                String tenhinh = String.valueOf(thoigian)+".jpg";

                int position = spinnerThucDon.getSelectedItemPosition();
                String maThucDon = thucDonModelList.get(position).getCstegory_id();

                MenuStoreModel monAnModel = new MenuStoreModel();
                monAnModel.setName(edTenMon.getText().toString());
                monAnModel.setPrice(Long.parseLong(edGiaTien.getText().toString()));
                monAnModel.setImage(tenhinh);

                AddMenuModel themThucDonModel = new AddMenuModel();
                themThucDonModel.setCstegory_id(maThucDon);
                themThucDonModel.setMenuStoreModel(monAnModel);

                themThucDonModelList.add(themThucDonModel);
                CloneThucDon();
            }
        });

        khungChuaThucDon.addView(view);
    }

    private void CloneChiNhanh(){
        final View view = LayoutInflater.from(AddStoreActivity.this).inflate(R.layout.branch_layout,null);
        Button btnThemChiNhanh = view.findViewById(R.id.btnThemChiNhanh);
        final Button btnXoaChiNhanh = view.findViewById(R.id.btnXoaChiNhanh);

        btnThemChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edTenChiNhanh = (EditText) view.findViewById(R.id.edTenChiNhanh);
                String tenChiNhanh = edTenChiNhanh.getText().toString();

                v.setVisibility(View.GONE);
                btnXoaChiNhanh.setVisibility(View.VISIBLE);
                btnXoaChiNhanh.setTag(tenChiNhanh);


                chiNhanhList.add(tenChiNhanh);

                CloneChiNhanh();
            }
        });

        btnXoaChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenChiNhanh = v.getTag().toString();
                chiNhanhList.remove(tenChiNhanh);
                khungChuaChiNhanh.removeView(view);

            }
        });
        khungChuaChiNhanh.addView(view);
    }

    private void LayDanhSachKhuVuc(){
        FirebaseDatabase.getInstance().getReference().child("khuvucs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String tenKhuVuc = snapshot.getKey();
                    khuVucList.add(tenKhuVuc);
                }

                adapterKhuVuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void LayDanhSachThucDon(final ArrayAdapter<String> adapterThucDon){
        FirebaseDatabase.getInstance().getReference().child("CategoriesStore").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    CategoryStoreModel thucDonModel = new CategoryStoreModel();
                    String key = snapshot.getKey();
                    String value = snapshot.getValue(String.class);

                    thucDonModel.setCategory_name(value);
                    thucDonModel.setCstegory_id(key);

                    thucDonModelList.add(thucDonModel);
                    thucDonList.add(value);

                }

                adapterThucDon.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void LayDanhSachTienIch(){
        FirebaseDatabase.getInstance().getReference().child("quanlitienich").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String maTienIch = snapshot.getKey();
                    UtilitiesModel tienIchModel = snapshot.getValue(UtilitiesModel.class);
                    tienIchModel.setMatienich(maTienIch);

                    CheckBox checkBox = new CheckBox(AddStoreActivity.this);
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    checkBox.setText(tienIchModel.getTentienich());
                    checkBox.setTag(maTienIch);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            String maTienIch = buttonView.getTag().toString();
                            if(isChecked){
                                selectedTienIchList.add(maTienIch);
                            }else{
                                selectedTienIchList.remove(maTienIch);
                            }
                        }
                    });
                    khungTienIch.addView(checkBox);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(final View v) {
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);

        switch (v.getId()){
            case R.id.btnGioDongCua:

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioDongCua = hourOfDay +":"+minute;
                        ((Button)v).setText(gioDongCua);
                    }
                },gio,phut,true);

                timePickerDialog.show();
                break;

            case R.id.btnGioMoCua:

                TimePickerDialog moCuaTimePickerDialog = new TimePickerDialog(AddStoreActivity.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioMoCua = hourOfDay +":"+minute;
                        ((Button)v).setText(gioMoCua);
                    }
                },gio,phut,true);

                moCuaTimePickerDialog.show();
                break;

            case R.id.imgHinhQuan1:
                ChonHinhTuGallary(RESULT_IMG1);
                break;

            case R.id.imgHinhQuan2:
                ChonHinhTuGallary(RESULT_IMG2);
                break;

            case R.id.imgHinhQuan3:
                ChonHinhTuGallary(RESULT_IMG3);
                break;

            case R.id.imgHinhQuan4:
                ChonHinhTuGallary(RESULT_IMG4);
                break;

            case R.id.imgHinhQuan5:
                ChonHinhTuGallary(RESULT_IMG5);
                break;

            case R.id.imgHinhQuan6:
                ChonHinhTuGallary(RESULT_IMG6);
                break;

            case R.id.imgVideo:
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Chọn video..."),RESULT_VIDEO);
                break;

            case R.id.btnThemQuanAn:
                ThemQuanAn();
                break;
        }
    }

    private void ThemQuanAn(){
       String tenquan=edTenQuanAn.getText().toString();

        int idRadioSelected = rdgTrangThai.getCheckedRadioButtonId();
        boolean giaoHang = false;
        if(idRadioSelected == R.id.rdGiaoHang){
            giaoHang = true;
        }else{
            giaoHang = false;
        }


        DatabaseReference nodeQuanAn = FirebaseDatabase.getInstance().getReference();
       // maQuanAn = nodeQuanAn.push().getKey();

        StoreModel quanAnModel = new StoreModel();
        quanAnModel.setGiodongcua(gioDongCua);
        quanAnModel.setGiomocua(gioMoCua);
        quanAnModel.setTenquanan(tenquan);
        quanAnModel.setGiaohang(giaoHang);
        quanAnModel.setTienich(selectedTienIchList);




        nodeQuanAn.child("quanans").push().setValue(tenquan).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddStoreActivity.this, "thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
       // nodeQuanAn.child(maQuanAn).setValue("ssss");
       /* nodeRoot.child("khuvucs").child(khuvuc).push().setValue(maQuanAn);*/

       /* for(String chinhanh : chiNhanhList){
            String urlGeoCoding = "https://maps.googleapis.com/maps/api/geocode/json?address="+chinhanh.replace(" ","%20")+"&key=AIzaSyBVd2D3evAh1Ip_f5nuN1P6ad-14G3Ns0g";
            DownloadToaDo downloadToaDo = new DownloadToaDo();
            downloadToaDo.execute(urlGeoCoding);

        }*/



        /*FirebaseStorage.getInstance().getReference().child("video/"+videoSelected.getLastPathSegment()).putFile(videoSelected);
        for(Uri hinhquan : hinhQuanAn){
            FirebaseStorage.getInstance().getReference().child("hinhanh"+hinhquan.getLastPathSegment()).putFile(hinhquan);
            nodeRoot.child("hinhanhquanans").child(maQuanAn).push().child(hinhquan.getLastPathSegment());
        }

        for (int i=0 ;i< themThucDonModelList.size() ; i++){
            nodeRoot.child("thucdonquanans").child(maQuanAn).child(themThucDonModelList.get(i).getMathucdon()).push().setValue(themThucDonModelList.get(i).getMonAnModel());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap bitmap = hinhDaChup.get(i);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            FirebaseStorage.getInstance().getReference().child("hinhanh/"+themThucDonModelList.get(i).getMonAnModel().getHinhanh()).putBytes(data);
        }
*/

    }

  /*  class DownloadToaDo extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line+"\n");
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray results = jsonObject.getJSONArray("results");
                for (int i =0 ;i<results.length();i++){
                    JSONObject object = results.getJSONObject(i);
                    String address = object.getString("formatted_address");
                    JSONObject geometry = object.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    double latitude = (double) location.get("lat");
                    double longitude = (double) location.get("lng");

                    ChiNhanhQuanAnModel chiNhanhQuanAnModel = new ChiNhanhQuanAnModel();
                    chiNhanhQuanAnModel.setDiachi(address);
                    chiNhanhQuanAnModel.setLatitude(latitude);
                    chiNhanhQuanAnModel.setLatitude(longitude);

                    FirebaseDatabase.getInstance().getReference().child("chinhanhquanans").child(maQuanAn).push().setValue(chiNhanhQuanAnModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/

    private void ChonHinhTuGallary(int requestCode){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Chọn hình..."),requestCode);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.spinnerKhuVuc:
                khuvuc = khuVucList.get(position);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
