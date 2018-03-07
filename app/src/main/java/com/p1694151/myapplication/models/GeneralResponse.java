package com.p1694151.myapplication.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by paalwinder on 23/02/18.
 */

public class GeneralResponse {
    public GeneralResponse() {
    }

    @SerializedName("Status")
    private String status = "";

    @SerializedName("Timestamp")
    private long timestamp = 0;

    @SerializedName("Message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
