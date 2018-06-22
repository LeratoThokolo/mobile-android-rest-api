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

public class AdminAddCategoriesActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutCategoryName;
    private Button buttonAddCategory;
    private RequestQueue requestQueueAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_categories);
        getSupportActionBar().setTitle("Add Category");

        this.requestQueueAddCategory = Volley.newRequestQueue(this);

        this.initialiseGadgets();
    }

    private void initialiseGadgets() {

        this.textInputLayoutCategoryName = findViewById(R.id.textInputLayoutCategoryName);

        this.buttonAddCategory = findViewById(R.id.buttonAddCategory);
        this.buttonAddCategory.setOnClickListener(this.addCategory());
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

    private void addCategoryLogic() {

        if(!this.validateCategoryName()){

            return;
        }else {

            String url = "http://10.0.2.2:8080/category/add-category";
            String name = this.textInputLayoutCategoryName.getEditText().getText().toString();



            Map<String, String> params = new HashMap<String, String>();
            params.put("name", name);

            JSONObject jsonObject = new JSONObject(params);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Gson gson = new Gson();

                            Category category = gson.fromJson(String.valueOf(response), Category.class);

                            CustomToast.createToast(AdminAddCategoriesActivity.this, category.getName() + " added successfully");
                            startActivity(new Intent(AdminAddCategoriesActivity.this, MainActivity.class));

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

            this.requestQueueAddCategory.add(jsonObjectRequest);
        }

    }


    private View.OnClickListener addCategory() {

        View.OnClickListener add = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addCategoryLogic();
            }
        };

        return add;
    }

}
