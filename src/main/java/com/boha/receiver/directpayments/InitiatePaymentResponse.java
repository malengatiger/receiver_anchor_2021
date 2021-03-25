package com.boha.receiver.directpayments;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InitiatePaymentResponse implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    
    @SerializedName("stellar_account_id")
    @Expose
    private String stellarAccountId;


    @SerializedName("stellar_memo_type")
    @Expose
    private String stellarMemoType;

    @SerializedName("stellar_memo")
    @Expose
    private String stellarMemo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
