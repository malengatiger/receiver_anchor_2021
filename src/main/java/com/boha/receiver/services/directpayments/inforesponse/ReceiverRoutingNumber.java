package com.boha.receiver.services.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReceiverRoutingNumber implements Serializable {
    public ReceiverRoutingNumber(String description) {
        this.description = description;
    }

    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = -8107660054882226903L;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
