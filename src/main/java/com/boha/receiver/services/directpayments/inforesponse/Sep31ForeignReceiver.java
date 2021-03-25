package com.boha.receiver.services.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sep31ForeignReceiver implements Serializable {
    public Sep31ForeignReceiver(String description) {
        this.description = description;
    }

    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = -2206363770809758041L;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
