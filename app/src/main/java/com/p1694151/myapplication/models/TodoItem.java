package com.p1694151.myapplication.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paalwinder on 07/03/18.
 */

public class TodoItem {

    public TodoItem() {

    }
    public TodoItem(int task_id, String title, String notes) {
        this.task_id = task_id;
        this.title = title;
        this.notes = notes;

    }

    @SerializedName("task_id")
    private int task_id = 0;
    @SerializedName("title")
    private String title = "";
    @SerializedName("notes")
    private String notes = "";

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
