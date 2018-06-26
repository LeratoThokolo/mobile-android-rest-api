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
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;
import com.thokolo.lerato.draweractivitywithfragments.models.Supplier;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdminSuppliersActivity extends AppCompatActivity {

    private ListView listView;
    private RequestQueue requestQueueLisSuppliers;
    private ArrayList<Supplier> suppliers = new ArrayList<>();
    private AdminSupplierCustomAdapter adminSupplierCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_suppliers);
        getSupportActionBar().setTitle("Suppliers");

        this.listView = findViewById(R.id.listViewAllSuppliers);
        this.requestQueueLisSuppliers = Volley.newRequestQueue(this);
        this.getSuppliers();
    }

    private void getSuppliers() {

        String url = "http://10.0.2.2:8080/supplier/all-suppliers";

        JsonArrayRequest jsonArrayRequestAllSuppliers = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Supplier>>(){}.getType();

                        suppliers = gson.fromJson(String.valueOf(response), type);

                        if(!suppliers.isEmpty()){

                            adminSupplierCustomAdapter = new AdminSupplierCustomAdapter(AdminSuppliersActivity.this, suppliers);
                            listView.setAdapter(adminSupplierCustomAdapter);

                        }else{

                            CustomToast.createToast(AdminSuppliersActivity.this, "No suppliers on the system");
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

        this.requestQueueLisSuppliers.add(jsonArrayRequestAllSuppliers);

    }


}
