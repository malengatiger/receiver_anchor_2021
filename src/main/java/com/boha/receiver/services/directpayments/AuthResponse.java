package com.boha.receiver.services.directpayments;

public class AuthResponse {

    String transaction, networkPassphrase;

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getNetworkPassphrase() {
        return networkPassphrase;
    }

    public void setNetworkPassphrase(String networkPassphrase) {
        this.networkPassphrase = networkPassphrase;
    }
}
