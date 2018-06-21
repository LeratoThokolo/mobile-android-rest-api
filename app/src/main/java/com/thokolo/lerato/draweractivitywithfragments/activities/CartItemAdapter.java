package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartViewHolder> {

    private ArrayList<CartItem> cartItems;

    public static class CartViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageViewProduct;
        public TextView cartProductNameTextView;
        public TextView cartItemsCountTextView;
        public TextView subTotalTextView;
        public TextView cartItemPriceProduct;
        public ImageView imageViewRemoveItem;

        public CartViewHolder(View itemView) {
            super(itemView);

            this.imageViewProduct = itemView.findViewById(R.id.imageViewCartProduct);
            this.cartItemsCountTextView = itemView.findViewById(R.id.cartItemsCount);
            this.imageViewRemoveItem = itemView.findViewById(R.id.delete_icon);
            this.subTotalTextView = itemView.findViewById(R.id.textViewSubtotal);
            this.cartProductNameTextView = itemView.findViewById(R.id.productNameCart);
            this.cartItemPriceProduct = itemView.findViewById(R.id.productPriceCart);

        }
    }

    public CartItemAdapter(ArrayList<CartItem> cartItems){

        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_custom, parent, false);

        CartViewHolder cartViewHolder = new CartViewHolder(view);

        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, int position) {


        final CartItem cartItem = this.cartItems.get(position);

        final Gson gson = new Gson();

        DecimalFormat df = new DecimalFormat("#.##");

        holder.cartItemPriceProduct.setText("Price = R" + cartItem.getProduct().getUnitPrice());
        holder.cartProductNameTextView.setText(cartItem.getProduct().getName());
        holder.cartItemsCountTextView.setText("# of items = " + cartItem.getCount());
        holder.imageViewProduct.setImageResource(cartItem.getProduct().getImageResource());

        double subTotal = cartItem.getCount() * cartItem.getProduct().getUnitPrice();
        holder.subTotalTextView.setText("Subtotal:R" + df.format(subTotal));

        holder.imageViewRemoveItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());


                String baseUrl = "http:10.0.2.2:8080/cart";

                Product product = cartItem.getProduct();
                final int count = cartItem.getCount();


                Map<String, String> params = new HashMap<String, String>();
                params.put("product", gson.toJson(product));
                params.put("count", gson.toJson(count));

                JSONObject item  = new JSONObject(params);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(

                        Request.Method.PUT,
                        baseUrl + "/remove-item",
                        item,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                CartItem c = gson.fromJson(String.valueOf(response), CartItem.class);


                                if(cartItem.getCount() == 1){

                                    CustomToast.createToast(v.getContext(), cartItem.getProduct().getName() + " removed from your cart");
                                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                                    v.getContext().startActivity(intent);
                                }
                                else if(cartItem.getCount() > 1){

                                    CustomToast.createToast(v.getContext(), cartItem.getProduct().getName() + " quantity reduced by 1");

                                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                                    v.getContext().startActivity(intent);
                                }
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
        });

    }

    @Override
    public int getItemCount() {

       return this.cartItems.size();
    }

}
