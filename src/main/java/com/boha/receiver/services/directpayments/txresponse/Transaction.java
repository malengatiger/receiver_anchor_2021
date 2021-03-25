package com.boha.receiver.services.directpayments.txresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Transaction implements Serializable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_eta")
    @Expose
    private Integer statusEta;
    @SerializedName("external_transaction_id")
    @Expose
    private String externalTransactionId;
    @SerializedName("amount_in")
    @Expose
    private String amountIn;
    @SerializedName("amount_out")
    @Expose
    private String amountOut;
    @SerializedName("amount_fee")
    @Expose
    private String amountFee;
    @SerializedName("started_at")
    @Expose
    private String startedAt;

    @SerializedName("stellar_account_id")
    @Expose
    private String stellarAccountId;

    @SerializedName("stellar_memo_type")
    @Expose
    private String stellarMemoType;

    @SerializedName("stellar_memo")
    @Expose
    private String stellarMemo;

    @SerializedName("completed_at")
    @Expose
    private String completedAt;

    @SerializedName("stellar_transaction_id")
    @Expose
    private String stellarTransactionId;

    @SerializedName("refunded")
    @Expose
    private boolean refunded;


    private final static long serialVersionUID = -7783814291034374407L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusEta() {
        return statusEta;
    }

    public void setStatusEta(Integer statusEta) {
        this.statusEta = statusEta;
    }

    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    public void setExternalTransactionId(String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
    }

    public String getAmountIn() {
        return amountIn;
    }

    public void setAmountIn(String amountIn) {
        this.amountIn = amountIn;
    }

    public String getAmountOut() {
        return amountOut;
    }

    public void setAmountOut(String amountOut) {
        this.amountOut = amountOut;
    }

    public String getAmountFee() {
        return amountFee;
    }

    public void setAmountFee(String amountFee) {
        this.amountFee = amountFee;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getStellarAccountId() {
        return stellarAccountId;
    }

    public void setStellarAccountId(String stellarAccountId) {
        this.stellarAccountId = stellarAccountId;
    }

    public String getStellarMemoType() {
        return stellarMemoType;
    }

    public void setStellarMemoType(String stellarMemoType) {
        this.stellarMemoType = stellarMemoType;
    }

    public String getStellarMemo() {
        return stellarMemo;
    }

    public void setStellarMemo(String stellarMemo) {
        this.stellarMemo = stellarMemo;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public String getStellarTransactionId() {
        return stellarTransactionId;
    }

    public void setStellarTransactionId(String stellarTransactionId) {
        this.stellarTransactionId = stellarTransactionId;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }
}