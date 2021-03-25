package com.boha.receiver.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TomlCurrency implements Serializable {

    @SerializedName("info_server")
    @Expose
    private String infoServer;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("anchor_asset_type")
    @Expose
    private String anchorAssetType;
    @SerializedName("issuer")
    @Expose
    private String issuer;

    public String getInfoServer() {
        return infoServer;
    }

    public void setInfoServer(String infoServer) {
        this.infoServer = infoServer;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAnchorAssetType() {
        return anchorAssetType;
    }

    public void setAnchorAssetType(String anchorAssetType) {
        this.anchorAssetType = anchorAssetType;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
