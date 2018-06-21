package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.Category;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomErrorToast;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;
import com.thokolo.lerato.draweractivitywithfragments.models.Product;
import com.thokolo.lerato.draweractivitywithfragments.models.Supplier;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminAddProductActivity extends AppCompatActivity {

    private Spinner spinnerSuppliers;
    private Spinner spinnerCategories;
    private Button buttonAddProduct;
    private TextInputLayout textInputLayoutPName;
    private TextInputLayout textInputLayoutQuantity;
    private TextInputLayout textInputLayoutMinimumQuantity;
    private TextInputLayout textInputLayoutBatchQuantity;
    private TextInputLayout textInputLayoutPrice;
    private RequestQueue requestQueueAdd;
    private RequestQueue requestQueueSuppliers;
    private RequestQueue requestQueueCategories;
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
        setContentView(R.layout.activity_admin_add_product);

        getSupportActionBar().setTitle("Add product");

        this.initializeGadgets();
        this.getCategories();
        this.getSuppliers();
    }

    private void initializeGadgets() {


        this.requestQueueAdd = Volley.newRequestQueue(this);
        this.requestQueueSuppliers = Volley.newRequestQueue(this);
        this.requestQueueCategories = Volley.newRequestQueue(this);


        this.textInputLayoutBatchQuantity = findViewById(R.id.textInputLayoutProductBatchQuantity);



        this.textInputLayoutMinimumQuantity = findViewById(R.id.textInputLayoutProductMinimumQuantity);



        this.textInputLayoutPName = findViewById(R.id.textInputLayoutProductName);



        this.textInputLayoutPrice = findViewById(R.id.textInputLayoutProductUnitPrice);


        this.textInputLayoutQuantity = findViewById(R.id.textInputLayoutProductQuantity);


        this.spinnerCategories = findViewById(R.id.spinnerCategories);
        this.spinnerSuppliers = findViewById(R.id.spinnerSuppliers);



        this.buttonAddProduct = findViewById(R.id.buttonAddProduct);
        this.buttonAddProduct.setOnClickListener(AddProduct());
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

    private boolean validateSpinnerCategory(){

        if(this.spinnerCategories.getSelectedItem().equals(this.categories.get(0))){

            CustomErrorToast.createErrorToast(AdminAddProductActivity.this, "Please select a category!!");
            return false;

        }else{

            return true;
        }
    }

    private boolean validateSpinnerSupplier(){

        if(this.spinnerSuppliers.getSelectedItem().equals(this.suppliers.get(0))){

            CustomErrorToast.createErrorToast(AdminAddProductActivity.this, "Please select a supplier!!");
            return false;

        }else{

            return true;
        }
    }

    private void getCategories(){

        String url = "http://10.0.2.2:8080/category/all-categories";

        JsonArrayRequest jsonArrayRequestCategories = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();
                        Type type = new TypeToken<ArrayList<Category>>(){}.getType();

                        categories = gson.fromJson(String.valueOf(response), type);

                        categories.add(0, new Category(0, "Select a category"));

                        categoryAdapter = new CategoryAdapter(categories, AdminAddProductActivity.this);
                        spinnerCategories.setAdapter(categoryAdapter);
                        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                category = categoryAdapter.getItem(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }
        );

        this.requestQueueCategories.add(jsonArrayRequestCategories);

    }

    private void getSuppliers(){

        String url = "http://10.0.2.2:8080/supplier/all-suppliers";

        JsonArrayRequest jsonArrayRequestSuppliers = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Gson gson = new Gson();

                        Type type = new TypeToken<ArrayList<Supplier>>(){}.getType();

                        suppliers = gson.fromJson(String.valueOf(response), type);
                        suppliers.add(0, new Supplier(0, "","","Select a supplier","","",null));

                        suppliersCustomAdapter = new SuppliersCustomAdapter(suppliers, AdminAddProductActivity.this);
                        spinnerSuppliers.setAdapter(suppliersCustomAdapter);
                        spinnerSuppliers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                supplier = suppliersCustomAdapter.getItem(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }
        );

        this.requestQueueSuppliers.add(jsonArrayRequestSuppliers);
    }

    private void addProductLogic() {

        if(!this.validateBatchQuantity() | !this.validateMinimumQuantity() | !this.validatePrice() | !this.validateProductName() |
                !this.validateQuantity() | !this.validateSpinnerCategory() | !this.validateSpinnerSupplier()) {
            return;
        }else{

            final String name = this.textInputLayoutPName.getEditText().getText().toString();
            int quantity = Integer.parseInt(this.textInputLayoutQuantity.getEditText().getText().toString());
            int badgeQuantity =  Integer.parseInt(this.textInputLayoutBatchQuantity.getEditText().getText().toString());
            int minimumQuantity =  Integer.parseInt(this.textInputLayoutMinimumQuantity.getEditText().getText().toString());
            double unitPrice = Double.parseDouble(this.textInputLayoutPrice.getEditText().getText().toString());


            Gson gson = new Gson();
            String url = "http://10.0.2.2:8080/product/add-product";

            Map<String, String> params = new HashMap<String, String>();
            params.put("name", name);
            params.put("purchased", String.valueOf(false));
            params.put("quantity", String.valueOf(quantity));
            params.put("minimumQuantity", String.valueOf(minimumQuantity));
            params.put("badgeQuantity", String.valueOf(badgeQuantity));
            params.put("unitPrice", String.valueOf(unitPrice));
            params.put("supplier", gson.toJson(this.supplier));
            params.put("category", gson.toJson(this.category));

            JSONObject jsonObjectProductAdd = new JSONObject(params);

            JsonObjectRequest jsonObjectRequestAddProduct = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObjectProductAdd,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            CustomToast.createToast(AdminAddProductActivity.this, name + " added successfully");
                            startActivity(new Intent(AdminAddProductActivity.this, MainActivity.class));
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            error.printStackTrace();
                        }
                    }
            );

            this.requestQueueAdd.add(jsonObjectRequestAddProduct);
        }

    }



    private View.OnClickListener AddProduct() {


        View.OnClickListener add = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addProductLogic();
            }
        };

        return add;
    }

}
