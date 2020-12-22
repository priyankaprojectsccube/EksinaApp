package com.example.eksinaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowBeneficiery {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("nick_name")
    @Expose
    private String nickName;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("purpose_for")
    @Expose
    private String purposeFor;
    @SerializedName("wallet_id")
    @Expose
    private String walletId;
    @SerializedName("bank_acc_no")
    @Expose
    private String bankAccNo;
    @SerializedName("ifsc_code")
    @Expose
    private String ifscCode;
    @SerializedName("ben_status")
    @Expose
    private String benStatus;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("wallet_id_fk")
    @Expose
    private String  wallet_id_fk;

    public String getWallet_id_fk() {
        return wallet_id_fk;
    }

    public void setWallet_id_fk(String wallet_id_fk) {
        this.wallet_id_fk = wallet_id_fk;
    }

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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPurposeFor() {
        return purposeFor;
    }

    public void setPurposeFor(String purposeFor) {
        this.purposeFor = purposeFor;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(String bankAccNo) {
        this.bankAccNo = bankAccNo;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBenStatus() {
        return benStatus;
    }

    public void setBenStatus(String benStatus) {
        this.benStatus = benStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}
