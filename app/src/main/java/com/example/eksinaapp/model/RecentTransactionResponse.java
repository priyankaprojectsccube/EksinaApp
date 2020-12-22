package com.example.eksinaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecentTransactionResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("transaction")
    @Expose
    private List<RecenetTransaction> transaction = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RecenetTransaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<RecenetTransaction> transaction) {
        this.transaction = transaction;
    }

}
