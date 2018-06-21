package com.thokolo.lerato.draweractivitywithfragments.models;

import java.util.ArrayList;
import java.util.Set;

public class Supplier extends User {

    public Supplier() {
    }


    public Supplier(int userID, String cellNo, String userName, String fullNames, String passwod, String email, ArrayList<Role> roles) {
        super(userID, cellNo, userName, fullNames, passwod, email, roles);
    }
}
