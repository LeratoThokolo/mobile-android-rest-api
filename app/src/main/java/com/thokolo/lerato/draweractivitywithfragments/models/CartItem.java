package com.thokolo.lerato.draweractivitywithfragments.models;

import com.thokolo.lerato.draweractivitywithfragments.models.Product;

public class CartItem {

    private Product product;
    private int count;

    public CartItem() {
    }

    public CartItem(Product product, int count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

