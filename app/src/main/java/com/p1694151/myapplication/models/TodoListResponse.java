package com.p1694151.myapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by paalwinder on 11/03/18.
 */

public class TodoListResponse {
    public TodoListResponse() {
    }

    @SerializedName("Status")
    private String status = "";

    @SerializedName("Timestamp")
    private long timestamp = 0;

    @SerializedName("Message")
    private String message;

    @SerializedName("Todo_List")
    private ArrayList<TodoItem> todoList;

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

    public ArrayList<TodoItem> getTodoList() {
        return todoList;
    }

    public void setTodoList(ArrayList<TodoItem> todoList) {
        this.todoList = todoList;
    }
}
