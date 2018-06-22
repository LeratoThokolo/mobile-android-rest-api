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

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AdminCategoriesActivity extends AppCompatActivity {

    private ListView listView;
    private RequestQueue requestQueueListCategories;
    private ArrayList<Category> categories = new ArrayList<>();
    private AdminCategoryCustomAdapter adminCategoryCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_categories);
        getSupportActionBar().setTitle("Categories");

        this.listView = findViewById(R.id.listViewAllCategories);
        this.requestQueueListCategories = Volley.newRequestQueue(this);
        
        this.getCategories();
    }

    private void getCategories() {

        String url = "http://10.0.2.2:8080/category/all-categories";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Category>>(){}.getType();

                        categories = gson.fromJson(String.valueOf(response), type);

                        adminCategoryCustomAdapter = new AdminCategoryCustomAdapter(AdminCategoriesActivity.this, categories);
                        listView.setAdapter(adminCategoryCustomAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }
        );

        this.requestQueueListCategories.add(jsonArrayRequest);
    }


}
