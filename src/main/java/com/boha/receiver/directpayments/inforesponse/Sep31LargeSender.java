package com.boha.receiver.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sep31LargeSender implements Serializable {
    public Sep31LargeSender(String description) {
        this.description = description;
    }

    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = 1574639141882117063L;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
