package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;
import com.thokolo.lerato.draweractivitywithfragments.models.Product;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SupplierActivity extends AppCompatActivity {

    private ArrayList<Product> products = new ArrayList<>();
    private SharedPreferences sharedPreferencesSupplierProducts;
    private ListView listViewSupplierProducts;
    private SupplierProductsAdapter supplierProductsAdapter;
    private RequestQueue requestQueueSupplier;
    private int supplierID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        getSupportActionBar().setTitle("Supplier Panel");

        this.requestQueueSupplier = Volley.newRequestQueue(this);
        this.sharedPreferencesSupplierProducts = getSharedPreferences("SupplierProducts", Context.MODE_PRIVATE);
        this.listViewSupplierProducts = findViewById(R.id.listViewSupplierProducts);

        this.supplierID = getIntent().getIntExtra("supplierID", -1);

        final Gson gson = new Gson();
        final Gson gson2 = new Gson();
        String urlSupplier = "http://10.0.2.2:8080/product/all-products-by-supplier-id/" + this.supplierID;

        JsonArrayRequest jsonArrayRequestSupplierProducts = new JsonArrayRequest(
                Request.Method.GET,
                urlSupplier,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Type type = new TypeToken<ArrayList<Product>>(){}.getType();

                        products = gson2.fromJson(String.valueOf(response), type);

                        if(!products.isEmpty()){

                            supplierProductsAdapter = new SupplierProductsAdapter(products, SupplierActivity.this);
                            listViewSupplierProducts.setAdapter(supplierProductsAdapter);

                        }else{

                            CustomToast.createToast(SupplierActivity.this, "Refresh or re-login");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }

        );
        this.requestQueueSupplier.add(jsonArrayRequestSupplierProducts);

        this.listViewSupplierProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(SupplierActivity.this, SupplierUpdateActivity.class);
                intent.putExtra("product", gson.toJson(supplierProductsAdapter.getItem(position)));
                startActivity(intent);

            }
        });
    }
}
