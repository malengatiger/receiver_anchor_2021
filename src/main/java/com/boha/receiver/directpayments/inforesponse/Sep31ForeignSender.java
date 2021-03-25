package com.boha.receiver.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sep31ForeignSender implements Serializable {
    public Sep31ForeignSender(String description) {
        this.description = description;
    }

    @SerializedName("description")
    @Expose
    private String description;
    private final static long serialVersionUID = -6102719134088462119L;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
