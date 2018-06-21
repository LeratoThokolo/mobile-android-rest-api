package com.thokolo.lerato.draweractivitywithfragments.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private ArrayList<Product> products = new ArrayList<>();
    private Product product = null;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RequestQueue queue;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity)getActivity()).setActionBarTitle("Products");



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.queue = Volley.newRequestQueue(view.getContext());

        //this.getProducts();


        this.recyclerView = view.findViewById(R.id.recyclerView);
        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(getContext());
        //this.recyclerViewAdapter = new ProductsAdapter(this.getAllProducts());
        this.recyclerView.setLayoutManager(this.layoutManager);
        //this.recyclerView.setAdapter(recyclerViewAdapter);

        this.getAllProducts();




        return view;
    }

    private void getAllProducts(){

        String baseUrl = "http://10.0.2.2:8080/product";

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                baseUrl + "/all-products",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Response from rest", response.toString());


                        Gson gson = new Gson();

                        Type type = new TypeToken<ArrayList<Product>>(){}.getType();

                        products = gson.fromJson(String.valueOf(response), type);

                        Log.e("Response from rest", products.toString());

                        for(int i = 0; i < products.size(); i++){

                            Log.e("Products from REST API ", products.get(i).getName() + " " + products.get(i).getUnitPrice());


                        }

                        products.get(0).setImageResource(R.drawable.heineken);
                        products.get(1).setImageResource(R.drawable.eggs);
                        products.get(2).setImageResource(R.drawable.cheese);
                        products.get(3).setImageResource(R.drawable.johnie_walker);
                        products.get(4).setImageResource(R.drawable.glen_morangie);
                        products.get(5).setImageResource(R.drawable.rich_creamy_ice_cream);
                        products.get(6).setImageResource(R.drawable.bokomo_rusks);
                        products.get(7).setImageResource(R.drawable.tennis_biscuits);
                        products.get(8).setImageResource(R.drawable.pnp_pizza);
                        products.get(9).setImageResource(R.drawable.beef_lasagne);
                        products.get(10).setImageResource(R.drawable.chicken_pnp_biryani);
                        products.get(11).setImageResource(R.drawable.bonaqua_naartjie);
                        products.get(12).setImageResource(R.drawable.nivea_men_deodorant);
                        products.get(13).setImageResource(R.drawable.camphor_cream);
                        products.get(14).setImageResource(R.drawable.nivea_men_lotion);
                        products.get(15).setImageResource(R.drawable.baby_jelly);
                        products.get(16).setImageResource(R.drawable.baby_aqueos_cream);
                        products.get(17).setImageResource(R.drawable.baby_formula);
                        products.get(18).setImageResource(R.drawable.pampers_active_dry_bay);
                        products.get(19).setImageResource(R.drawable.braai_briquets);
                        products.get(20).setImageResource(R.drawable.twinsaver);
                        products.get(21).setImageResource(R.drawable.domestos);
                        products.get(22).setImageResource(R.drawable.wireless_mouse);
                        products.get(23).setImageResource(R.drawable.external_hard_drive);




                        recyclerViewAdapter = new ProductsAdapter(products);
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

        this.queue.add(arrayRequest);

    }

}
