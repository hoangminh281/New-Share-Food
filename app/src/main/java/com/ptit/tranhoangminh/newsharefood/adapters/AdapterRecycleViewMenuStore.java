package com.ptit.tranhoangminh.newsharefood.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.CategoryStoreModel;

import java.util.List;

public class AdapterRecycleViewMenuStore extends RecyclerView.Adapter<AdapterRecycleViewMenuStore.ViewHolder> {
    Context context;
    List<CategoryStoreModel> categoryStoreModelList;

    public AdapterRecycleViewMenuStore(Context context, List<CategoryStoreModel> categoryStoreModelList) {
        this.context = context;
        this.categoryStoreModelList = categoryStoreModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_menu_store, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryStoreModel categoryStoreModel=categoryStoreModelList.get(position);
        holder.txtTenLoai.setText(categoryStoreModel.getCategory_name());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        AdapterRecycleViewFoodStore adapter=new AdapterRecycleViewFoodStore(context,categoryStoreModel.getMenuStoreModelListl());
        holder.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return categoryStoreModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenLoai;
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoaiMon);
            recyclerView = itemView.findViewById(R.id.recycleMenuDetail);
        }
    }
}
