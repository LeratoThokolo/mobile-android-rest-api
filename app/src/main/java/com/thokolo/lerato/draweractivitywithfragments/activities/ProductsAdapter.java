package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.Product;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

    private ArrayList<Product> products;


    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView productNameTextView;
        public TextView priceTextView;


        public ProductViewHolder(View itemView) {
            super(itemView);


            this.imageView = itemView.findViewById(R.id.imageView);
            this.productNameTextView = itemView.findViewById(R.id.productName);
            this.priceTextView = itemView.findViewById(R.id.productPrice);


        }
    }

    public ProductsAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_item, parent, false);

        ProductViewHolder viewHolder = new ProductViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        final Product product =  this.products.get(position);

        final Gson gson = new Gson();


        holder.imageView.setImageResource(product.getImageResource());
        holder.productNameTextView.setText(product.getName());
        holder.priceTextView.setText("R" + String.valueOf(product.getUnitPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductDetails.class);
                intent.putExtra("product", gson.toJson(product));
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return products.size();
    }
}
