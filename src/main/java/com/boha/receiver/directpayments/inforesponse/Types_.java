package com.boha.receiver.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Types_ implements Serializable {

    @SerializedName("sep31-receiver")
    @Expose
    private Sep31Receiver sep31Receiver;
    @SerializedName("sep31-foreign-receiver")
    @Expose
    private Sep31ForeignReceiver sep31ForeignReceiver;
    private final static long serialVersionUID = 49249907279089710L;

    public Sep31Receiver getSep31Receiver() {
        return sep31Receiver;
    }

    public void setSep31Receiver(Sep31Receiver sep31Receiver) {
        this.sep31Receiver = sep31Receiver;
    }

    public Sep31ForeignReceiver getSep31ForeignReceiver() {
        return sep31ForeignReceiver;
    }

    public void setSep31ForeignReceiver(Sep31ForeignReceiver sep31ForeignReceiver) {
        this.sep31ForeignReceiver = sep31ForeignReceiver;
    }

}
