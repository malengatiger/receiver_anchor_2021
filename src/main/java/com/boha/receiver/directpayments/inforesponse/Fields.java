package com.boha.receiver.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Fields implements Serializable {

    @SerializedName("transaction")
    @Expose
    private Transaction transaction;
    private final static long serialVersionUID = 8558687811562245450L;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
