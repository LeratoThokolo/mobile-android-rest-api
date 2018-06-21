package com.thokolo.lerato.draweractivitywithfragments.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class User {

    private int userID;
    private String cellNo;
    private String userName;
    private String fullNames;
    private String password;
    private String email;
    private Date lastPasswordResetDate;
    private ArrayList<Role> roles;

    public User() {
    }

    public User(User user) {

        this.cellNo = user.getCellNo();
        this.email = user.getEmail();
        this.fullNames = user.getFullNames();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.userID = user.getUserID();
        this.userName = user.getUserName();
        this.lastPasswordResetDate = user.lastPasswordResetDate;
    }

    public User(int userID, String cellNo, String userName, String fullNames, String password, String email,  ArrayList<Role> roles) {
        this.userID = userID;
        this.cellNo = cellNo;
        this.userName = userName;
        this.fullNames = fullNames;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public int getUserID() {
        return userID;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getCellNo() {
        return cellNo;
    }

    public void setCellNo(String cellNo) {
        this.cellNo = cellNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullNames() {
        return fullNames;
    }

    public void setFullNames(String fullNames) {
        this.fullNames = fullNames;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwod) {
        this.password = passwod;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public  ArrayList<Role> getRoles() {
        return roles;
    }

    public void setRoles( ArrayList<Role> roles) {
        this.roles = roles;
    }
}
