package com.ptit.tranhoangminh.newsharefood.views.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.adapters.SearchAdapter;

import java.util.ArrayList;

public class HomeSearchFragment extends Fragment {

    RecyclerView recyclerViewSearch;
    DatabaseReference databaseReference;
    String textSearch;

    ArrayList arrName;
    ArrayList arrHinh;
    SearchAdapter searchAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homesearchfragment, container, false);

        recyclerViewSearch = (RecyclerView) view.findViewById(R.id.recycleViewSearch);
        recyclerViewSearch.setHasFixedSize(true);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewSearch.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        arrName = new ArrayList<>();
        arrHinh = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getArguments();
        if(bundle!=null){
            textSearch = bundle.getString("keySearch");
            //firebaseSearch(textSearch);
            setAdapter(textSearch);
            // Toast.makeText(getActivity(), textSearch, Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    public void setAdapter(final String textSearch) {
        databaseReference.child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrName.clear();
                arrHinh.clear();
                recyclerViewSearch.removeAllViews();
                int count = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String name = snapshot.child("name").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);

                    if(name.toLowerCase().contains(textSearch)){
                        arrName.add(name);
                        arrHinh.add(image);
                        count++;
                    }

                    if(count == 15) break;
                }

                searchAdapter = new SearchAdapter(getActivity(), arrName, arrHinh);
                recyclerViewSearch.setAdapter(searchAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


//    public void firebaseSearch(String keyword) {
//        // Toast.makeText(getActivity(), "search started", Toast.LENGTH_SHORT).show();
//        Query firebaseSearchQuery = databaseReference.orderByChild("name").startAt(keyword).endAt(keyword + "\uf8ff");
//
//        FirebaseRecyclerOptions<HomeSearch> options = new FirebaseRecyclerOptions.Builder<HomeSearch>()
//                .setQuery(firebaseSearchQuery, HomeSearch.class)
//                .build();
//
//        final FirebaseRecyclerAdapter<HomeSearch, SearchViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<HomeSearch, SearchViewHolder>(options) {
//            @NonNull
//            @Override
//            public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext())
//                        .inflate(R.layout.list_search_home_layout, parent, false);
//
//                return new SearchViewHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull SearchViewHolder holder, int position, @NonNull HomeSearch model) {
//                // holder.setDetails(model.getImage(), model.getName());
//                holder.nameSearch.setText("yyy");
//                holder.imageViewSearch.setImageResource(R.drawable.bg2);
//                Log.d("aaa", model.getName());
//            }
//
//        };
//
//        recyclerViewSearch.setAdapter(firebaseRecyclerAdapter);
//    }
//
//    // View Holder class
//    public static class SearchViewHolder extends RecyclerView.ViewHolder {
//
//        View mView;
//        ImageView imageViewSearch;
//        TextView nameSearch;
//
//        public SearchViewHolder(View itemView) {
//            super(itemView);
//
//            mView = itemView;
//            imageViewSearch = (ImageView) mView.findViewById(R.id.imageViewSearch);
//            nameSearch = (TextView) mView.findViewById(R.id.txtTen);
//        }
//
//        public void setDetails(String image, String name) {
//            ImageView imageViewSearch = (ImageView) mView.findViewById(R.id.imageViewSearch);
//            TextView nameSearch = (TextView) mView.findViewById(R.id.txtTen);
//
//            nameSearch.setText(name);
//        }
//    }
}
