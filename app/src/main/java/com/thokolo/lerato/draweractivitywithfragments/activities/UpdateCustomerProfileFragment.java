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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.Customer;
import com.thokolo.lerato.draweractivitywithfragments.models.User;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateCustomerProfileFragment extends Fragment {

    private View view;
    private ListView listView;
    private ArrayList<Customer> customers = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private CustomerProfileAdapter customerProfileAdapter;
    private SharedPreferences customerPreferences;


    public UpdateCustomerProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity)getActivity()).setActionBarTitle("Profile");

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_update_customer_profile, container, false);
        this.customerPreferences = getActivity().getSharedPreferences("CustomerLoggedIn", Context.MODE_PRIVATE);
        this.listView = view.findViewById(R.id.customer_profile);
        this.getLoggedInCustomer();

        return view;
    }

    private void getLoggedInCustomer(){


        final Gson gson = new Gson();
        Customer customer = gson.fromJson(this.customerPreferences.getString("customer-logged-in", "Customer not found!!"), Customer.class);

        this.customers.add(customer);
        this.customerProfileAdapter = new CustomerProfileAdapter(getContext(), customers);
        this.listView.setAdapter(customerProfileAdapter);

    }
}

