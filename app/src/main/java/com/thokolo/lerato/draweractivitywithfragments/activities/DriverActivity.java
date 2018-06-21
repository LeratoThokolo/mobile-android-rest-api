package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.Order;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DriverActivity extends AppCompatActivity {

    private ArrayList<Order> orders = new ArrayList<>();
    private ListView listViewOrders;
    private RequestQueue requestQueueOrders;
    private OrdersListAdapter ordersListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        getSupportActionBar().setTitle("Driver Panel");

        this.requestQueueOrders = Volley.newRequestQueue(this);
        this.listViewOrders = findViewById(R.id.listViewOrders);

        getOrders();
    }

    private void getOrders() {

        String url = "http://10.0.2.2:8080/order/get-orders";

        JsonArrayRequest jsonArrayRequestOrders = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        final Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Order>>(){}.getType();

                        orders = gson.fromJson(String.valueOf(response), type);
                        ordersListAdapter = new OrdersListAdapter(orders, getApplicationContext());
                        listViewOrders.setAdapter(ordersListAdapter);

                        listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                Order order = ordersListAdapter.getItem(position);


                                Intent intent = new Intent(DriverActivity.this, UpdateOrderActivity.class);
                                intent.putExtra("order", gson.toJson(order));
                                startActivity(intent);
                            }
                        });


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }
        );

        this.requestQueueOrders.add(jsonArrayRequestOrders);
    }


}
