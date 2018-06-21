package com.thokolo.lerato.draweractivitywithfragments.activities;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.ApplicationHeaders;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;
import com.thokolo.lerato.draweractivitywithfragments.models.Customer;
import com.thokolo.lerato.draweractivitywithfragments.models.Driver;
import com.thokolo.lerato.draweractivitywithfragments.models.LoginResponse;
import com.thokolo.lerato.draweractivitywithfragments.models.Product;
import com.thokolo.lerato.draweractivitywithfragments.models.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;
    private Button loginButton;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private View view;
    private int customerID;
    private RequestQueue requestQueueCustomer;
    private int driverID;
    private RequestQueue requestQueueDriver;
    private RequestQueue requestQueueSupplier;
    private int supplierID;
    private ArrayList<Product> products = new ArrayList<>();




    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity)getActivity()).setActionBarTitle("Login");

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);


        this.initializeGadgets();
        this.loginButton.setOnClickListener(login());
        this.requestQueue = Volley.newRequestQueue(view.getContext());
        this.requestQueueDriver = Volley.newRequestQueue(view.getContext());
        this.requestQueueCustomer = Volley.newRequestQueue(view.getContext());
        this.requestQueueSupplier = Volley.newRequestQueue(view.getContext());

        return view;
    }

    private void initializeGadgets(){

        this.textInputLayoutUsername = view.findViewById(R.id.textInputUsername);
        this.textInputLayoutPassword = view.findViewById(R.id.textInputPassword);
        this.loginButton = view.findViewById(R.id.buttonLogin);
    }

    private View.OnClickListener login() {

        View.OnClickListener accessApp = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginLogic();


            }
        };
        return accessApp;
    }

    private boolean validateUserName(){

        if( textInputLayoutUsername.getEditText().getText().toString().trim().isEmpty()){

            textInputLayoutUsername.setError("User name is required!!");
            return false;

        }else{

            textInputLayoutUsername.setError(null);

            return true;
        }
    }

    private boolean validatePassword(){

        if(textInputLayoutPassword.getEditText().getText().toString().trim().isEmpty()){

            textInputLayoutPassword.setError("Password is required!!");
            return  false;
        }
        else{

            textInputLayoutPassword.setError(null);
            return true;
        }
    }

    private void loginLogic(){

        if(!validateUserName() | !validatePassword()){

            return;
        }else {

            String userName = textInputLayoutUsername.getEditText().getText().toString();
            String password = textInputLayoutPassword.getEditText().getText().toString();
            String url = "http://10.0.2.2:8080/user/login-user";
            final Gson gson = new Gson();

            Map<String, String> params = new HashMap<String, String>();
            params.put("userName", userName);
            params.put("password", password);

            JSONObject loginObject = new JSONObject(params);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(

                    Request.Method.POST,
                    url,
                    loginObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            LoginResponse loginResponse = gson.fromJson(String.valueOf(response), LoginResponse.class);
                            Log.e("Login response :", loginResponse.getUser().getUserName() + " has " + loginResponse.getMessageResponse());

                            if(loginResponse.getUser().getUserName() != null){

                                sharedPreferences = getActivity().getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user-logged-in", gson.toJson(loginResponse.getUser()));
                                editor.apply();

                                customerID = loginResponse.getUser().getUserID();
                                driverID = loginResponse.getUser().getUserID();
                                supplierID = loginResponse.getUser().getUserID();
                                String userRole = loginResponse.getUser().getRoles().get(0).getName();
                                redirectBasedOnRole(userRole);



                            }
                            else if(loginResponse.getUser().getUserName() == null){

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                                        .setTitle("Login response")
                                        .setMessage(loginResponse.getMessageResponse() + "\n" + "Forgotten password?")
                                        .setPositiveButton("Recover password", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                startActivity(new Intent(getContext(), RecoverPasswordActivity.class));
                                            }
                                        })
                                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            error.printStackTrace();
                        }
                    }
            ){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put(ApplicationHeaders.HEADERS, ApplicationHeaders.HEADERSVALUE);

                    return headers;
                }
            };

            requestQueue.add(jsonObjectRequest);
        }
    }

    private void redirectBasedOnRole(String role){

        switch (role){

            case "Admin":
                startActivity(new Intent(getContext(), TabbedActivity.class));
                break;

            case "Driver":

                final Gson gson = new Gson();
                String url = "http://10.0.2.2:8080/driver/single-driver/" + this.driverID;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Driver driver = gson.fromJson(String.valueOf(response), Driver.class);
                                SharedPreferences driverPreferences = getActivity().getSharedPreferences("DriverLoggedIn", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = driverPreferences.edit();
                                editor.putString("driver-logged-in", gson.toJson(driver));
                                editor.apply();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                error.printStackTrace();
                            }
                        }
                );

                this.requestQueueDriver.add(jsonObjectRequest);
                startActivity(new Intent(getContext(), DriverActivity.class));
                break;

            case "Customer":

                final Gson gson1 = new Gson();
                String url1 = "http://10.0.2.2:8080/customer/customer-details/" + this.customerID;

                JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(
                        Request.Method.GET,
                        url1,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Customer customer = gson1.fromJson(String.valueOf(response), Customer.class);
                                SharedPreferences customerPreferences = getActivity().getSharedPreferences("CustomerLoggedIn", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = customerPreferences.edit();
                                editor.putString("customer-logged-in", gson1.toJson(customer));
                                editor.apply();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                error.printStackTrace();
                            }
                        }
                );

                this.requestQueueCustomer.add(jsonObjectRequest1);

                startActivity(new Intent(getContext(), MainActivity.class));
                break;

            case "Supplier":

                Intent intent = new Intent(getContext(), SupplierActivity.class);
                intent.putExtra("supplierID", this.supplierID);
                startActivity(intent);

                break;

                default:
                    startActivity(new Intent(getContext(), MainActivity.class));

        }

    }

}
