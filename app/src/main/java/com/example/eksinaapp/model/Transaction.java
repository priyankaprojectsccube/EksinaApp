package com.example.eksinaapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("pay_date")
    @Expose
    private String payDate;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id_fk")
    @Expose
    private String userIdFk;
    @SerializedName("card_name")
    @Expose
    private String cardName;
    @SerializedName("card_email")
    @Expose
    private String cardEmail;
    @SerializedName("stripe_customer_id")
    @Expose
    private String stripeCustomerId;
    @SerializedName("beneficiary_id")
    @Expose
    private String beneficiaryId;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("paid_amount")
    @Expose
    private String paidAmount;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("beneficiary_amount")
    @Expose
    private String beneficiaryAmount;
    @SerializedName("txn_id")
    @Expose
    private String txnId;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("apg_status")
    @Expose
    private String apgStatus;
    @SerializedName("benificiery_code")
    @Expose
    private String benificieryCode;
    @SerializedName("payout_process_status")
    @Expose
    private String payoutProcessStatus;
    @SerializedName("payout_process_agency_id")
    @Expose
    private String payoutProcessAgencyId;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("created_date")
    @Expose
    private Object createdDate;
    @SerializedName("updated_date")
    @Expose
    private Object updatedDate;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("city_name")
    @Expose
    private Object cityName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("nick_name")
    @Expose
    private String nickName;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("purpose_for")
    @Expose
    private String purposeFor;
    @SerializedName("wallet_id")
    @Expose
    private String walletId;
    @SerializedName("bank_acc_no")
    @Expose
    private Object bankAccNo;
    @SerializedName("ifsc_code")
    @Expose
    private Object ifscCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_deleted")
    @Expose
    private Object isDeleted;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("country_code3")
    @Expose
    private String countryCode3;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("dial_code")
    @Expose
    private String dialCode;
    @SerializedName("indicatif")
    @Expose
    private String indicatif;
    @SerializedName("currency_name")
    @Expose
    private String currencyName;
    @SerializedName("description")
    @Expose
    private String description;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserIdFk() {
        return userIdFk;
    }

    public void setUserIdFk(String userIdFk) {
        this.userIdFk = userIdFk;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardEmail() {
        return cardEmail;
    }

    public void setCardEmail(String cardEmail) {
        this.cardEmail = cardEmail;
    }

    public String getStripeCustomerId() {
        return stripeCustomerId;
    }

    public void setStripeCustomerId(String stripeCustomerId) {
        this.stripeCustomerId = stripeCustomerId;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getBeneficiaryAmount() {
        return beneficiaryAmount;
    }

    public void setBeneficiaryAmount(String beneficiaryAmount) {
        this.beneficiaryAmount = beneficiaryAmount;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getApgStatus() {
        return apgStatus;
    }

    public void setApgStatus(String apgStatus) {
        this.apgStatus = apgStatus;
    }

    public String getBenificieryCode() {
        return benificieryCode;
    }

    public void setBenificieryCode(String benificieryCode) {
        this.benificieryCode = benificieryCode;
    }

    public String getPayoutProcessStatus() {
        return payoutProcessStatus;
    }

    public void setPayoutProcessStatus(String payoutProcessStatus) {
        this.payoutProcessStatus = payoutProcessStatus;
    }

    public String getPayoutProcessAgencyId() {
        return payoutProcessAgencyId;
    }

    public void setPayoutProcessAgencyId(String payoutProcessAgencyId) {
        this.payoutProcessAgencyId = payoutProcessAgencyId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getCityName() {
        return cityName;
    }

    public void setCityName(Object cityName) {
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public Object getBankAccNo() {
        return bankAccNo;
    }

    public void setBankAccNo(Object bankAccNo) {
        this.bankAccNo = bankAccNo;
    }

    public Object getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(Object ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Object isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode3() {
        return countryCode3;
    }

    public void setCountryCode3(String countryCode3) {
        this.countryCode3 = countryCode3;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getDialCode() {
        return dialCode;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public String getIndicatif() {
        return indicatif;
    }

    public void setIndicatif(String indicatif) {
        this.indicatif = indicatif;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
