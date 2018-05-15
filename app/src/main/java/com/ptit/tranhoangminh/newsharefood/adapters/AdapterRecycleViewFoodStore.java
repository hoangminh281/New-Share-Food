package com.ptit.tranhoangminh.newsharefood.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.CategoryStoreModel;
import com.ptit.tranhoangminh.newsharefood.models.MenuStoreModel;
import com.ptit.tranhoangminh.newsharefood.models.OrderFoodModel;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecycleViewFoodStore extends RecyclerView.Adapter<AdapterRecycleViewFoodStore.ViewHolder> {
    Context context;
    List<MenuStoreModel> product_list;
    public static List<OrderFoodModel> orderFoodModelList=new ArrayList<>();

    public AdapterRecycleViewFoodStore(Context context, List<MenuStoreModel> product_list) {
        this.context = context;
        this.product_list = product_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_food_store, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final MenuStoreModel menuStoreModel = product_list.get(position);
        holder.txtTenMon.setText(menuStoreModel.getName());
        holder.txtGiaTien.setText(menuStoreModel.getPrice()+"");
        holder.txtSoLuong.setTag(0);
        holder.imgTangSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = (int) holder.txtSoLuong.getTag();
                count++;

                holder.txtSoLuong.setText(count + "");
                holder.txtSoLuong.setTag(count);



                //remove tag cũ
                OrderFoodModel orderfoodTag= (OrderFoodModel) holder.imgGiamSL.getTag();
                if(orderfoodTag!=null)
                {
                    AdapterRecycleViewFoodStore.orderFoodModelList.remove(orderfoodTag);
                }

                OrderFoodModel orderFoodModel=new OrderFoodModel();
                orderFoodModel.setSoluong(count);
                orderFoodModel.setTenmon(menuStoreModel.getName());
                orderFoodModel.setGiatien(count*menuStoreModel.getPrice());

                holder.imgGiamSL.setTag(orderFoodModel);
                AdapterRecycleViewFoodStore.orderFoodModelList.add(orderFoodModel);
                for(OrderFoodModel order:AdapterRecycleViewFoodStore.orderFoodModelList)
                {
                    Log.d("cc",order.getTenmon()+" "+order.getSoluong()+" "+order.getGiatien());
                }

            }
        });
        holder.imgGiamSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = (int) holder.txtSoLuong.getTag();
                if (count != 0) {
                    count--;
                    if(count==0)
                    {
                        OrderFoodModel orderFoodModel= (OrderFoodModel) view.getTag();
                        AdapterRecycleViewFoodStore.orderFoodModelList.remove(orderFoodModel);
                        Log.d("cc2",orderFoodModelList.size()+"");
                    }
                }
                holder.txtSoLuong.setText(count + "");
                holder.txtSoLuong.setTag(count);
            }
        });
    }

    @Override
    public int getItemCount() {
        return product_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenMon, txtSoLuong,txtGiaTien;
        ImageView imgGiamSL, imgTangSL;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTenMon = itemView.findViewById(R.id.txtTenMonAnStore);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            imgGiamSL = itemView.findViewById(R.id.imgGiamSL);
            imgTangSL = itemView.findViewById(R.id.imgTangSL);
            txtGiaTien=itemView.findViewById(R.id.txtGiaTien);
        }
    }
}
