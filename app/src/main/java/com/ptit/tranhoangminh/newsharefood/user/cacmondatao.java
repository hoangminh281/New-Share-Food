package com.ptit.tranhoangminh.newsharefood.user;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.ptit.tranhoangminh.newsharefood.R;
        import com.ptit.tranhoangminh.newsharefood.models.Product;
        import com.ptit.tranhoangminh.newsharefood.views.CategoryViews.activities.CategoryActivity;
        import com.ptit.tranhoangminh.newsharefood.views.NewProductDetailViews.activities.NewProductDetailActivity;
        import com.ptit.tranhoangminh.newsharefood.views.ProductViews.activities.ProductActivity;

        import java.util.ArrayList;

public class cacmondatao extends Fragment {
    View view;
    private ListView lvmonan;
    private ArrayList<Product> arrayMonAn;
    private MemberAdapter adapter;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private String userID;
    private cacmondatao fragmentCacMonDaTao;
    private DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.createfood, container, false);

        //lay user
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            userID = user.getUid();
            //anh xa
            lvmonan = view.findViewById(R.id.listviewmonan);
            arrayMonAn = new ArrayList<>();


            //phan cac mon da tao
            adapter = new MemberAdapter(getContext(), R.layout.dong_member, arrayMonAn);
            lvmonan.setAdapter(adapter);
            //lay du lieu
            laydulieu();
            lvmonan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), NewProductDetailActivity.class);
                    intent.putExtra("objectKey",arrayMonAn.get(i));
                    startActivity(intent);
                }
            });
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    void laydulieu(){
        mRef.child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for(DataSnapshot ds: iterable){
                    Product product = ds.getValue(Product.class);
                    if(userID.equals(product.getMember_id())){
                        arrayMonAn.add(product);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}