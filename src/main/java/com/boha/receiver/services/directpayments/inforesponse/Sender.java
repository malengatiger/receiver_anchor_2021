package com.boha.receiver.services.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sender implements Serializable {

    @SerializedName("types")
    @Expose
    private Types types;
    private final static long serialVersionUID = -5431741464858647697L;

    public Types getTypes() {
        return types;
    }

    public void setTypes(Types types) {
        this.types = types;
    }

}
