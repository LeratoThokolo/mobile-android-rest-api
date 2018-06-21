package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.ApplicationHeaders;
import com.thokolo.lerato.draweractivitywithfragments.models.Category;
import com.thokolo.lerato.draweractivitywithfragments.models.Product;
import com.thokolo.lerato.draweractivitywithfragments.models.Supplier;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminUpdateProductActivity extends AppCompatActivity {

    private Spinner spinnerSuppliers;
    private Spinner spinnerCategories;
    private Button buttonUpdateProduct;
    private TextInputLayout textInputLayoutPName;
    private TextInputLayout textInputLayoutQuantity;
    private TextInputLayout textInputLayoutMinimumQuantity;
    private TextInputLayout textInputLayoutBatchQuantity;
    private TextInputLayout textInputLayoutPrice;
    private RequestQueue requestQueueUpdate;
    private Product product = new Product();
    private ArrayList<Supplier> suppliers = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private SuppliersCustomAdapter suppliersCustomAdapter;
    private Category category = new Category();
    private Supplier supplier = new Supplier();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_product);

        getSupportActionBar().setTitle("Update product");

        this.initializeGadgets();

    }

    private void initializeGadgets() {

        Intent intent = getIntent();
        Gson gson = new Gson();

        this.product = gson.fromJson(intent.getStringExtra("productToUpdate"), Product.class);

        this.requestQueueUpdate = Volley.newRequestQueue(this);


        this.textInputLayoutBatchQuantity = findViewById(R.id.textInputLayoutProductBatchQuantity);
        this.textInputLayoutBatchQuantity.getEditText().setText(String.valueOf(this.product.getBadgeQuantity()));


        this.textInputLayoutMinimumQuantity = findViewById(R.id.textInputLayoutProductMinimumQuantity);
        this.textInputLayoutMinimumQuantity.getEditText().setText(String.valueOf(this.product.getMinimumQuantity()));


        this.textInputLayoutPName = findViewById(R.id.textInputLayoutProductName);
        this.textInputLayoutPName.getEditText().setText(this.product.getName());


        this.textInputLayoutPrice = findViewById(R.id.textInputLayoutProductUnitPrice);
        this.textInputLayoutPrice.getEditText().setText(String.valueOf(this.product.getUnitPrice()));

        this.textInputLayoutQuantity = findViewById(R.id.textInputLayoutProductQuantity);
        this.textInputLayoutQuantity.getEditText().setText(String.valueOf(this.product.getQuantity()));


        this.suppliers.add(this.product.getSupplier());
        this.categories.add(this.product.getCategory());

        this.categoryAdapter = new CategoryAdapter(this.categories, this);
        this.suppliersCustomAdapter = new SuppliersCustomAdapter(this.suppliers, this);





        this.spinnerCategories = findViewById(R.id.spinnerCategories);
        this.spinnerCategories.setAdapter(this.categoryAdapter);
        this.spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category = categoryAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        this.spinnerSuppliers = findViewById(R.id.spinnerSuppliers);
        this.spinnerSuppliers.setAdapter(this.suppliersCustomAdapter);
        this.spinnerSuppliers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                supplier = suppliersCustomAdapter.getItem(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        this.buttonUpdateProduct = findViewById(R.id.buttonUpdateProduct);
        this.buttonUpdateProduct.setOnClickListener(updateProduct());

    }

    private boolean validateProductName(){

        if(this.textInputLayoutPName.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutPName.setError("Product name is required!!");

            return false;

        }else{

            this.textInputLayoutPName.setError(null);

            return true;
        }
    }

    private boolean validateQuantity(){

        if(this.textInputLayoutQuantity.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutQuantity.setError("Quantity is required!!");

            return false;

        }else{

            this.textInputLayoutQuantity.setError(null);

            return true;
        }

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

    private boolean validatePrice(){

        if(this.textInputLayoutPrice.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutPrice.setError("Price is required!!");

            return false;

        }else{

            this.textInputLayoutPrice.setError(null);

            return true;
        }

    }

    private boolean validateMinimumQuantity(){

        if(this.textInputLayoutMinimumQuantity.getEditText().getText().toString().trim().isEmpty()){

            this.textInputLayoutMinimumQuantity.setError("Minimum quantity is required!!");

            return false;

        }else{

            this.textInputLayoutMinimumQuantity.setError(null);

            return true;
        }

    }

    private View.OnClickListener updateProduct() {

        View.OnClickListener update = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProductLogic();
            }
        };

        return update;
    }

    private void updateProductLogic() {

        if(!this.validateBatchQuantity() | !this.validateMinimumQuantity() | !this.validatePrice() | !this.validateProductName() |
                !this.validateQuantity()){
            return;
        }else{

            String url = "http://10.0.2.2:8080/product/update-product";
            String name = this.textInputLayoutPName.getEditText().getText().toString();
            int quantity = Integer.parseInt( this.textInputLayoutQuantity.getEditText().getText().toString());
            int minimumQuantity = Integer.parseInt( this.textInputLayoutMinimumQuantity.getEditText().getText().toString());
            int badgeQuantity = Integer.parseInt( this.textInputLayoutBatchQuantity.getEditText().getText().toString());
            double unitPrice = Double.parseDouble(this.textInputLayoutPrice.getEditText().getText().toString());
            int supplierID = this.supplier.getUserID();
            int categoryID = this.category.getCategoryID();
            String image = this.product.getImage();
            Gson gson = new Gson();

            Map<String, String> params = new HashMap<String, String >();
            params.put("productID", String.valueOf(this.product.getProductID()));
            params.put("name", name);
            params.put("purchased", String.valueOf(true));
            params.put("quantity", String.valueOf(quantity));
            params.put("minimumQuantity", String.valueOf(minimumQuantity));
            params.put("badgeQuantity", String.valueOf(badgeQuantity));
            params.put("unitPrice", String.valueOf(unitPrice));
            params.put("image", image);

            params.put("supplierID", String.valueOf(supplierID));
            params.put("supplier", gson.toJson(this.supplier));

            params.put("categoryID", String.valueOf(categoryID));
            params.put("category", gson.toJson(this.category));

            JSONObject jsonObjectUpdate = new JSONObject(params);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    url,
                    jsonObjectUpdate,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(AdminUpdateProductActivity.this)
                                    .setTitle("Server response")
                                    .setMessage(product.getName() + " updated successfully!!")
                                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            startActivity(new Intent(AdminUpdateProductActivity.this, AdminProductsActivity.class));
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

            this.requestQueueUpdate.add(jsonObjectRequest);

        }
    }
}
