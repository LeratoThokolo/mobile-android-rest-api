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
import com.thokolo.lerato.draweractivitywithfragments.models.Category;
import com.thokolo.lerato.draweractivitywithfragments.models.Driver;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdminDriverActivity extends AppCompatActivity {

    private ListView listView;
    private RequestQueue requestQueueListDrivers;
    private ArrayList<Driver> drivers = new ArrayList<>();
    private AdminDriverCustomAdapter adminDriverCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_driver);
        getSupportActionBar().setTitle("Drivers");

        this.listView = findViewById(R.id.listViewAllDrivers);
        this.requestQueueListDrivers = Volley.newRequestQueue(this);

        this.getAllDrivers();
    }

    private void getAllDrivers() {

        String url = new String("http://10.0.2.2:8080/driver/all-drivers");

        JsonArrayRequest jsonArrayRequestDrivers = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Driver>>(){}.getType();

                        drivers = gson.fromJson(String.valueOf(response), type);

                        adminDriverCustomAdapter = new AdminDriverCustomAdapter(AdminDriverActivity.this, drivers);
                        listView.setAdapter(adminDriverCustomAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }
        );

        this.requestQueueListDrivers.add(jsonArrayRequestDrivers);

    }
}
