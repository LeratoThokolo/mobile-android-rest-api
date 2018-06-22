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
import com.thokolo.lerato.draweractivitywithfragments.models.Category;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminUpdateCategoryActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutCategoryName;
    private Button buttonUpdateCategory;
    private Category category = new Category();
    private RequestQueue requestQueueUpdateCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_category);
        getSupportActionBar().setTitle("Admin update category");

        Gson gson = new Gson();
        category = gson.fromJson(getIntent().getStringExtra("category"), Category.class);
        this.requestQueueUpdateCategory = Volley.newRequestQueue(this);

        this.initialiseGadgets();
    }

    private void initialiseGadgets() {

        this.textInputLayoutCategoryName = findViewById(R.id.textInputLayoutCategoryName);
        this.textInputLayoutCategoryName.getEditText().setText(this.category.getName());
        this.buttonUpdateCategory = findViewById(R.id.buttonUpdateCategory);
        this.buttonUpdateCategory.setOnClickListener(this.updateCategory());
    }

    public boolean validateCategoryName(){

        if(this.textInputLayoutCategoryName.getEditText().getText().toString().trim().isEmpty()){


            this.textInputLayoutCategoryName.setError("Category name is required!!");
            return false;

        }else{

            this.textInputLayoutCategoryName.setError(null);
            return true;
        }
    }

    private void updateCategoryLogic() {

        if(!this.validateCategoryName()){

            return;
        }else {

            String url = "http://10.0.2.2:8080/category/update";
            int categoryID = this.category.getCategoryID();
            String name = this.textInputLayoutCategoryName.getEditText().getText().toString();



            Map<String, String> params = new HashMap<String, String>();
            params.put("categoryID", String.valueOf(categoryID));
            params.put("name", name);

            JSONObject jsonObject = new JSONObject(params);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            CustomToast.createToast(AdminUpdateCategoryActivity.this, category.getName() + " updated successfully");

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

            this.requestQueueUpdateCategory.add(jsonObjectRequest);
        }
    }

    private View.OnClickListener updateCategory() {

        View.OnClickListener update = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateCategoryLogic();
            }
        };

        return update;
    }

}
