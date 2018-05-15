package com.ptit.tranhoangminh.newsharefood.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.ptit.tranhoangminh.newsharefood.R;

import org.xmlpull.v1.XmlPullParser;

import java.util.List;

public class MaterialAdapter {
    TableLayout tableLayout;
    Context context;

    public MaterialAdapter(TableLayout tableLayout, Context context) {
        this.tableLayout = tableLayout;
        this.context = context;
    }

    private class ViewHolder {
        EditText edtName, edtQuantity;
        Button btnRemove;
    }

    public void addNewRow() {
        final ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.row_add_material, null, false);
        final TableRow row = new TableRow(context);
        row.addView(v);
        viewHolder.edtName = v.findViewById(R.id.txtName);
        viewHolder.edtQuantity = v.findViewById(R.id.txtQuantity);
        viewHolder.btnRemove = v.findViewById(R.id.buttonRemoveRow);
        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(row);
            }
        });
        v.setTag(viewHolder);
        tableLayout.addView(row);
    }

    public void addNewRow(String quantity, String material) {
        final ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.row_add_material, null, false);
        final TableRow row = new TableRow(context);
        row.addView(v);
        viewHolder.edtName = v.findViewById(R.id.txtName);
        viewHolder.edtName.setText(material);
        viewHolder.edtQuantity = v.findViewById(R.id.txtQuantity);
        viewHolder.edtQuantity.setText(quantity);
        viewHolder.btnRemove = v.findViewById(R.id.buttonRemoveRow);
        viewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(row);
            }
        });
        v.setTag(viewHolder);
        tableLayout.addView(row);
    }

    public void removeView(TableRow r) {
        int count = tableLayout.getChildCount();
        if (count > 0) {
            tableLayout.removeView(r);
        }
    }

    public String getMaterials() throws Exception {
        if (tableLayout.getChildCount() < 1) {
            throw new Exception("MaterialException");
        }
        String material = "";
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            EditText edtName = ((ViewHolder) ((TableRow) tableLayout.getChildAt(i)).getChildAt(0).getTag()).edtName;
            EditText edtQuantity = ((ViewHolder) ((TableRow) tableLayout.getChildAt(i)).getChildAt(0).getTag()).edtQuantity;
            if (edtName.getText().toString().equals("")) {
                edtName.setError("Empty material");
                return "";
            }
            if (edtQuantity.getText().toString().equals("")) {
                edtQuantity.setError("Empty quantity");
                return "";
            }
            material += edtQuantity.getText() + "\t" + edtName.getText() + "\n\n";
        }
        return material;
    }


    public void setMaterials(String materials) {
        String[] arr = materials.split("\n\n");
        for (String st : arr) {
            String[] row = st.split("\t");
            if (row.length == 2) addNewRow(row[0], row[1]);
            else addNewRow("",row[0]);
        }
    }
}
