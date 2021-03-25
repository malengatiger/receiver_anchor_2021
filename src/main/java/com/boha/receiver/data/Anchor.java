package com.boha.receiver.data;

public class Anchor {
    String anchorId, name, cellphone, email;
    StellarAccount baseStellarAccount, issuingStellarAccount, distributionStellarAccount;
    String date;

    public Anchor() {
    }

    public Anchor(String anchorId, String name, String cellphone, String email, StellarAccount baseStellarAccount, StellarAccount issuingStellarAccount, StellarAccount distributionStellarAccount, String date) {
        this.anchorId = anchorId;
        this.name = name;
        this.cellphone = cellphone;
        this.email = email;
        this.baseStellarAccount = baseStellarAccount;
        this.issuingStellarAccount = issuingStellarAccount;
        this.distributionStellarAccount = distributionStellarAccount;
        this.date = date;
    }

    public String getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(String anchorId) {
        this.anchorId = anchorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StellarAccount getBaseStellarAccount() {
        return baseStellarAccount;
    }

    public void setBaseStellarAccount(StellarAccount baseStellarAccount) {
        this.baseStellarAccount = baseStellarAccount;
    }

    public StellarAccount getIssuingStellarAccount() {
        return issuingStellarAccount;
    }

    public void setIssuingStellarAccount(StellarAccount issuingStellarAccount) {
        this.issuingStellarAccount = issuingStellarAccount;
    }

    public StellarAccount getDistributionStellarAccount() {
        return distributionStellarAccount;
    }

    public void setDistributionStellarAccount(StellarAccount distributionStellarAccount) {
        this.distributionStellarAccount = distributionStellarAccount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
