package com.thokolo.lerato.draweractivitywithfragments.models;

public class LoginResponse {

    private User user;
    private String messageResponse;

    public LoginResponse() {
    }

    public LoginResponse(User user, String messageResponse) {
        this.user = user;
        this.messageResponse = messageResponse;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(String messageResponse) {
        this.messageResponse = messageResponse;
    }
}
