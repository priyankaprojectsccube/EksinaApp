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
    @SerializedName("fees_percent")
    @Expose
    private Integer feesPercent;
    @SerializedName("ero_recive_amount")
    @Expose
    private String eroReciveAmount;
    @SerializedName("ero_total_amount")
    @Expose
    private Double eroTotalAmount;
    @SerializedName("ero_fees")
    @Expose
    private Double eroFees;
    @SerializedName("ero_amount")
    @Expose
    private String eroAmount;
    @SerializedName("ben_recive_amount")
    @Expose
    private String benReciveAmount;
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

    public Integer getFeesPercent() {
        return feesPercent;
    }

    public void setFeesPercent(Integer feesPercent) {
        this.feesPercent = feesPercent;
    }

    public String getEroReciveAmount() {
        return eroReciveAmount;
    }

    public void setEroReciveAmount(String eroReciveAmount) {
        this.eroReciveAmount = eroReciveAmount;
    }

    public Double getEroTotalAmount() {
        return eroTotalAmount;
    }

    public void setEroTotalAmount(Double eroTotalAmount) {
        this.eroTotalAmount = eroTotalAmount;
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
