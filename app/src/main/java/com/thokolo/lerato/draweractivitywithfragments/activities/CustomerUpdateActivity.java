package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.ApplicationHeaders;
import com.thokolo.lerato.draweractivitywithfragments.models.Customer;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerUpdateActivity extends AppCompatActivity {

    private SharedPreferences customerPreferences;
    private Customer customer;
    private RequestQueue requestQueue;
    private TextInputLayout textInputLayoutFullNames;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutCellNo;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutHouseNo;
    private TextInputLayout textInputLayoutStreetName;
    private TextInputLayout textInputLayoutSurburb;
    private TextInputLayout textInputLayoutCity;
    private TextInputLayout textInputLayoutPostalCode;
    private Button buttonUpdate;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_update);
        getSupportActionBar().setTitle("Update profile");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4682b4")));
        this.requestQueue = Volley.newRequestQueue(this);
        this.customerPreferences = getSharedPreferences("CustomerLoggedIn", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        customer = gson.fromJson(this.customerPreferences.getString("customer-logged-in", "Customer not logged in"), Customer.class);
        this.userID = customer.getUserID();
        this.initializeViews();


    }

    private void initializeViews() {

        this.textInputLayoutCellNo = findViewById(R.id.textInputLayoutCellNo);
        this.textInputLayoutCellNo.getEditText().setText(this.customer.getCellNo());


        this.textInputLayoutCity = findViewById(R.id.textInputLayoutCity);
        this.textInputLayoutCity.getEditText().setText(this.customer.getTown());


        this.textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        this.textInputLayoutEmail.getEditText().setText(this.customer.getEmail());


        this.textInputLayoutFullNames = findViewById(R.id.textInputLayoutFullNames);
        this.textInputLayoutFullNames.getEditText().setText(this.customer.getFullNames());


        this.textInputLayoutHouseNo = findViewById(R.id.textInputLayoutHouseNo);
        this.textInputLayoutHouseNo.getEditText().setText(this.customer.getHouseNo());


        this.textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        this.textInputLayoutPassword.getEditText().setText(this.customer.getPassword());


        this.textInputLayoutPostalCode = findViewById(R.id.textInputLayoutPostalCode);
        this.textInputLayoutPostalCode.getEditText().setText(this.customer.getPostalCode());


        this.textInputLayoutStreetName = findViewById(R.id.textInputLayoutStreetName);
        this.textInputLayoutStreetName.getEditText().setText(this.customer.getStreetName());


        this.textInputLayoutSurburb = findViewById(R.id.textInputLayoutSurburb);
        this.textInputLayoutSurburb.getEditText().setText(this.customer.getSurburb());


        this.textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername);
        this.textInputLayoutUsername.getEditText().setText(this.customer.getUserName());


        this.buttonUpdate = findViewById(R.id.buttonUpdate);
        this.buttonUpdate.setOnClickListener(this.updateProfile());

    }

    private boolean validateFullNames(){

        if(this.textInputLayoutFullNames.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutFullNames.setError("Full names are required!!");
            return  false;

        }
        else if(this.textInputLayoutFullNames.getEditText().getText().length() > 30){

            this.textInputLayoutFullNames.setError("Full names are too long!!");
            return false;
        }
        else{

            this.textInputLayoutFullNames.setError(null);

            return true;
        }
    }

    private boolean validateUserName(){

        if(this.textInputLayoutUsername.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutUsername.setError("User name required!!");
            return  false;

        }
        else{

            this.textInputLayoutUsername.setError(null);

            return true;
        }
    }

    private boolean isEmailValid(String email){

        String email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean validateEmail(){

        if(this.textInputLayoutEmail.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutEmail.setError("Email is required!!");

            return false;
        }
        else if(!isEmailValid(this.textInputLayoutEmail.getEditText().getText().toString())){

            this.textInputLayoutEmail.setError("Enter a valid email address!!");

            return false;
        }
        else{

            this.textInputLayoutEmail.setError(null);

            return true;
        }

    }

    private boolean validatePhoneNo(){

        if(this.textInputLayoutCellNo.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutCellNo.setError("Phone # is required!!");

            return false;
        }
        else if(this.textInputLayoutCellNo.getEditText().getText().length() > 10){

            this.textInputLayoutCellNo.setError("Phone # is long");

            return false;

        }else if(this.textInputLayoutCellNo.getEditText().getText().length() < 10){

            this.textInputLayoutCellNo.setError("Phone # is short!!");

            return false;
        }
        else{

            this.textInputLayoutCellNo.setError(null);

            return true;
        }
    }

    private boolean validatePassword(){

        if(this.textInputLayoutPassword.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutPassword.setError("Password required!!");

            return false;

        }else {

            this.textInputLayoutPassword.setError(null);

            return true;
        }
    }

    private boolean validateHouseNo(){

        if(this.textInputLayoutHouseNo.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutHouseNo.setError("House # is required!!");

            return false;

        }
        else{

            this.textInputLayoutHouseNo.setError(null);
            return true;
        }
    }

    private boolean validateStreetName(){

        if(this.textInputLayoutStreetName.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutStreetName.setError("Street name is required!!");

            return false;

        }
        else{

            this.textInputLayoutStreetName.setError(null);
            return true;
        }
    }

    private boolean validateSurburb(){

        if(this.textInputLayoutSurburb.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutSurburb.setError("Area name is required!!");

            return false;

        }
        else{

            this.textInputLayoutSurburb.setError(null);
            return true;
        }
    }

    private boolean validateCity(){

        if(this.textInputLayoutCity.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutCity.setError("City name is required!!");

            return false;

        }
        else{

            this.textInputLayoutCity.setError(null);
            return true;
        }
    }

    private boolean validatePostalCode(){

        if(this.textInputLayoutPostalCode.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutPostalCode.setError("Postal code is required!!");

            return false;

        }else if(this.textInputLayoutPostalCode.getEditText().getText().length() > 4){

            this.textInputLayoutPostalCode.setError("Postal code is long!!");

            return false;

        }else if(this.textInputLayoutPostalCode.getEditText().getText().length() < 4){

            this.textInputLayoutPostalCode.setError("Postal code is short!!");

            return false;

        }
        else{

            this.textInputLayoutPostalCode.setError(null);
            return true;
        }
    }

    private void updateCustomerProfile() {

        if(!this.validateFullNames() | !this.validateUserName() | !this.validateEmail() | !this.validatePhoneNo() |
                !this.validatePassword() | !this.validateHouseNo() | !this.validateStreetName()
                | !this.validateSurburb() | !this.validateCity() | !this.validatePostalCode()){

            return;
        }else{

            final String fullNames = this.textInputLayoutFullNames.getEditText().getText().toString();
            final String userName = this.textInputLayoutUsername.getEditText().getText().toString();
            final String email = this.textInputLayoutEmail.getEditText().getText().toString();
            final String cellNo = this.textInputLayoutCellNo.getEditText().getText().toString();
            final String password = this.textInputLayoutPassword.getEditText().getText().toString();
            final String houseNo = this.textInputLayoutHouseNo.getEditText().getText().toString();
            final String streetName = this.textInputLayoutStreetName.getEditText().getText().toString();
            final String surburb = this.textInputLayoutSurburb.getEditText().getText().toString();
            final String town = this.textInputLayoutCity.getEditText().getText().toString();
            final String postalCode = this.textInputLayoutPostalCode.getEditText().getText().toString();


            Map<String, String> params = new HashMap<String, String>();
            params.put("userID", String.valueOf(this.userID));
            params.put("fullNames", fullNames);
            params.put("userName", userName);
            params.put("email", email);
            params.put("cellNo", cellNo);
            params.put("password", password);
            params.put("houseNo", houseNo);
            params.put("streetName", streetName);
            params.put("surburb", surburb);
            params.put("town", town);
            params.put("postalCode", postalCode);


            JSONObject jsonObject = new JSONObject(params);

            String url = "http://10.0.2.2:8080/customer/customer-update";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Gson gson = new Gson();
                            Customer returnedCustomer = gson.fromJson(String.valueOf(response), Customer.class);

                            AlertDialog.Builder builder = new AlertDialog.Builder(CustomerUpdateActivity.this)
                                    .setTitle("Serve response")
                                    .setMessage(returnedCustomer.getUserName() + "'s profile updated successfully!!")
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            startActivity(new Intent(CustomerUpdateActivity.this, MainActivity.class));
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();

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

            this.requestQueue.add(jsonObjectRequest);
        }

    }

    private View.OnClickListener updateProfile() {

        View.OnClickListener update = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateCustomerProfile();
            }
        };

        return update;
    }

}
