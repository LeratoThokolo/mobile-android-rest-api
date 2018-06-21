package com.thokolo.lerato.draweractivitywithfragments.activities;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.thokolo.lerato.draweractivitywithfragments.models.Driver;
import com.thokolo.lerato.draweractivitywithfragments.models.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverOrdersDeliveredFragment extends Fragment {

    private ArrayList<Order> orders = new ArrayList<>();
    private ListView listViewOrders;
    private RequestQueue requestQueueOrders;
    private SharedPreferences sharedPreferencesDriver;
    private int driverID;
    private DriverDeliveredOrdersAdapter driverDeliveredOrdersAdapter;
    private Driver driver;


    public DriverOrdersDeliveredFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_orders_delivered, container, false);

        Gson gson = new Gson();

        this.sharedPreferencesDriver = getActivity().getSharedPreferences("DriverLoggedIn", Context.MODE_PRIVATE);

        driver = gson.fromJson(this.sharedPreferencesDriver.getString("driver-logged-in", "Driver not logged in!!"), Driver.class);
        this.driverID = driver.getUserID();

        ((MainActivity)getActivity()).setActionBarTitle(driver.getUserName() + "'s delivered order(s)");
        this.requestQueueOrders = Volley.newRequestQueue(getContext());

        this.listViewOrders = view.findViewById(R.id.listViewDriverDeliveredOrders);

        this.getDriverDeliveredOrders();

        return view;
    }

    private void getDriverDeliveredOrders() {


        String url = "http://10.0.2.2:8080/order/get-driver-orders/" + this.driverID;


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();

                        Type type = new TypeToken<ArrayList<Order>>(){}.getType();

                        orders = gson.fromJson(String.valueOf(response), type);

                        if(!orders.isEmpty()){

                            driverDeliveredOrdersAdapter = new DriverDeliveredOrdersAdapter(orders, getContext());
                            listViewOrders.setAdapter(driverDeliveredOrdersAdapter);


                        }
                        else {

                            CustomToast.createToast(getContext(),"You did not make any deliveries");
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


        this.requestQueueOrders.add(jsonArrayRequest);

    }


}
