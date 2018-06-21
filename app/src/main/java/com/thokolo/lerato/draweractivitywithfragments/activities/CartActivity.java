package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.CartItem;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    double grandTotal = 0;
    private SharedPreferences sharedPreferences;
    private SharedPreferences loginSharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setTitle("Cart Item(s)");

        this.requestQueue = Volley.newRequestQueue(this);

        this.recyclerView = findViewById(R.id.recyclerViewCartItems);
        this.recyclerView.setHasFixedSize(true);
        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(this.layoutManager);
        loginSharedPreference = getSharedPreferences("LoggedInUser", Context.MODE_PRIVATE);

        this.getCartItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.cart_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        final int id = item.getItemId();

        if(id == R.id.grandTotal){

            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            item.setTitle("R" + decimalFormat.format(grandTotal));

            return true;
        }
        else if(id == R.id.clearCart){

            if(this.cartItems.isEmpty()){

                CustomToast.createToast(this,"Your cart is empty");

            }
            else if(!this.cartItems.isEmpty()){

                String baseUrl = "http:10.0.2.2:8080/cart";
                StringRequest removeAll = new StringRequest(
                        Request.Method.DELETE,
                        baseUrl + "/remove-all-items",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                             recyclerView.setAdapter(null);
                                grandTotal = 0.00;
                              CustomToast.createToast(getApplicationContext(), response);

                              Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                              startActivity(intent);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                error.printStackTrace();
                            }
                        }

                );

                this.requestQueue.add(removeAll);
            }

        }
        else if(id == R.id.proceed){

            if(this.cartItems.isEmpty()){

                CustomToast.createToast(this,"Your cart is empty");
                startActivity(new Intent(this, MainActivity.class));

            }
            else if(!this.cartItems.isEmpty()){

                if(loginSharedPreference.contains("user-logged-in")){

                    startActivity(new Intent(this, BankDetailActivity.class));

                }
                else if(!loginSharedPreference.contains("user-logged-in"))
                {

                    CustomToast.createToast(this, "Please log in first");
                    startActivity(new Intent(this, MainActivity.class));

                }


            }

        }

        return super.onOptionsItemSelected(item);
    }


    private void getCartItems() {

       String baseUrl = "http:10.0.2.2:8080/cart";

        JsonArrayRequest cartItemsRequest = new JsonArrayRequest(

                Request.Method.GET,
                baseUrl + "/cart-items",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e("Cart Items", response.toString());
                        Gson gson = new Gson();

                        Type type = new TypeToken<ArrayList<CartItem>>(){}.getType();

                        cartItems = gson.fromJson(String.valueOf(response), type);
                        for(int i = 0; i < cartItems.size(); i++){

                           grandTotal += cartItems.get(i).getProduct().getUnitPrice() * cartItems.get(i).getCount();

                        }

                        sharedPreferences = getApplicationContext().getSharedPreferences("CartSizeValue", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("cartSize", cartItems.size());
                        editor.putString("cart_items", gson.toJson(cartItems));
                        editor.putString("grand_total", gson.toJson(grandTotal));
                        editor.apply();


                        recyclerViewAdapter = new CartItemAdapter(cartItems);
                        recyclerView.setAdapter(recyclerViewAdapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();

                    }
                }
        );

        this.requestQueue.add(cartItemsRequest);
    }
}
