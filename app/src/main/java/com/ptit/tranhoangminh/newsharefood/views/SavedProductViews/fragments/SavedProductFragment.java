package com.ptit.tranhoangminh.newsharefood.views.SavedProductViews.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.adapters.SavedProductAdapter;
import com.ptit.tranhoangminh.newsharefood.models.ProductSQLite;
import com.ptit.tranhoangminh.newsharefood.presenters.savedProductPresenters.SavedProductPresenter;
import com.ptit.tranhoangminh.newsharefood.views.SavedProductDetailViews.activities.SavedProductDetailActivity;
import com.ptit.tranhoangminh.newsharefood.views.SavedProductViews.activities.SavedProductActivity;
import com.ptit.tranhoangminh.newsharefood.views.SavedProductViews.activities.SavedProductView;

import java.util.ArrayList;
import java.util.List;

public class SavedProductFragment extends Fragment implements SavedProductView {
    private List<ProductSQLite> productArrayList;
    private SavedProductAdapter myAdapter;
    private GridView gridView;
    private ProgressBar pgbSavedProduct;
    private SavedProductPresenter savedProductPresenter;
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.products_layout, null);
        setControls(view);

        initPresenter();
        savedProductPresenter.loadAllSavedProducts();
        registerForContextMenu(gridView);
        toolbar.setVisibility(View.GONE);
        setEvents();
        return view;
    }


    private void setControls(View view) {
        gridView = view.findViewById(R.id.gridview);
        pgbSavedProduct = view.findViewById(R.id.progressBarProduct);
        toolbar = view.findViewById(R.id.toolbar);
    }

    private void initPresenter() {
        savedProductPresenter = new SavedProductPresenter(this, getActivity());
    }

    void setEvents() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailProductIntent = new Intent(getActivity(), SavedProductDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("objectKey", productArrayList.get(position).getId());
                detailProductIntent.putExtras(bundle);
                startActivity(detailProductIntent);
            }
        });
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            savedProductPresenter.loadAllSavedProducts();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_context, menu);
        menu.setHeaderTitle("Chọn chức năng");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuXoa:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                final ProductSQLite pd = myAdapter.getItem((int) info.id);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Xác nhận xóa");
                alertDialog.setMessage("Bạn thật sự muốn xóa sản phẩm này?");

                alertDialog.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        savedProductPresenter.destroyProductOnSQLite(pd.getId());
                    }
                }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void showProgress() {
        pgbSavedProduct.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pgbSavedProduct.setVisibility(View.GONE);
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displaySavedProducts(List<ProductSQLite> savedProductList) {
        this.productArrayList = savedProductList;
        this.myAdapter = new SavedProductAdapter(getActivity(), R.layout.dong_products, productArrayList);
        gridView.setAdapter(myAdapter);
    }
}
