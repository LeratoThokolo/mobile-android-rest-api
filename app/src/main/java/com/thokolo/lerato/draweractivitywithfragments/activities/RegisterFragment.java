package com.thokolo.lerato.draweractivitywithfragments.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.thokolo.lerato.draweractivitywithfragments.models.RegisterCustomerResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private View view;
    private TextInputLayout textInputLayoutFullNames;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutCellNo;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private TextInputLayout textInputLayoutHouseNo;
    private TextInputLayout textInputLayoutStreetName;
    private TextInputLayout textInputLayoutSurburb;
    private TextInputLayout textInputLayoutCity;
    private TextInputLayout textInputLayoutPostalCode;
    private Button buttonRegister;
    private RequestQueue requestQueue;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity)getActivity()).setActionBarTitle("Register");

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        this.requestQueue = Volley.newRequestQueue(getContext());

        this.initializeViews();
        this.buttonRegister.setOnClickListener(buttonRegisterCustomer());

        return view;
    }

    private void initializeViews() {

        this.textInputLayoutCellNo = view.findViewById(R.id.textInputLayoutCellNo);
        this.textInputLayoutCity = view.findViewById(R.id.textInputLayoutCity);
        this.textInputLayoutConfirmPassword = view.findViewById(R.id.textInputLayoutConfirmPassword);
        this.textInputLayoutEmail = view.findViewById(R.id.textInputLayoutEmail);
        this.textInputLayoutFullNames = view.findViewById(R.id.textInputLayoutFullNames);
        this.textInputLayoutHouseNo = view.findViewById(R.id.textInputLayoutHouseNo);
        this.textInputLayoutPassword = view.findViewById(R.id.textInputLayoutPassword);
        this.textInputLayoutPostalCode = view.findViewById(R.id.textInputLayoutPostalCode);
        this.textInputLayoutStreetName = view.findViewById(R.id.textInputLayoutStreetName);
        this.textInputLayoutSurburb = view.findViewById(R.id.textInputLayoutSurburb);
        this.textInputLayoutUsername = view.findViewById(R.id.textInputLayoutUsername);
        this.buttonRegister = view.findViewById(R.id.buttonRegister);
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

    private boolean validatePasswordConfirm(){

        String password = textInputLayoutPassword.getEditText().getText().toString();
        String confirmPassword = textInputLayoutConfirmPassword.getEditText().getText().toString();

        if(this.textInputLayoutConfirmPassword.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutConfirmPassword.setError("Confirm password required!!");

            return false;
        }
        else if(!password.equals(confirmPassword)){

            this.textInputLayoutConfirmPassword.setError("Passwords do not match!!");

            return false;

        }else{

            this.textInputLayoutConfirmPassword.setError(null);

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

    private void registerCustomer() {


        if(!this.validateFullNames() | !this.validateUserName() | !this.validateEmail() | !this.validatePhoneNo() |
                !this.validatePassword() | !this.validatePasswordConfirm() | !this.validateHouseNo() | !this.validateStreetName()
                | !this.validateSurburb() | !this.validateCity() | !this.validatePostalCode()){

            return;
        }else{

            String url = "http://10.0.2.2:8080/customer/register";

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


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Gson gson = new Gson();
                            RegisterCustomerResponse registerCustomerResponse = gson.fromJson(String.valueOf(response), RegisterCustomerResponse.class);

                            if(registerCustomerResponse.getCustomer().getFullNames() != null){

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                                        .setTitle("Server response")
                                        .setMessage(registerCustomerResponse.getResponseMessage())
                                        .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                fragmentTransaction.replace(R.id.frameLayoutMain, new LoginFragment());
                                                fragmentTransaction.commit();
                                            }
                                        })
                                        .setNegativeButton("Login later", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                startActivity(new Intent(getContext(), MainActivity.class));
                                            }
                                        });

                                AlertDialog dialog = builder.create();
                                dialog.show();



                            }else{

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                                        .setTitle("Server response")
                                        .setMessage(registerCustomerResponse.getResponseMessage())
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

            this.requestQueue.add(jsonObjectRequest);
        }

    }

    private View.OnClickListener buttonRegisterCustomer() {

        View.OnClickListener customerRegister = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerCustomer();
            }
        };

        return customerRegister;
    }

}
