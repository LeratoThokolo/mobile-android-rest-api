package com.thokolo.lerato.draweractivitywithfragments.models;

public class Customer extends User {

    private String houseNo;
    private String streetName;
    private String surburb;
    private String town;
    private String postalCode;


    public Customer() {
    }

    public Customer(User user){

        super(user);
    }

    public Customer(String houseNo, String streetName, String surburb, String town, String postalCode) {

        this.houseNo = houseNo;
        this.streetName = streetName;
        this.surburb = surburb;
        this.town = town;
        this.postalCode = postalCode;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getSurburb() {
        return surburb;
    }

    public void setSurburb(String surburb) {
        this.surburb = surburb;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
