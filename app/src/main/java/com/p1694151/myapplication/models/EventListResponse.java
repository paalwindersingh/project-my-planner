package com.p1694151.myapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by paalwinder on 11/03/18.
 */

public class EventListResponse {
    public EventListResponse() {
    }

    @SerializedName("Status")
    private String status = "";

    @SerializedName("Timestamp")
    private long timestamp = 0;

    @SerializedName("Message")
    private String message;

    @SerializedName("Event_List")
    private ArrayList<Event> eventList;

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

    public ArrayList<Event> getEventList() {
        return eventList;
    }

    public void setEventList(ArrayList<Event> eventList) {
        this.eventList = eventList;
    }
}
