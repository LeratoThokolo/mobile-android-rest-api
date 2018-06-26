package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AdminOrdersActivity extends AppCompatActivity {

    private ListView listViewOrders;
    private ArrayList<Order> orders = new ArrayList<>();
    private RequestQueue requestQueueOrders;
    private AdminOrdersCustomAdapter adminOrdersCustomAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        getSupportActionBar().setTitle("Orders");

        this.listViewOrders = findViewById(R.id.listViewAllOrders);
        this.requestQueueOrders = Volley.newRequestQueue(this);
        this.getAllOrders();
    }

    private void getAllOrders() {

        String url = "http://10.0.2.2:8080/order/orders";
        JsonArrayRequest jsonArrayRequestAllOrders = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Order>>(){}.getType();

                        orders = gson.fromJson(String.valueOf(response), type);

                        adminOrdersCustomAdapter = new AdminOrdersCustomAdapter(AdminOrdersActivity.this, orders);
                        listViewOrders.setAdapter(adminOrdersCustomAdapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }
        );

        this.requestQueueOrders.add(jsonArrayRequestAllOrders);
    }
}
