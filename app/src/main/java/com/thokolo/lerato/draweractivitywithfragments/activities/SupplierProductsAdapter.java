package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.Product;

import java.util.ArrayList;

public class SupplierProductsAdapter extends BaseAdapter {

    private ArrayList<Product> products;
    private Context contextProducts;
    private TextView textViewProductName;
    private TextView textViewMinimumQuantity;
    private TextView textViewQuantity;


    public SupplierProductsAdapter(ArrayList<Product> products, Context contextProducts) {
        this.products = products;
        this.contextProducts = contextProducts;
    }

    @Override
    public int getCount() {
        return this.products.size();
    }

    @Override
    public Product getItem(int position) {
        return this.products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.products.get(position).getProductID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView  = convertView.inflate(this.contextProducts, R.layout.supplier_products_custom, null);

        this.textViewProductName = convertView.findViewById(R.id.textViewProductName);
        this.textViewMinimumQuantity = convertView.findViewById(R.id.textViewMinimumQuantity);
        this.textViewQuantity = convertView.findViewById(R.id.textViewQuantity);

        this.textViewQuantity.setText("Quantity:" + this.products.get(position).getQuantity());
        this.textViewMinimumQuantity.setText("Minimum Quantity:" + this.products.get(position).getMinimumQuantity());
        this.textViewProductName.setText(this.products.get(position).getName());

        return convertView;
    }
}
