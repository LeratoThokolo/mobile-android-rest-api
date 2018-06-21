package com.thokolo.lerato.draweractivitywithfragments.models;

public class Bank {

    private int bankID;
    private String bankName;
    private String branchCode;

    public Bank() {

    }

    public Bank(int bankID, String bankName, String branchCode) {
        this.bankID = bankID;
        this.bankName = bankName;
        this.branchCode = branchCode;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
}
