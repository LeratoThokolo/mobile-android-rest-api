package com.thokolo.lerato.draweractivitywithfragments.models;

import java.util.Date;

public class Order {

    private int orderID;
    private int orderNumber;
    private double amount;
    private Date dateCreated;
    private String delivered;
    private int userID;
    private String houseNo;
    private String fullNames;
    private String area;
    private int driverID;

    public Order() {
    }

    public Order(int orderID, int orderNumber, double amount, Date dateCreated, String delivered, int userID, String houseNo, String fullNames, String area, int driverID) {
        this.orderID = orderID;
        this.orderNumber = orderNumber;
        this.amount = amount;
        this.dateCreated = dateCreated;
        this.delivered = delivered;
        this.userID = userID;
        this.houseNo = houseNo;
        this.fullNames = fullNames;
        this.area = area;
        this.driverID = driverID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDelivered() {
        return delivered;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getFullNames() {
        return fullNames;
    }

    public void setFullNames(String fullNames) {
        this.fullNames = fullNames;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }
}
