package com.thokolo.lerato.draweractivitywithfragments.models;

public class Product {

    private int productID;
    private int imageResource;
    private String name;
    private double unitPrice;
    private int quantity;
    private int badgeQuantity;
    private int minimumQuantity;
    private String image;
    private boolean purchased;
    private Category category;
    private Supplier supplier;



    public Product() {
    }

    public Product(int productID, int imageResource, String name, double unitPrice, int quantity, int badgeQuantity, int minimumQuantity, String image, boolean purchased, Category category, Supplier supplier) {
        this.productID = productID;
        this.imageResource = imageResource;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.badgeQuantity = badgeQuantity;
        this.minimumQuantity = minimumQuantity;
        this.image = image;
        this.purchased = purchased;
        this.category = category;
        this.supplier = supplier;
    }

    public int getBadgeQuantity() {
        return badgeQuantity;
    }

    public void setBadgeQuantity(int badgeQuantity) {
        this.badgeQuantity = badgeQuantity;
    }

    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
