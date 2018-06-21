package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.Order;

import java.util.ArrayList;

public class DriverDeliveredOrdersAdapter extends BaseAdapter {

    private ArrayList<Order> orders;
    private Context contextOrders;
    private TextView textViewOrderNo;

    public DriverDeliveredOrdersAdapter(ArrayList<Order> orders, Context contextOrders) {
        this.orders = orders;
        this.contextOrders = contextOrders;
    }

    @Override
    public int getCount() {
        return this.orders.size();
    }

    @Override
    public Order getItem(int position) {
        return this.orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.orders.get(position).getOrderID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = convertView.inflate(this.contextOrders, R.layout.custom_driver_delivered_orders, null);

        this.textViewOrderNo = convertView.findViewById(R.id.textViewOrderNo);

        this.textViewOrderNo.setText("Order # " + this.getItem(position).getOrderNumber());
        return convertView;
    }
}
