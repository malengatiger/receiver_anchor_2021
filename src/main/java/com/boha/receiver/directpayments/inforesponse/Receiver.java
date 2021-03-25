package com.boha.receiver.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Receiver implements Serializable {

    @SerializedName("types")
    @Expose
    private Types_ types;
    private final static long serialVersionUID = -4686051401213998797L;

    public Types_ getTypes() {
        return types;
    }

    public void setTypes(Types_ types) {
        this.types = types;
    }

}
