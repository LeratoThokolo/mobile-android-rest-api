package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.Order;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderCustomAdapter extends BaseAdapter {

    private ArrayList<Order> orders;
    private Context contextOrders;
    private TextView textViewOrderAmount;
    private TextView textViewOrderDate;
    private TextView textViewDelivered;
    private TextView textViewDOrderNo;

    public OrderCustomAdapter(ArrayList<Order> orders, Context contextOrders) {
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

        convertView = convertView.inflate(this.contextOrders, R.layout.customer_order_view, null);

        DecimalFormat decimalFormat = new DecimalFormat("##.#");

        this.textViewOrderAmount = convertView.findViewById(R.id.textViewOrderAmount);
        this.textViewOrderDate = convertView.findViewById(R.id.textViewOrderDate);
        this.textViewDelivered = convertView.findViewById(R.id.textViewDelivered);
        this.textViewDOrderNo = convertView.findViewById(R.id.textViewOrderNo);

        this.textViewOrderDate.setText("Order Date:" + this.orders.get(position).getDateCreated());
        this.textViewOrderAmount.setText("Amount paid:R" + decimalFormat.format(this.orders.get(position).getAmount()));
        this.textViewDelivered.setText("Delivered:" + this.orders.get(position).getDelivered());
        this.textViewDOrderNo.setText("Order #:" + this.orders.get(position).getOrderNumber());
        return convertView;
    }
}
