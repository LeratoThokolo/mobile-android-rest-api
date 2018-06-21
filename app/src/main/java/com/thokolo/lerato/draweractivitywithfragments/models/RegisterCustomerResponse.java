package com.thokolo.lerato.draweractivitywithfragments.models;

public class RegisterCustomerResponse {

    private Customer customer;
    private String responseMessage;

    public RegisterCustomerResponse() {
    }

    public RegisterCustomerResponse(Customer customer, String responseMessage) {
        this.customer = customer;
        this.responseMessage = responseMessage;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
