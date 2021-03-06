package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;
import com.thokolo.lerato.draweractivitywithfragments.models.Driver;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminAddDriverActivity extends AppCompatActivity {


    private TextInputLayout textInputLayoutFullNames;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutCellNo;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private Button buttonAddDriver;
    private RequestQueue requestQueueAddDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_driver);

        this.requestQueueAddDriver = Volley.newRequestQueue(this);

        this.initializeViews();
        this.buttonAddDriver = findViewById(R.id.buttonAddDriver);
        this.buttonAddDriver.setOnClickListener(this.buttonRegisterDriver());


    }

    private void initializeViews() {

        this.textInputLayoutCellNo = findViewById(R.id.textInputLayoutCellNo);
        this.textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword);
        this.textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        this.textInputLayoutFullNames = findViewById(R.id.textInputLayoutFullNames);
        this.textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        this.textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername);

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

    private void registerDriver() {

        if(!this.validateFullNames() | !this.validateUserName() | !this.validateEmail() | !this.validatePhoneNo() |
                !this.validatePassword() | !this.validatePasswordConfirm()){

            return;
        }else{

            String url = "http://10.0.2.2:8080/driver/add-driver";

            final String fullNames = this.textInputLayoutFullNames.getEditText().getText().toString();
            final String userName = this.textInputLayoutUsername.getEditText().getText().toString();
            final String email = this.textInputLayoutEmail.getEditText().getText().toString();
            final String cellNo = this.textInputLayoutCellNo.getEditText().getText().toString();
            final String password = this.textInputLayoutPassword.getEditText().getText().toString();

            Map<String, String> params = new HashMap<String, String>();
            params.put("fullNames", fullNames);
            params.put("userName", userName);
            params.put("email", email);
            params.put("cellNo", cellNo);
            params.put("password", password);

            JSONObject jsonObject = new JSONObject(params);
            JsonObjectRequest jsonObjectRequestAddDriver = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Gson gson = new Gson();
                            Driver driver = gson.fromJson(String.valueOf(response), Driver.class);
                            CustomToast.createToast(AdminAddDriverActivity.this, driver.getFullNames() + " added successfully");
                            startActivity(new Intent(AdminAddDriverActivity.this, AdminDriverActivity.class));
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

            this.requestQueueAddDriver.add(jsonObjectRequestAddDriver);
        }
    }

    private View.OnClickListener buttonRegisterDriver() {

        View.OnClickListener driverRegister = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerDriver();
            }
        };

        return driverRegister;
    }

}
