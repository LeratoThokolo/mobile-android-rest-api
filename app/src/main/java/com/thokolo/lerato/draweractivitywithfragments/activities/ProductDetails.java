package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.CartItem;
import com.thokolo.lerato.draweractivitywithfragments.models.CustomToast;
import com.thokolo.lerato.draweractivitywithfragments.models.Product;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductDetails extends AppCompatActivity {

    private ImageView imageView;
    private TextView price;
    private TextView name;
    private Button btnAddToCart;
    private CartItem cartItem = null;
    private int count = 1;
    private Button decrementCount;
    private Button incrementCount;
    private TextView itemCount;
    private RequestQueue requestQueue;
    private AlertDialog.Builder builder;
    private Product product = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        this.incrementCount = findViewById(R.id.buttonPlus);
        this.decrementCount = findViewById(R.id.buttonMinus);
        this.itemCount = findViewById(R.id.textViewCount);
        this.itemCount.setText(String.valueOf(this.count));

        this.requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();


        Gson gson = new Gson();

        this.product = new Product();
        this.product  = gson.fromJson(intent.getStringExtra("product"), Product.class);
        getSupportActionBar().setTitle(this.product.getName());

        this.imageView = findViewById(R.id.imgProduct);
        this.price = findViewById(R.id.textViewName);
        this.name = findViewById(R.id.textViewPrice);


        this.imageView.setImageResource(this.product.getImageResource());
        this.price.setTextColor(Color.RED);
        this.price.setText("R" + this.product.getUnitPrice());
        this.name.setText(this.product.getName());

        this.btnAddToCart = findViewById(R.id.addToCart);
        this.btnAddToCart.setOnClickListener(addToCart());

    }

    public void incrementCountValue(View view) {

        this.count++;
        this.itemCount.setText(String.valueOf(this.count));
        this.decrementCount.setEnabled(true);

        if(this.count == this.product.getQuantity()){

            this.incrementCount.setEnabled(false);
            CustomToast.createToast(this, "Maximum stock available!!");
        }

    }

    public void decrementCountValue(View view) {

        this.count--;
        this.itemCount.setText(String.valueOf(this.count));
        this.incrementCount.setEnabled(true);


        if(this.count == 1){

            this.decrementCount.setEnabled(false);
        }

    }

    private View.OnClickListener addToCart() {

        View.OnClickListener addCartItem = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String baseUrl = "http://10.0.2.2:8080/cart";

                final Gson gson = new Gson();

                Map<String, String> params = new HashMap<String, String>();
                params.put("product",gson.toJson(product));
                params.put("count", gson.toJson(count));

                JSONObject cartItemToServer = new JSONObject(params);

               JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                       Request.Method.POST,
                       baseUrl + "/add-item",
                       cartItemToServer,
                       new Response.Listener<JSONObject>() {
                           @Override
                           public void onResponse(JSONObject response) {

                               new CartItem();
                               CartItem c = gson.fromJson(String.valueOf(response), CartItem.class);

                               builder = new AlertDialog.Builder(ProductDetails.this)
                                       .setTitle("Response")
                                       .setMessage(c.getProduct().getName() + " added to your cart!!")
                                       .setPositiveButton("Go to", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {
                                               Intent intent = new Intent(ProductDetails.this, CartActivity.class);
                                               startActivity(intent);
                                           }
                                       })
                               .setNegativeButton("Buy more", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                       startActivity(new Intent(ProductDetails.this, MainActivity.class));
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
                       headers.put("Content-Type", "application/json");

                       return headers;
                   }

               };

                requestQueue.add(jsonObjectRequest);
            }
        };

        return addCartItem;
    }
}
