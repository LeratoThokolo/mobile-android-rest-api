package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.Supplier;

import java.util.ArrayList;

public class SuppliersCustomAdapter extends BaseAdapter {

    private ArrayList<Supplier> suppliers;
    private Context context;
    private TextView textViewSupplierName;

    public SuppliersCustomAdapter(ArrayList<Supplier> suppliers, Context context) {
        this.suppliers = suppliers;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.suppliers.size();
    }

    @Override
    public Supplier getItem(int position) {
        return this.suppliers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.suppliers.get(position).getUserID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = convertView.inflate(this.context, R.layout.suppliers_custom_layout, null);
        this.textViewSupplierName = convertView.findViewById(R.id.textViewSupplierName);

        this.textViewSupplierName.setText(this.getItem(position).getFullNames());

        return convertView;
    }
}
