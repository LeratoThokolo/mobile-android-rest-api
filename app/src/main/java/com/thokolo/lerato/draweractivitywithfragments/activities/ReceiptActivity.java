package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.ApplicationHeaders;
import com.thokolo.lerato.draweractivitywithfragments.models.CartItem;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomErrorToast;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;
import com.thokolo.lerato.draweractivitywithfragments.models.Customer;
import com.thokolo.lerato.draweractivitywithfragments.models.Order;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ReceiptActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    double grandTotal = 0;
    private SharedPreferences sharedPreferences;
    private Customer customer;
    private SharedPreferences sharedPreferencesCustomerDetails;
    private RequestQueue requestQueueCreateOrder;
    private Order order;
    private SharedPreferences loginSharedPreferences;
    private RequestQueue requestQueueCartAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        getSupportActionBar().setTitle("Receipt");


        this.requestQueueCreateOrder = Volley.newRequestQueue(this);
        this.requestQueueCartAll = Volley.newRequestQueue(this);
        this.requestQueue = Volley.newRequestQueue(this);
        this.recyclerView = findViewById(R.id.recyclerViewReceipt);
        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.loginSharedPreferences = getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);
        sharedPreferences = getApplicationContext().getSharedPreferences("CartSizeValue", Context.MODE_PRIVATE);
        this.sharedPreferencesCustomerDetails = getSharedPreferences("CustomerLoggedIn", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        this.customer = gson.fromJson(this.sharedPreferencesCustomerDetails.getString("customer-logged-in", "Customer not logged in"), Customer.class);


        this.grandTotal = Double.parseDouble(sharedPreferences.getString("grand_total", "No data found"));
        this.getCartItems();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.receipt_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        final int id = item.getItemId();


        if(id == R.id.grandTotal){

            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            item.setTitle("R" + decimalFormat.format(this.grandTotal));

            return true;

        }else if(id == R.id.checkout){

            Random random = new Random();
            final int orderNumber = random.nextInt(1000) + 234;

            DecimalFormat decimalFormat = new DecimalFormat("##.#");

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Response")
                    .setMessage("Customer name: " + this.customer.getFullNames() + "\n" + "Order #: " + orderNumber + "\n" +
                    "Order date: " + new Date().toString() + "\n" + "Amount Due:R" + decimalFormat.format(this.grandTotal))
                    .setPositiveButton("Checkout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            Date date = new Date();
                            date.getTime();
                            order = new Order();
                            order.setAmount(grandTotal);
                            order.setOrderNumber(orderNumber);
                            order.setArea(customer.getSurburb());
                            order.setDelivered("No");
                            order.setDriverID(0);
                            //order.setDateCreated(new Date());
                            order.setUserID(customer.getUserID());
                            order.setFullNames(customer.getFullNames());
                            order.setHouseNo(customer.getHouseNo());

                            String url = "http://10.0.2.2:8080/order/order-create";

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("amount", String.valueOf(order.getAmount()));
                            params.put("orderNumber", String.valueOf(orderNumber));
                            params.put("area", String.valueOf(order.getArea()));
                            params.put("delivered", order.getDelivered());
                            params.put("driverID", String.valueOf(order.getDriverID()));
                            params.put("dateCreated", String.valueOf(order.getDateCreated()));
                            params.put("userID", String.valueOf(order.getUserID()));
                            params.put("fullNames", order.getFullNames());
                            params.put("houseNo", order.getHouseNo());

                            JSONObject orderObject = new JSONObject(params);

                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                                    Request.Method.POST,
                                    url,
                                    orderObject,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {

                                            AlertDialog.Builder builder1 = new AlertDialog.Builder(ReceiptActivity.this)
                                                    .setTitle("Thank You")
                                                    .setMessage("Please expect your order within 30 minutes!!")
                                                    .setPositiveButton("Okay thanks", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                            SharedPreferences.Editor editor = loginSharedPreferences.edit();
                                                            editor.remove("user-logged-in");
                                                            editor.apply();
                                                            SharedPreferences.Editor editor1 = sharedPreferencesCustomerDetails.edit();
                                                            editor1.remove("customer-logged-in");
                                                            editor1.apply();
                                                            SharedPreferences.Editor editor2 = sharedPreferences.edit();
                                                            editor2.remove("grand_total");
                                                            editor2.remove("cartSize");
                                                            editor2.remove("cart_items");
                                                            editor2.apply();

                                                            String baseUrl = "http:10.0.2.2:8080/cart";
                                                            StringRequest removeAll = new StringRequest(
                                                                    Request.Method.DELETE,
                                                                    baseUrl + "/remove-all-items",
                                                                    new Response.Listener<String>() {
                                                                        @Override
                                                                        public void onResponse(String response) {

                                                                            recyclerView.setAdapter(null);
                                                                            grandTotal = 0.00;
                                                                        }
                                                                    },
                                                                    new Response.ErrorListener() {
                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {

                                                                            error.printStackTrace();
                                                                        }
                                                                    }

                                                            );

                                                            requestQueueCartAll.add(removeAll);


                                                            String url = "http://10.0.2.2:8080/bank-account/remove-accounts";
                                                            StringRequest stringRequest = new StringRequest(
                                                                    Request.Method.DELETE,
                                                                    url,
                                                                    new Response.Listener<String>() {
                                                                        @Override
                                                                        public void onResponse(String response) {

                                                                        }
                                                                    },
                                                                    new Response.ErrorListener() {
                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {

                                                                            error.printStackTrace();
                                                                        }
                                                                    }
                                                            );
                                                            requestQueue.add(stringRequest);

                                                            startActivity(new Intent(ReceiptActivity.this, MainActivity.class));
                                                        }
                                                    });

                                            AlertDialog dialog1 = builder1.create();
                                            dialog1.show();

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
                                    return  headers;
                                }
                            };

                            requestQueueCreateOrder.add(jsonObjectRequest);
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void getCartItems(){

        Type type = new TypeToken<ArrayList<CartItem>>(){}.getType();
        Gson gson = new Gson();

        this.cartItems = gson.fromJson(this.sharedPreferences.getString("cart_items", "Data not found!!"), type);
        this.recyclerViewAdapter = new ReceiptProductsAdapter(this.cartItems);
        this.recyclerView.setAdapter(this.recyclerViewAdapter);

    }
}
