package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
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
import com.thokolo.lerato.draweractivitywithfragments.models.Product;
import com.thokolo.lerato.draweractivitywithfragments.models.Supplier;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SupplierUpdateActivity extends AppCompatActivity {

    private RequestQueue requestQueueUpdateProduct;
    private TextInputLayout textInputLayoutBatchQuantity;
    private Button buttonUpdateProduct;
    private Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_update);


        this.requestQueueUpdateProduct = Volley.newRequestQueue(this);
        this.initializeGadgets();
        this.getProduct();

        this.buttonUpdateProduct.setOnClickListener(updateProductQuantity());

    }

    private void initializeGadgets() {

        this.textInputLayoutBatchQuantity = findViewById(R.id.textInputBatchQuantity);
        this.buttonUpdateProduct = findViewById(R.id.buttonUpdateProduct);
    }

    private boolean validateBatchQuantity(){

        if(this.textInputLayoutBatchQuantity.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutBatchQuantity.setError("Batch quantity is required!!");

            return false;

        }else{

            this.textInputLayoutBatchQuantity.setError(null);

            return true;
        }

    }

    private void getProduct(){

        Gson gson = new Gson();
        Intent intent = getIntent();

        this.product = gson.fromJson(intent.getStringExtra("product"), Product.class);
        this.textInputLayoutBatchQuantity.getEditText().setText(String.valueOf(this.product.getBadgeQuantity()));

        if(this.product.getName().length() < 10){

            getSupportActionBar().setTitle("Update " + this.product.getName() + " batch quantity");

        }else if(this.product.getName().length() > 10){

            String name = this.product.getName().substring(0, 10);

            getSupportActionBar().setTitle("Update " + name + "..." + " batch quantity");
        }
    }

    private View.OnClickListener updateProductQuantity() {

        View.OnClickListener update = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProduct();

            }
        };

        return update;
    }

    private void updateProduct() {

        if(!validateBatchQuantity()){

            return;

        }else{

            String url = "http://10.0.2.2:8080/product/update-product";
            int badgeQuantity = Integer.parseInt(this.textInputLayoutBatchQuantity.getEditText().getText().toString());

            final Gson gson = new Gson();
            int updatedQuantity = this.product.getQuantity() + badgeQuantity;


            Map<String, String> params = new HashMap<String, String>();
            params.put("productID", String.valueOf(this.product.getProductID()));
            params.put("productName", this.product.getName());
            params.put("badgeQuantity", String.valueOf(badgeQuantity));
            params.put("name", this.product.getName());
            params.put("purchased", String.valueOf(true));
            params.put("quantity", String.valueOf(updatedQuantity));
            params.put("unitPrice", String.valueOf(this.product.getUnitPrice()));
            params.put("image", this.product.getImage());
            params.put("minimumQuantity", String.valueOf(this.product.getMinimumQuantity()));
            params.put("imageResource", String.valueOf(this.product.getImageResource()));

            Category c = new Category();
            c.setCategoryID(this.product.getCategory().getCategoryID());
            c.setName(this.product.getCategory().getName());
            params.put("categoryID", String.valueOf(c.getCategoryID()));
            this.product.setCategory(c);
            params.put("category", gson.toJson(this.product.getCategory()));

            Supplier s = new Supplier();
            s.setUserID(this.product.getSupplier().getUserID());
            s.setCellNo(this.product.getSupplier().getCellNo());
            s.setEmail(this.product.getSupplier().getEmail());
            s.setFullNames(this.product.getSupplier().getFullNames());
            s.setPassword(this.product.getSupplier().getPassword());
            s.setUserName(this.product.getSupplier().getUserName());
            s.setRoles(this.product.getSupplier().getRoles());
            params.put("supplierID", String.valueOf(s.getUserID()));
            this.product.setSupplier(s);
            params.put("supplier", gson.toJson(this.product.getSupplier()));


            JSONObject jsonObject = new JSONObject(params);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Product p = gson.fromJson(String.valueOf(response), Product.class);

                            AlertDialog.Builder builder = new AlertDialog.Builder(SupplierUpdateActivity.this)
                                    .setTitle("Server response")
                                    .setMessage(p.getName() + " has been updated!!")
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            startActivity(new Intent(SupplierUpdateActivity.this, SupplierActivity.class));
                                        }
                                    });

                            AlertDialog dialog = builder.create();
                            dialog.show();
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

            this.requestQueueUpdateProduct.add(jsonObjectRequest);
        }


    }


}
