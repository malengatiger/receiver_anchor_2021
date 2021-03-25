package com.boha.receiver.services.directpayments;

import com.boha.receiver.services.directpayments.inforesponse.Fields;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InitiatePayment implements Serializable {

    @SerializedName("amount")
    @Expose
    private String amount;
    
    @SerializedName("asset_code")
    @Expose
    private String assetCode;
    @SerializedName("asset_issuer")
    @Expose
    private String assetIssuer;
    @SerializedName("sender_id")
    @Expose
    private String senderId;
    @SerializedName("receiver_id")
    @Expose
    private String receiverId;
    @SerializedName("fields")
    @Expose
    private Fields fields;
    private final static long serialVersionUID = 7121400095305624231L;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetIssuer() {
        return assetIssuer;
    }

    public void setAssetIssuer(String assetIssuer) {
        this.assetIssuer = assetIssuer;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

}
