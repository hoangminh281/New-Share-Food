package com.ptit.tranhoangminh.newsharefood.views.Orderfood;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.adapters.AdapterRecycleViewFoodStore;
import com.ptit.tranhoangminh.newsharefood.models.BillModel;
import com.ptit.tranhoangminh.newsharefood.models.OrderFoodModel;
import com.ptit.tranhoangminh.newsharefood.presenters.Mail.SendMail;
public class MyCartActivity extends AppCompatActivity {
    ListView listView;
    Button btnThanhToan;
    ArrayAdapter<OrderFoodModel> adapter;
    SharedPreferences sharedPreferences;
    BillModel billModel;
    String userId;
    TextView txtTongCong;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);
        listView = findViewById(R.id.listViewCart);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        txtTongCong=findViewById(R.id.txtTongCong);

        billModel = new BillModel();
        sharedPreferences = getSharedPreferences("userId_login", MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", "");
        billModel.setUser_id(userId);



        int total=0;
        for(OrderFoodModel order : AdapterRecycleViewFoodStore.orderFoodModelList) {

             total+=order.getGiatien();
        }
        txtTongCong.setText(String.valueOf(total)+"");
        billModel.setTotal(total);



        adapter=new ArrayAdapter<>(MyCartActivity.this,android.R.layout.simple_list_item_1,AdapterRecycleViewFoodStore.orderFoodModelList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyCartActivity.this);
                alertDialog.setTitle("Xác nhân mua hàng");
                alertDialog.setMessage("Bạn có chắc chắn mua sản phẩm này?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        final DatabaseReference mrefHoaDon = FirebaseDatabase.getInstance().getReference().child("bills");
                        final String key = mrefHoaDon.push().getKey();
                        mrefHoaDon.child(key).setValue(billModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    DatabaseReference mrefChitiet = FirebaseDatabase.getInstance().getReference().child("billdetail").child(key);
                                    mrefChitiet.setValue(AdapterRecycleViewFoodStore.orderFoodModelList);
                                    Toast.makeText(MyCartActivity.this, "Thanh toan thanh cong", Toast.LENGTH_SHORT).show();
                                    AdapterRecycleViewFoodStore.orderFoodModelList.clear();

                                    SendMail sm = new SendMail(MyCartActivity.this, "computerwindowxp@gmail.com", "Xin Chao Ban", "Ban dang lam gi vay");

                                    sm.execute();

                                    finish();
                                }
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();


            }
        });
    }


}
