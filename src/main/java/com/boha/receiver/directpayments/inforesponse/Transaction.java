package com.boha.receiver.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Transaction implements Serializable {

    @SerializedName("receiver_routing_number")
    @Expose
    private ReceiverRoutingNumber receiverRoutingNumber;
    @SerializedName("receiver_account_number")
    @Expose
    private ReceiverAccountNumber receiverAccountNumber;
    @SerializedName("type")
    @Expose
    private Type type;
    private final static long serialVersionUID = -3250439524026451481L;

    public ReceiverRoutingNumber getReceiverRoutingNumber() {
        return receiverRoutingNumber;
    }

    public void setReceiverRoutingNumber(ReceiverRoutingNumber receiverRoutingNumber) {
        this.receiverRoutingNumber = receiverRoutingNumber;
    }

    public ReceiverAccountNumber getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(ReceiverAccountNumber receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
