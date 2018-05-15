package com.ptit.tranhoangminh.newsharefood.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ptit.tranhoangminh.newsharefood.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    Context context;
    ArrayList<String>nameList;
    ArrayList<String>imageList;
    ArrayList<String>idList;
    ArrayList<Integer>parent_idList;

    class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewSearch;
        TextView nameSearch;
        CardView card;

        public SearchViewHolder(View itemView) {
            super(itemView);
            imageViewSearch = (ImageView) itemView.findViewById(R.id.imageViewSearch);
            nameSearch = (TextView) itemView.findViewById(R.id.txtTen);
            card = (CardView) itemView.findViewById(R.id.card);
        }
    }

    public SearchAdapter(Context context, ArrayList<String> nameList, ArrayList<String> imageList) {
        this.context = context;
        this.nameList = nameList;
        this.imageList = imageList;
        this.idList = idList;
        this.parent_idList = parent_idList;
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_search_home_layout, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder holder, int position) {
        holder.nameSearch.setText(nameList.get(position));

//        Toast.makeText(context, imageList.get(position)+"", Toast.LENGTH_SHORT).show();
        //set image
        StorageReference storageReferenceImage = FirebaseStorage.getInstance().getReference().child("Products").child(imageList.get(position)+"");
        final long ONE_MEGABYTE = 1024 * 1024;
        storageReferenceImage.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Log.d("abc", bitmap.toString());
                holder.imageViewSearch.setImageBitmap(bitmap);
            }
        });

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "dong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return nameList.size();
    }
}
