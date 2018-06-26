package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;
import com.thokolo.lerato.draweractivitywithfragments.models.Order;


import org.json.JSONObject;

import java.util.ArrayList;

public class AdminOrdersCustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Order> orders;
    private TextView textViewOrderNo;
    private TextView textViewDelivered;
    private ImageView imageViewDelete;
    private RequestQueue requestQueueOrdersDelete;

    public AdminOrdersCustomAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = convertView.inflate(this.context, R.layout.admin_orders_custom, null);
        this.textViewDelivered = convertView.findViewById(R.id.textViewDelivered);
        this.textViewOrderNo = convertView.findViewById(R.id.textViewOrderNo);
        this.imageViewDelete = convertView.findViewById(R.id.imageViewDelete);
        this.requestQueueOrdersDelete = Volley.newRequestQueue(context);

        this.textViewOrderNo.setText("Order #: " + this.getItem(position).getOrderNumber());
        this.textViewDelivered.setText("Delivered: " + this.getItem(position).getDelivered());

        this.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://10.0.2.2:8080/order/remove-order/" + getItem(position).getOrderID();

                JsonObjectRequest jsonObjectRequestDelete = new JsonObjectRequest(
                        Request.Method.DELETE,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                CustomToast.createToast(context, "Order # " + getItem(position).getOrderNumber() + " deleted successfully");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                error.printStackTrace();
                            }
                        }
                );

                requestQueueOrdersDelete.add(jsonObjectRequestDelete);
            }
        });
        return convertView;
    }
}
