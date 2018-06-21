package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;
import com.thokolo.lerato.draweractivitywithfragments.models.LoginResponse;
import com.thokolo.lerato.draweractivitywithfragments.models.User;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecoverPasswordActivity extends AppCompatActivity {
    private TextInputLayout textInputLayoutRecoverPasswordEmail;
    private Button buttonRecoverPassword;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        getSupportActionBar().setTitle("Recover password");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4682b4")));

        this.requestQueue = Volley.newRequestQueue(this);
        this.intializeGadgets();
        this.buttonRecoverPassword.setOnClickListener(recoverPassword());

    }

    private View.OnClickListener recoverPassword() {

        View.OnClickListener recover = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getForgottenPassword();
            }
        };

        return recover;
    }


    private void intializeGadgets() {

        this.textInputLayoutRecoverPasswordEmail = findViewById(R.id.textInputRecoverPassword);
        this.buttonRecoverPassword =findViewById(R.id.buttonRecoverPassword);
    }

    private boolean isEmailValid(String email){

        String email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }


    private boolean validateEmail(){

        if(this.textInputLayoutRecoverPasswordEmail.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutRecoverPasswordEmail.setError("Email is required!!");

            return false;
        }
        else if(!isEmailValid(this.textInputLayoutRecoverPasswordEmail.getEditText().getText().toString())){

            this.textInputLayoutRecoverPasswordEmail.setError("Enter a valid email address!!");

            return false;
        }
        else{

            this.textInputLayoutRecoverPasswordEmail.setError(null);

            return true;
        }

    }

    private void getForgottenPassword() {

        if(!validateEmail()){

            return;

        }else{

            String url = "http://10.0.2.2:8080/user/recover-user-password/";
            String userEmail = textInputLayoutRecoverPasswordEmail.getEditText().getText().toString();

            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url + userEmail,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Gson gson = new Gson();
                            LoginResponse loginResponse = gson.fromJson(String.valueOf(response), LoginResponse.class);

                            if(loginResponse.getUser().getUserName() != null){

                               AlertDialog.Builder builder = new AlertDialog.Builder(RecoverPasswordActivity.this)
                                       .setTitle(loginResponse.getMessageResponse())
                                       .setMessage("Your username:" + loginResponse.getUser().getUserName()
                                               + "\n" + "Your password:" + loginResponse.getUser().getPassword())
                                       .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               CustomToast.createToast(RecoverPasswordActivity.this, "Navigate to login");
                                               startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                            else if(loginResponse.getUser().getUserName() == null){

                                AlertDialog.Builder builder = new AlertDialog.Builder(RecoverPasswordActivity.this)
                                        .setTitle(loginResponse.getMessageResponse())
                                        .setMessage("Don't have an account?" + "\n" + "Navigate to register")
                                        .setPositiveButton("Navigate", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                    }
                    ,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            error.printStackTrace();
                        }
                    }
            );

            this.requestQueue.add(objectRequest);

        }

    }

}
