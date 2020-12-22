package com.example.eksinaapp.model;

public class RecentTransfer {

String name,date,amount,transfer;


    public RecentTransfer(String name, String date, String amount, String transfer) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.transfer = transfer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }
}
