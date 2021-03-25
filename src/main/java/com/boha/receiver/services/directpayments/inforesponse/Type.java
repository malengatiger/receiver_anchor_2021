package com.boha.receiver.services.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Type implements Serializable {
    public Type(String description, List<String> choices) {
        this.description = description;
        this.choices = choices;
    }

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("choices")
    @Expose
    private List<String> choices = new ArrayList<String>();
    private final static long serialVersionUID = -9127402012505051051L;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

}
