package com.boha.receiver.services.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Receive implements Serializable {

    @SerializedName("ZARK")
    @Expose
    private ZARK zARK;
    private final static long serialVersionUID = 7278597027200051722L;

    public ZARK getZARK() {
        return zARK;
    }

    public void setZARK(ZARK zARK) {
        this.zARK = zARK;
    }

}
