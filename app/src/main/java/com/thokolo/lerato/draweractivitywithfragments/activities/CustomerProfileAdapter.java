package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomErrorToast;
import com.thokolo.lerato.draweractivitywithfragments.models.Customer;
import com.thokolo.lerato.draweractivitywithfragments.models.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerProfileAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Customer> customers;
    private TextView textViewCustomerName;
    private ImageView imageViewUpdate;
    private ImageView imageViewDelete;
    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private User user = null;


    public CustomerProfileAdapter(Context context, ArrayList<Customer> customers) {
        this.context = context;
        this.customers = customers;
    }

    @Override
    public int getCount() {

        return customers.size();
    }

    @Override
    public Customer getItem(int position) {

        return customers.get(position);
    }

    @Override
    public long getItemId(int position) {

        return customers.get(position).getUserID();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        convertView = convertView.inflate(this.context, R.layout.custom_user_profile, null);
        this.textViewCustomerName = convertView.findViewById(R.id.textViewUserProfileName);
        this.imageViewUpdate = convertView.findViewById(R.id.update_profile_img);
        this.imageViewDelete = convertView.findViewById(R.id.delete_profile_img);

        this.requestQueue = Volley.newRequestQueue(context);
        sharedPreferences = context.getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);

        this.textViewCustomerName.setText(customers.get(position).getUserName());
        this.imageViewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                v.getContext().startActivity(new Intent(v.getContext(), CustomerUpdateActivity.class));
            }
        });

        this.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete account")
                        .setMessage("Are you sure you want to delete your account?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                int userID = customers.get(position).getUserID();
                                String url = "http://10.0.2.2:8080/customer/customer-delete/" + userID;

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                        Request.Method.DELETE,
                                        url,
                                        null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.remove("user-logged-in");
                                                editor.apply();

                                                CustomErrorToast.createErrorToast(v.getContext(), "Account deleted successfully");
                                                v.getContext().startActivity(new Intent(v.getContext(), MainActivity.class ));
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                error.printStackTrace();
                                            }
                                        }
                                );

                                requestQueue.add(jsonObjectRequest);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        return  convertView;
    }

}
