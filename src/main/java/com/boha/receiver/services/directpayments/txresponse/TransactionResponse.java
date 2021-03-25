package com.boha.receiver.services.directpayments.txresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TransactionResponse implements Serializable
{

    @SerializedName("transaction")
    @Expose
    private Transaction transaction;
    private final static long serialVersionUID = 8290860158185585998L;

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

}
