package com.ptit.tranhoangminh.newsharefood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.R;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;

import java.util.List;

public class RecipeAdapter {
    TableLayout tableLayout;
    Context context;

    public RecipeAdapter(TableLayout tableLayout, Context context) {
        this.tableLayout = tableLayout;
        this.context = context;
    }

    private class ViewHolder {
        EditText edtRecipe;
        Button btnRemove;
        TextView tvNumber;
    }

    public void addNewRow() {
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.row_add_recipe, null, false);
        final TableRow row = new TableRow(context);
        row.addView(v);
        viewHolder.edtRecipe = v.findViewById(R.id.txtName);
        viewHolder.btnRemove = v.findViewById(R.id.buttonRemoveRow);
        viewHolder.tvNumber = v.findViewById(R.id.txtNumber);
        viewHolder.tvNumber.setText((tableLayout.getChildCount() + 1) + "");
        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(row, v);
            }
        });
        v.setTag(viewHolder);
        tableLayout.addView(row);
    }

    public void addNewRow(String st) {
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.row_add_recipe, null, false);
        final TableRow row = new TableRow(context);
        row.addView(v);
        viewHolder.edtRecipe = v.findViewById(R.id.txtName);
        viewHolder.edtRecipe.setText(st);
        viewHolder.btnRemove = v.findViewById(R.id.buttonRemoveRow);
        viewHolder.tvNumber = v.findViewById(R.id.txtNumber);
        viewHolder.tvNumber.setText((tableLayout.getChildCount() + 1) + "");
        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(row, v);
            }
        });
        v.setTag(viewHolder);
        tableLayout.addView(row);
    }

    public void removeView(TableRow r, View v) {
        int count = tableLayout.getChildCount();
        if (count > 0) {
            tableLayout.removeView(r);
            for (int i = 0; i < count - 1; i++) {
                ((ViewHolder) ((TableRow)tableLayout.getChildAt(i)).getChildAt(0).getTag()).tvNumber.setText((i + 1) + "");
            }
        }
    }
    public String getRecipe() throws Exception {
        if (tableLayout.getChildCount() < 1) {
            throw new Exception("RecipeException");
        }
        String recipe = "";
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            EditText edtRecipe = ((ViewHolder) ((TableRow) tableLayout.getChildAt(i)).getChildAt(0).getTag()).edtRecipe;
            if (edtRecipe.getText().toString().equals("")) {
                edtRecipe.setError("Empty recipe");
                return "";
            }
            recipe += edtRecipe.getText()  + "\n\n";
        }
        return recipe;
    }

    public void setRecipe(String recipe) {
        String[] arr = recipe.split("\n\n");
        for (String st : arr) {
            addNewRow(st);
        }
    }
}
