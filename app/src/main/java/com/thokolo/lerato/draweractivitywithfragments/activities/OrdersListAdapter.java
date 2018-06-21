package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.Order;

import java.util.ArrayList;

public class OrdersListAdapter extends BaseAdapter {

    private ArrayList<Order> orders;
    private Context contextOrders;
    private TextView textViewCustomerFullNames;
    private TextView textViewHouseNo;
    private TextView textViewArea;

    public OrdersListAdapter(ArrayList<Order> orders, Context contextOrders) {
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

        convertView = convertView.inflate(this.contextOrders, R.layout.orders_list_custom, null);

        this.textViewArea = convertView.findViewById(R.id.textViewArea);
        this.textViewHouseNo = convertView.findViewById(R.id.textViewOrderHouseNo);
        this.textViewCustomerFullNames = convertView.findViewById(R.id.textViewCustomerFullNames);

        this.textViewCustomerFullNames.setText("Ordered by: " + this.orders.get(position).getFullNames());
        this.textViewHouseNo.setText("Deliver to: " + this.orders.get(position).getHouseNo());
        this.textViewArea.setText("Area: " + this.orders.get(position).getArea());

        return convertView;
    }
}
