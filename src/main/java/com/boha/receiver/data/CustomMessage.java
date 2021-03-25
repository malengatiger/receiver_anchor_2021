package com.boha.receiver.data;

import org.joda.time.DateTime;

public class CustomMessage {
    int statusCode;
    String message, date;

    public CustomMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.date = new DateTime().toDateTimeISO().toString();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
