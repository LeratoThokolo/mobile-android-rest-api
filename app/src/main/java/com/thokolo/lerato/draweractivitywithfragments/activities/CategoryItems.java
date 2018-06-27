package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

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
import com.thokolo.lerato.draweractivitywithfragments.models.Product;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CategoryItems extends AppCompatActivity {

    private TextView textViewCategory;
    private SharedPreferences sharedPreferences;
    private Category category = null;
    int categoryID = 0;
    private RequestQueue requestQueue;
    private ArrayList<Product> products = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);

        this.requestQueue = Volley.newRequestQueue(this);
        Gson gson = new Gson();
        sharedPreferences = getSharedPreferences("CategoryValue", Context.MODE_PRIVATE);
        this.category = gson.fromJson(sharedPreferences.getString("category", "Data not found"), Category.class);
        this.categoryID = this.category.getCategoryID();
        getSupportActionBar().setTitle(this.category.getName());

        this.recyclerView = findViewById(R.id.recyclerViewItems);
        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.layoutManager);

        this.getProductsByCategoryID();
    }


    private void getProductsByCategoryID(){

        String baseUrl = "http://10.0.2.2:8080/product";

        JsonArrayRequest arrayRequest = new JsonArrayRequest(

                Request.Method.GET,
                baseUrl + "/all-products-by-category/" + this.categoryID,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();

                        Type type = new TypeToken<ArrayList<Product>>(){}.getType();

                        products = gson.fromJson(String.valueOf(response), type);

                        for(int i = 0; i < products.size(); i++){
                            Log.e("Products list", products.get(i).getName());
                        }

                        switch (categoryID){

                            case 55:

                                products.get(0).setImageResource(R.drawable.heineken);
                                products.get(1).setImageResource(R.drawable.johnie_walker);
                                products.get(2).setImageResource(R.drawable.glen_morangie);
                                break;

                            case 56:

                                products.get(0).setImageResource(R.drawable.eggs);
                                products.get(1).setImageResource(R.drawable.cheese);
                                break;

                            case 57:

                                products.get(0).setImageResource(R.drawable.rich_creamy_ice_cream);
                                break;

                            case 58:

                                products.get(0).setImageResource(R.drawable.bokomo_rusks);
                                products.get(1).setImageResource(R.drawable.tennis_biscuits);

                                break;

                            case 59:

                                products.get(0).setImageResource(R.drawable.pnp_pizza);
                                products.get(1).setImageResource(R.drawable.beef_lasagne);
                                products.get(2).setImageResource(R.drawable.chicken_pnp_biryani);

                                break;

                            case 414:

                                products.get(0).setImageResource(R.drawable.braaimaster);

                                break;

                            case 69:

                                products.get(0).setImageResource(R.drawable.nivea_men_deodorant);
                                products.get(1).setImageResource(R.drawable.camphor_cream);
                                products.get(2).setImageResource(R.drawable.nivea_men_lotion);

                                break;

                            case 71:

                                products.get(0).setImageResource(R.drawable.twinsaver);
                                products.get(1).setImageResource(R.drawable.domestos);

                                break;

                            case 152:

                                products.get(0).setImageResource(R.drawable.bonaqua_naartjie);

                                break;

                            case 32:

                                products.get(0).setImageResource(R.drawable.baby_jelly);
                                products.get(1).setImageResource(R.drawable.baby_aqueos_cream);
                                products.get(2).setImageResource(R.drawable.baby_formula);
                                products.get(3).setImageResource(R.drawable.pampers_active_dry_bay);

                                break;

                            case 36:

                                products.get(0).setImageResource(R.drawable.wireless_mouse);
                                products.get(1).setImageResource(R.drawable.external_hard_drive);

                                break;

                                default:
                                    return;

                        }

                        recyclerViewAdapter  = new ProductsAdapter(products);
                        recyclerView.setAdapter(recyclerViewAdapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }
        );

        this.requestQueue.add(arrayRequest);

    }
}
