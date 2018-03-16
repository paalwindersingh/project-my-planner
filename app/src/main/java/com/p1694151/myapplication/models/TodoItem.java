package com.p1694151.myapplication.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paalwinder on 07/03/18.
 */

public class TodoItem implements Parcelable{

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

    protected TodoItem(Parcel in) {
        task_id = in.readInt();
        title = in.readString();
        notes = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(task_id);
        dest.writeString(title);
        dest.writeString(notes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TodoItem> CREATOR = new Creator<TodoItem>() {
        @Override
        public TodoItem createFromParcel(Parcel in) {
            return new TodoItem(in);
        }

        @Override
        public TodoItem[] newArray(int size) {
            return new TodoItem[size];
        }
    };

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
