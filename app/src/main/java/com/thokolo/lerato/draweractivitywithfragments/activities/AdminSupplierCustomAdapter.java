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
import com.thokolo.lerato.draweractivitywithfragments.models.Supplier;

import org.json.JSONObject;

import java.util.ArrayList;

public class AdminSupplierCustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Supplier> suppliers;
    private TextView textViewSupplierFullNames;
    private ImageView imageViewDelete;
    private RequestQueue requestQueueDelete;

    public AdminSupplierCustomAdapter(Context context, ArrayList<Supplier> suppliers) {
        this.context = context;
        this.suppliers = suppliers;
    }

    @Override
    public int getCount() {
        return this.suppliers.size();
    }

    @Override
    public Supplier getItem(int position) {
        return this.suppliers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.suppliers.get(position).getUserID();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = convertView.inflate(this.context, R.layout.admin_supplier_custom, null);

        this.imageViewDelete = convertView.findViewById(R.id.imageViewDelete);
        this.textViewSupplierFullNames = convertView.findViewById(R.id.textViewSupplierName);
        this.textViewSupplierFullNames.setText(this.getItem(position).getFullNames());
        this.requestQueueDelete = Volley.newRequestQueue(this.context);

        this.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {

                final String url = "http://10.0.2.2:8080/supplier/remove-supplier/" + getItem(position).getUserID();

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Confirm delete")
                        .setMessage("Are you sure to delete " + getItem(position).getFullNames() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                JsonObjectRequest jsonObjectRequestDelete = new JsonObjectRequest(
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

                                requestQueueDelete.add(jsonObjectRequestDelete);
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

        return convertView;
    }
}
