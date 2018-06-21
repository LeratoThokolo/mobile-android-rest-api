package com.thokolo.lerato.draweractivitywithfragments.models;

public class BankDetail {

    private int bankDetailID;
    private int pin;
    private int accountNumber;
    private int userID;
    private Bank bank;

    public BankDetail() {

    }

    public BankDetail(int bankDetailID, int pin, int accountNumber, int userID, Bank bank) {
        this.bankDetailID = bankDetailID;
        this.pin = pin;
        this.accountNumber = accountNumber;
        this.userID = userID;
        this.bank = bank;
    }

    public int getBankDetailID() {
        return bankDetailID;
    }

    public void setBankDetailID(int bankDetailID) {
        this.bankDetailID = bankDetailID;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
