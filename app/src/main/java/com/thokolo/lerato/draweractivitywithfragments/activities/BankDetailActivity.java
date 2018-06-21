package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

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
import com.thokolo.lerato.draweractivitywithfragments.models.Bank;
import com.thokolo.lerato.draweractivitywithfragments.models.BankDetail;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomErrorToast;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;
import com.thokolo.lerato.draweractivitywithfragments.models.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BankDetailActivity extends AppCompatActivity {

    private Spinner spinnerBanks;
    private TextInputLayout textInputLayoutAccountNo;
    private TextInputLayout textInputLayoutPin;
    private TextInputLayout textInputLayoutCustomerName;
    private Button buttonSaveBankingDetails;
    private ArrayList<Bank> banks = new ArrayList<>();
    private RequestQueue requestQueue;
    private Bank bank = null;
    private SharedPreferences loginSharedPreferences;
    private User user = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_detail);
        getSupportActionBar().setTitle("Banking Details");

        this.requestQueue = Volley.newRequestQueue(this);


        this.getBanks();
        //Getting logged in user from sharedpreferences
        Gson gson = new Gson();
        loginSharedPreferences = getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
        this.user = new User();
        this.user = gson.fromJson(loginSharedPreferences.getString("user-logged-in", "No logged in user"), User.class);

        this.initializeViews();
        this.buttonSaveBankingDetails.setOnClickListener(saveBankingDetails());
    }

    private void initializeViews(){

        this.spinnerBanks = findViewById(R.id.spinnerBanks);
        this.textInputLayoutAccountNo = findViewById(R.id.textInputAccountNo);
        this.textInputLayoutPin = findViewById(R.id.textInputPin);
        this.textInputLayoutCustomerName = findViewById(R.id.textInputCustomerName);
        this.textInputLayoutCustomerName.getEditText().setText(this.user.getFullNames());
        this.buttonSaveBankingDetails = findViewById(R.id.buttonSaveBankDetails);

    }

    private boolean validateAccountNo(){

        if(textInputLayoutAccountNo.getEditText().getText().toString().trim().isEmpty()){

            textInputLayoutAccountNo.setError("Account # is required!!");

            return false;

        }
        else if(textInputLayoutAccountNo.getEditText().getText().length() > 10){

            textInputLayoutAccountNo.setError("Account # too long!!");

            return false;
        }
        else{

            textInputLayoutAccountNo.setError(null);
            return true;
        }

    }

    private boolean validatePin(){

        if(textInputLayoutPin.getEditText().getText().toString().trim().isEmpty()){

            textInputLayoutPin.setError("Pin is required!!");

            return false;

        }
        else if(textInputLayoutPin.getEditText().getText().length() > 5){

            textInputLayoutPin.setError("Pin too long!!");

            return false;
        }
        else{

            textInputLayoutPin.setError(null);

            return true;
        }

    }

    private boolean validateCustomerName(){

        if(textInputLayoutCustomerName.getEditText().getText().toString().trim().isEmpty()){

            textInputLayoutCustomerName.setError("Customer name is required!!");

            return false;
        }else{

            textInputLayoutCustomerName.setError(null);

            return true;
        }
    }

    private boolean validateSpinner(){

        if(this.spinnerBanks.getSelectedItem().equals(banks.get(0))){

            CustomErrorToast.createErrorToast(this, "Please select a bank");

            return false;
        }
        else{

            return true;
        }
    }



    private void getBanks(){

        String baseUrl = "http://10.0.2.2:8080/bank";

        JsonArrayRequest arrayRequest = new JsonArrayRequest(

                Request.Method.GET,
                baseUrl + "/banks",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();

                        Type type = new TypeToken<ArrayList<Bank>>(){}.getType();

                        banks = gson.fromJson(String.valueOf(response), type);
                        banks.add(0, new Bank(0, "Select bank", "Null"));

                        final BankCustomAdapter adapter = new BankCustomAdapter(banks, getApplicationContext());
                        spinnerBanks.setAdapter(adapter);
                        spinnerBanks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                bank = new Bank();
                                bank = adapter.getItem(position);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {


                            }
                        });

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

    private void addBankingDetails(){

        if(!validateSpinner() | !validateAccountNo() | !validatePin() | !validateCustomerName()){

            return;
        }
        else{

            final Gson gson = new Gson();

            String baseUrl = "http://10.0.2.2:8080/bank-account";

            int pin = Integer.parseInt(textInputLayoutPin.getEditText().getText().toString());
            int accountNumber = Integer.parseInt(textInputLayoutAccountNo.getEditText().getText().toString());

            Map<String, String> params = new HashMap<String, String>();
            params.put("bank", gson.toJson(bank));
            params.put("accountNumber", gson.toJson(accountNumber));
            params.put("pin", gson.toJson(pin));
            params.put("userID", gson.toJson(user.getUserID()));

            JSONObject bankDetailObject = new JSONObject(params);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(

                    Request.Method.POST,
                    baseUrl + "/create-account",
                    bankDetailObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.e("Saved details", response.toString());
                            startActivity(new Intent(BankDetailActivity.this, ReceiptActivity.class));

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
                    headers.put("Content-Type", "application/json");

                    return headers;
                }
            };

            requestQueue.add(jsonObjectRequest);

        }

    }

    private View.OnClickListener saveBankingDetails() {

        View.OnClickListener saveDetails = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addBankingDetails();
            }
        };

        return saveDetails;
    }

}
