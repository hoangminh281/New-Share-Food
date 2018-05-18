package com.ptit.tranhoangminh.newsharefood.models;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.tranhoangminh.newsharefood.presenters.displayStorePresenters.StoreInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell on 5/6/2018.
 */

public class StoreModel implements Parcelable {

    boolean giaohang;
    String giomocua,giodongcua,tenquanan,maquanan;
    private DatabaseReference mref;
    List<CommentModel>commentModelList;
    List<String> tienich;
    List<String> hinhanh;
    List<BranchModel>branchModelList;
    List<CategoryStoreModel>categoryStoreModelList;



    protected StoreModel(Parcel in) {
        giaohang = in.readByte() != 0;
        giomocua = in.readString();
        giodongcua = in.readString();
        tenquanan = in.readString();
        maquanan = in.readString();
        tienich = in.createStringArrayList();
        hinhanh = in.createStringArrayList();
        branchModelList=new ArrayList<BranchModel>();
        in.readTypedList(branchModelList,BranchModel.CREATOR);
        commentModelList=new ArrayList<CommentModel>();
        in.readTypedList(commentModelList,CommentModel.CREATOR);
    }
    public static final Creator<StoreModel> CREATOR = new Creator<StoreModel>() {
        @Override
        public StoreModel createFromParcel(Parcel in) {
            return new StoreModel(in);
        }

        @Override
        public StoreModel[] newArray(int size) {
            return new StoreModel[size];
        }
    };

    public List<CommentModel> getCommentModelList() {
        return commentModelList;
    }

    public void setCommentModelList(List<CommentModel> commentModelList) {
        this.commentModelList = commentModelList;
    }

    public StoreModel() {
        mref= FirebaseDatabase.getInstance().getReference();
    }

   /* public StoreModel(boolean giaohang, String giomocua, String giodongcua, String tenquan, String maquanan, List<String> tienich, DatabaseReference mref) {
        this.giaohang = giaohang;
        this.giomocua = giomocua;
        this.giodongcua = giodongcua;
        this.tenquanan = tenquan;
        this.maquanan = maquanan;
        this.tienich = tienich;
        this.mref = mref;

    }*/
    public void GetDanhSachQuanAn(final StoreInterface storeInterface, final Location current_location)
    {
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get quanans
                DataSnapshot dataSnapshot1=dataSnapshot.child("quanans");
                //key dong nên k lấy dc data =>for
                for(DataSnapshot valueQuanAn:dataSnapshot1.getChildren())
                {
                    StoreModel storeModel=valueQuanAn.getValue(StoreModel.class);
                    storeModel.setMaquanan(valueQuanAn.getKey());

                    //get image-----------------------
                    DataSnapshot dataSnapshotHinhQuanAn=dataSnapshot.child("hinhanhquanans").child(valueQuanAn.getKey());
                    Log.d("hinhma",valueQuanAn.getKey());
                    List<String>listHinh=new ArrayList<>();
                    for(DataSnapshot valueHinh:dataSnapshotHinhQuanAn.getChildren())
                    {
                        Log.d("hinh",valueHinh.getValue()+"");
                        listHinh.add(valueHinh.getValue(String.class));
                    }
                    storeModel.setHinhanh(listHinh);
                    Log.d("kt",storeModel.getHinhanh().size()+"");

                    //get comment-------------------------------

                    DataSnapshot dataSnapshotComment=dataSnapshot.child("binhluans").child(storeModel.getMaquanan());
                    List<CommentModel>commentList=new ArrayList<>();
                    for(DataSnapshot valueComment:dataSnapshotComment.getChildren())
                    {
                        CommentModel commentModel=valueComment.getValue(CommentModel.class);

                        //get info member-----------------------
                        Log.d("user",commentModel.getMauser());
                        MemberModel dataSnapshotMember=dataSnapshot.child("members").child(commentModel.getMauser()).getValue(MemberModel.class);
                        commentModel.setMemberModel(dataSnapshotMember);

                        //get image comment------------------
                        List<String>hinhanhlist=new ArrayList<>();
                        DataSnapshot dataSnapshotHinhAnhBL= dataSnapshot.child("hinhanhbinhluans").child(valueComment.getKey());
                        for(DataSnapshot hinhbl:dataSnapshotHinhAnhBL.getChildren())
                        {
                            hinhanhlist.add(hinhbl.getValue(String.class));
                        }
                        commentModel.setListImageComment(hinhanhlist);
                        commentList.add(commentModel);
                    }
                    storeModel.setCommentModelList(commentList);

                    //get chi nhánh quán ăn--------------------
                    DataSnapshot dataSnapshotBranch=dataSnapshot.child("chinhanhquanans").child(storeModel.getMaquanan());
                    List<BranchModel>branchModels=new ArrayList<>();
                    for(DataSnapshot valueBranch:dataSnapshotBranch.getChildren())
                    {
                        BranchModel branchModel=valueBranch.getValue(BranchModel.class);
                        Location vitri_store=new Location("");
                        vitri_store.setLatitude(branchModel.getLatitude());
                        vitri_store.setLongitude(branchModel.getLongitude());

                        double distance=current_location.distanceTo(vitri_store)/1000;
                        Log.d("khoangcach",distance+" -"+branchModel.getDiachi());
                        branchModel.setDistance(distance);
                        branchModels.add(branchModel);
                    }
                    storeModel.setBranchModelList(branchModels);
                    storeInterface.GetListStore(storeModel);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        //lắng nghe 1 lần duy nhất-data change is no care
        mref.addListenerForSingleValueEvent(valueEventListener);


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (giaohang ? 1 : 0));
        parcel.writeString(giomocua);
        parcel.writeString(giodongcua);
        parcel.writeString(tenquanan);
        parcel.writeString(maquanan);
        parcel.writeStringList(tienich);
        parcel.writeStringList(hinhanh);
        parcel.writeTypedList(branchModelList);
        parcel.writeTypedList(commentModelList);
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public DatabaseReference getMref() {
        return mref;
    }

    public void setMref(DatabaseReference mref) {
        this.mref = mref;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public List<String> getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(List<String> hinhanh) {
        this.hinhanh = hinhanh;
    }

    public List<BranchModel> getBranchModelList() {
        return branchModelList;
    }

    public void setBranchModelList(List<BranchModel> branchModelList) {
        this.branchModelList = branchModelList;
    }

    public List<CategoryStoreModel> getCategoryStoreModelList() {
        return categoryStoreModelList;
    }

    public void setCategoryStoreModelList(List<CategoryStoreModel> categoryStoreModelList) {
        this.categoryStoreModelList = categoryStoreModelList;
    }

    public static Creator<StoreModel> getCREATOR() {
        return CREATOR;
    }
}


