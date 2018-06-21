package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.gson.Gson;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;
import com.thokolo.lerato.draweractivitywithfragments.models.Product;

import org.json.JSONObject;

import java.util.ArrayList;

public class AdminProductsAdapterCustom extends BaseAdapter {

    private ArrayList<Product> products;
    private Context context;
    private TextView textViewPname;
    private ImageView imageViewUpdate;
    private ImageView imageViewDelete;
    private int productID;
    private RequestQueue requestQueueDelete;

    public AdminProductsAdapterCustom(ArrayList<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.products.size();
    }

    @Override
    public Product getItem(int position) {
        return this.products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.products.get(position).getProductID();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = convertView.inflate(this.context, R.layout.admin_products_custom, null);

        this.imageViewDelete = convertView.findViewById(R.id.adminDelete);
        this.imageViewUpdate = convertView.findViewById(R.id.adminEdit);
        this.textViewPname = convertView.findViewById(R.id.textViewAdminP);
        this.requestQueueDelete = Volley.newRequestQueue(context);

        this.productID = (int) this.getItemId(position);
        if(this.getItem(position).getName().length() < 10){

            this.textViewPname.setText(this.getItem(position).getName());
        }else{

            String name = getItem(position).getName().substring(0, 9);
            this.textViewPname.setText(name + "...");
        }

        this.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Response")
                        .setMessage("Äre you sure to delete " + getItem(position).getName() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String url = "http://10.0.2.2:8080/product/delete-Product/" + productID;

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                        Request.Method.DELETE,
                                        url,
                                        null,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject response) {


                                                CustomToast.createToast(context, getItem(position).getName() + " deleted successfully");


                                            }
                                        },
                                        new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                error.printStackTrace();
                                            }
                                        }
                                );

                                requestQueueDelete.add(jsonObjectRequest);
                                notifyDataSetChanged();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        this.imageViewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(context, AdminUpdateProductActivity.class);
                Gson gson = new Gson();
                intent.putExtra("productToUpdate", gson.toJson(getItem(position)));
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}
