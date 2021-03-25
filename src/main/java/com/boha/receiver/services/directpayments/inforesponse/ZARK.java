package com.boha.receiver.services.directpayments.inforesponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ZARK implements Serializable
{

    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("fee_fixed")
    @Expose
    private Double feeFixed;
    @SerializedName("fee_percent")
    @Expose
    private Double feePercent;
    @SerializedName("min_amount")
    @Expose
    private Double minAmount;
    @SerializedName("max_amount")
    @Expose
    private Double maxAmount;
    @SerializedName("sep12")
    @Expose
    private Sep12 sep12;
    @SerializedName("fields")
    @Expose
    private Fields fields;
    private final static long serialVersionUID = 5012782415428325392L;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Double getFeeFixed() {
        return feeFixed;
    }

    public void setFeeFixed(Double feeFixed) {
        this.feeFixed = feeFixed;
    }

    public Double getFeePercent() {
        return feePercent;
    }

    public void setFeePercent(Double feePercent) {
        this.feePercent = feePercent;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Sep12 getSep12() {
        return sep12;
    }

    public void setSep12(Sep12 sep12) {
        this.sep12 = sep12;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

}
