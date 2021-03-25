package com.boha.receiver.services.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sep31Sender implements Serializable {

    public Sep31Sender(String description) {
        this.description = description;
    }

    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = 595313215410609287L;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
