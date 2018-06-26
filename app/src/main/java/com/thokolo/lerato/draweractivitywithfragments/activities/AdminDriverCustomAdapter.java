package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;
import com.thokolo.lerato.draweractivitywithfragments.models.Driver;

import org.json.JSONObject;

import java.util.ArrayList;

public class AdminDriverCustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Driver> drivers;
    private TextView textViewDriverName;
    private ImageView imageViewDelete;
    private RequestQueue requestQueueDelete;

    public AdminDriverCustomAdapter(Context context, ArrayList<Driver> drivers) {
        this.context = context;
        this.drivers = drivers;
    }

    @Override
    public int getCount() {
        return this.drivers.size();
    }

    @Override
    public Driver getItem(int position) {
        return this.drivers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.drivers.get(position).getUserID();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = convertView.inflate(this.context, R.layout.admin_driver_custom, null);

        this.textViewDriverName = convertView.findViewById(R.id.textViewDriverName);
        this.textViewDriverName.setText(this.getItem(position).getFullNames());
        this.imageViewDelete = convertView.findViewById(R.id.imageViewDelete);
        this.requestQueueDelete = Volley.newRequestQueue(context);

        this.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete " + getItem(position).getFullNames() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String url = "http://10.0.2.2:8080/driver/remove-driver/" + getItem(position).getUserID();

                                JsonObjectRequest jsonObjectRequestDeleteDriver = new JsonObjectRequest(
                                        Request.Method.DELETE,
                                        url,
                                        null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {

                                                CustomToast.createToast(context, getItem(position).getFullNames() + " deleted successfully");
                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                error.printStackTrace();
                                            }
                                        }
                                );

                                requestQueueDelete.add(jsonObjectRequestDeleteDriver);

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        return convertView;
    }
}
