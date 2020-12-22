package com.example.eksinaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConvertCurrency {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("convert_rate")
    @Expose
    private String convertRate;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("ero_recive_amount")
    @Expose
    private Double eroReciveAmount;
    @SerializedName("ero_fees")
    @Expose
    private Double eroFees;
    @SerializedName("ero_amount")
    @Expose
    private String eroAmount;
    @SerializedName("ben_recive_amount")
    @Expose
    private String benReciveAmount;
    @SerializedName("ben_fees")
    @Expose
    private String benFees;
    @SerializedName("ben_amount")
    @Expose
    private String benAmount;
    @SerializedName("doc_varification")
    @Expose
    private String docVarification;
    @SerializedName("trans_count")
    @Expose
    private Integer transCount;

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

    public String getConvertRate() {
        return convertRate;
    }

    public void setConvertRate(String convertRate) {
        this.convertRate = convertRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getEroReciveAmount() {
        return eroReciveAmount;
    }

    public void setEroReciveAmount(Double eroReciveAmount) {
        this.eroReciveAmount = eroReciveAmount;
    }

    public Double getEroFees() {
        return eroFees;
    }

    public void setEroFees(Double eroFees) {
        this.eroFees = eroFees;
    }

    public String getEroAmount() {
        return eroAmount;
    }

    public void setEroAmount(String eroAmount) {
        this.eroAmount = eroAmount;
    }

    public String getBenReciveAmount() {
        return benReciveAmount;
    }

    public void setBenReciveAmount(String benReciveAmount) {
        this.benReciveAmount = benReciveAmount;
    }

    public String getBenFees() {
        return benFees;
    }

    public void setBenFees(String benFees) {
        this.benFees = benFees;
    }

    public String getBenAmount() {
        return benAmount;
    }

    public void setBenAmount(String benAmount) {
        this.benAmount = benAmount;
    }

    public String getDocVarification() {
        return docVarification;
    }

    public void setDocVarification(String docVarification) {
        this.docVarification = docVarification;
    }

    public Integer getTransCount() {
        return transCount;
    }

    public void setTransCount(Integer transCount) {
        this.transCount = transCount;
    }



}
