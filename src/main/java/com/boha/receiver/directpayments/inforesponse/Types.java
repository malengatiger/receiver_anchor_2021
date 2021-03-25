package com.boha.receiver.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Types implements Serializable {

    public Types(Sep31Sender sep31Sender, Sep31LargeSender sep31LargeSender, Sep31ForeignSender sep31ForeignSender) {
        this.sep31Sender = sep31Sender;
        this.sep31LargeSender = sep31LargeSender;
        this.sep31ForeignSender = sep31ForeignSender;
    }

    public Types() {
    }

    @SerializedName("sep31-sender")
    @Expose
    private Sep31Sender sep31Sender;
    @SerializedName("sep31-large-sender")
    @Expose
    private Sep31LargeSender sep31LargeSender;
    @SerializedName("sep31-foreign-sender")
    @Expose
    private Sep31ForeignSender sep31ForeignSender;
    private final static long serialVersionUID = 2625586614387702621L;

    public Sep31Sender getSep31Sender() {
        return sep31Sender;
    }

    public void setSep31Sender(Sep31Sender sep31Sender) {
        this.sep31Sender = sep31Sender;
    }

    public Sep31LargeSender getSep31LargeSender() {
        return sep31LargeSender;
    }

    public void setSep31LargeSender(Sep31LargeSender sep31LargeSender) {
        this.sep31LargeSender = sep31LargeSender;
    }

    public Sep31ForeignSender getSep31ForeignSender() {
        return sep31ForeignSender;
    }

    public void setSep31ForeignSender(Sep31ForeignSender sep31ForeignSender) {
        this.sep31ForeignSender = sep31ForeignSender;
    }

}
