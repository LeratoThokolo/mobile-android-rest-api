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
import com.thokolo.lerato.draweractivitywithfragments.models.Category;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminCategoryCustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Category> categories;
    private TextView textViewCategoryName;
    private ImageView imageViewUpdate;
    private ImageView imageViewDelete;
    private RequestQueue requestQueueDelete;

    public AdminCategoryCustomAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return this.categories.size();
    }

    @Override
    public Category getItem(int position) {
        return this.categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.categories.get(position).getCategoryID();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = convertView.inflate(this.context, R.layout.admin_list_categories, null);

        this.textViewCategoryName = convertView.findViewById(R.id.textViewCategoryName);
        this.textViewCategoryName.setText(this.getItem(position).getName());
        this.imageViewUpdate = convertView.findViewById(R.id.imageViewUpdate);
        this.imageViewDelete = convertView.findViewById(R.id.imageViewDelete);
        this.requestQueueDelete = Volley.newRequestQueue(context);

        this.imageViewUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AdminUpdateCategoryActivity.class);

                Gson gson = new Gson();
                intent.putExtra("category", gson.toJson(getItem(position)));
                context.startActivity(intent);
            }
        });

        this.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure to delete " + getItem(position).getName() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String url = "http://10.0.2.2:8080/category/delete-category/" + getItem(position).getCategoryID();

                                JsonObjectRequest jsonObjectRequestDeleteCategory = new JsonObjectRequest(
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

                                requestQueueDelete.add(jsonObjectRequestDeleteCategory);

                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        return convertView;
    }
}
