package com.boha.receiver.services.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sep12 implements Serializable {

    @SerializedName("sender")
    @Expose
    private Sender sender;
    @SerializedName("receiver")
    @Expose
    private Receiver receiver;
    private final static long serialVersionUID = -6985446386930790705L;

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

}
