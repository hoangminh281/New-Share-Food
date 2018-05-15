package com.ptit.tranhoangminh.newsharefood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.R;
import com.ptit.tranhoangminh.newsharefood.models.Category;
import com.ptit.tranhoangminh.newsharefood.views.AddEditProductViews.activities.AddEditProductView;

import org.xmlpull.v1.XmlPullParser;

import java.util.List;

public class CategoryCollapseAdapter {
    TableLayout tableLayout;
    Context context;
    AddEditProductView listener;


    public CategoryCollapseAdapter(TableLayout tableLayout, Context context, AddEditProductView listener) {
        this.tableLayout = tableLayout;
        this.context = context;
        this.listener = listener;
    }

    private class ViewHolder {
        Button btnCategory;
    }

    public void addView(List<Category> categories) {
        final ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(context);
        for (Category cate : categories) {
            final View v = inflater.inflate(R.layout.row_choose_categories, null, false);
            final TableRow row = new TableRow(context);
            row.addView(v);
            viewHolder.btnCategory = v.findViewById(R.id.buttonCategory);
            viewHolder.btnCategory.setText(cate.getName());
            viewHolder.btnCategory.setTag(cate);
            viewHolder.btnCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.setCategory((Category) v.getTag());
                }
            });
            v.setTag(viewHolder);
            tableLayout.addView(row);
        }
    }
}
