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
import com.thokolo.lerato.draweractivitywithfragments.models.Product;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdminProductsActivity extends AppCompatActivity {

    private ListView listView;
    private RequestQueue requestQueueAllProducts;
    private ArrayList<Product> products = new ArrayList<>();
    private AdminProductsAdapterCustom adminProductsAdapterCustom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_products);

        getSupportActionBar().setTitle("All products");

        this.listView = findViewById(R.id.listViewAllProducts);
        this.requestQueueAllProducts = Volley.newRequestQueue(this);

        this.getAllProducts();
    }

    private void getAllProducts() {

        String url = "http://10.0.2.2:8080/product/all-products";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();

                        Type type = new TypeToken<ArrayList<Product>>(){}.getType();
                        products = gson.fromJson(String.valueOf(response), type);

                        adminProductsAdapterCustom = new AdminProductsAdapterCustom(products, AdminProductsActivity.this);
                        listView.setAdapter(adminProductsAdapterCustom);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }
        );

        this.requestQueueAllProducts.add(jsonArrayRequest);

    }
}
