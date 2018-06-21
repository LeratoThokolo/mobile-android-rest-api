package com.thokolo.lerato.draweractivitywithfragments.activities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thokolo.lerato.draweractivitywithfragments.R;
import com.thokolo.lerato.draweractivitywithfragments.models.CartItem;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReceiptProductsAdapter extends RecyclerView.Adapter<ReceiptProductsAdapter.ReceiptItemViewHolder>{

    private ArrayList<CartItem> cartItems;

    public static class ReceiptItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageViewProduct;
        private TextView textViewProductName;
        private TextView textViewPrice;
        private TextView textViewItemsCount;
        private TextView textViewSubTotal;

        public ReceiptItemViewHolder(View itemView) {
            super(itemView);

            this.imageViewProduct = itemView.findViewById(R.id.imageView);
            this.textViewProductName = itemView.findViewById(R.id.productName);
            this.textViewItemsCount = itemView.findViewById(R.id.productCount);
            this.textViewPrice = itemView.findViewById(R.id.productPrice);
            this.textViewSubTotal = itemView.findViewById(R.id.productSubTotal);

        }
    }

    public ReceiptProductsAdapter(ArrayList<CartItem> cartItems){

        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ReceiptItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.receipt_products_view, parent, false);

        ReceiptItemViewHolder receiptItemViewHolder = new ReceiptItemViewHolder(view);

        return receiptItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiptItemViewHolder holder, int position) {

        CartItem cartItem = this.cartItems.get(position);
        holder.textViewPrice.setText("Price = R" + cartItem.getProduct().getUnitPrice());
        holder.textViewItemsCount.setText("# of items = " + cartItem.getCount());

        if(cartItem.getProduct().getName().length() > 13){

            String name = cartItem.getProduct().getName().substring(0,12);

            holder.textViewProductName.setText(name + "...");
        }
        else{

            holder.textViewProductName.setText(cartItem.getProduct().getName());

        }

        holder.imageViewProduct.setImageResource(cartItem.getProduct().getImageResource());
        double subTotal = cartItem.getCount() * cartItem.getProduct().getUnitPrice();

        DecimalFormat decimalFormat = new DecimalFormat("##.#");
        holder.textViewSubTotal.setText("SubTotal: R" + decimalFormat.format(subTotal));

    }

    @Override
    public int getItemCount() {
        return this.cartItems.size();
    }

}
