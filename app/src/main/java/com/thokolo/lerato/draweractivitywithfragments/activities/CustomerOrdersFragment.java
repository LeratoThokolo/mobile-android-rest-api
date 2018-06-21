package com.thokolo.lerato.draweractivitywithfragments.activities;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.thokolo.lerato.draweractivitywithfragments.models.Customer;
import com.thokolo.lerato.draweractivitywithfragments.models.Order;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerOrdersFragment extends Fragment {

    private ArrayList<Order> orders = new ArrayList<>();
    private RequestQueue requestQueueCustomerOrders;
    private ListView listViewOrders;
    private SharedPreferences sharedPreferencesCustomer;
    private Customer customer;
    private int customerID;
    private OrderCustomAdapter orderCustomAdapter;

    public CustomerOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_orders, container, false);

        this.sharedPreferencesCustomer = getActivity().getSharedPreferences("CustomerLoggedIn", Context.MODE_PRIVATE);
        this.listViewOrders = view.findViewById(R.id.list_view_customer_orders);
        Gson gson = new Gson();

        this.customer = gson.fromJson(this.sharedPreferencesCustomer.getString("customer-logged-in", "Customer not logged in"), Customer.class);

        ((MainActivity)getActivity()).setActionBarTitle(this.customer.getUserName() + "'s order(s)");
        this.customerID = this.customer.getUserID();
        this.requestQueueCustomerOrders = Volley.newRequestQueue(getContext());

        getCustomerOrders();

        return view;
    }

    private void getCustomerOrders() {

        String url = "http://10.0.2.2:8080/order/customer-orders/" + this.customerID;

        JsonArrayRequest jsonArrayRequestOrders = new JsonArrayRequest(
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

                            orderCustomAdapter = new OrderCustomAdapter(orders, getContext());
                            listViewOrders.setAdapter(orderCustomAdapter);

                        }
                        else{

                            CustomToast.createToast(getContext(), "No previous orders");
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

        this.requestQueueCustomerOrders.add(jsonArrayRequestOrders);
    }


}
