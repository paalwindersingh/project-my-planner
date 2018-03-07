package com.p1694151.myapplication.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by paalwinder on 06/03/18.
 */

public class User {
    public User() {
    }

    @SerializedName("firstname")
    private String firstname = "";

    @SerializedName("lastname")
    private String lastname = "";

    @SerializedName("dob")
    private String dob = "";

    @SerializedName("gender")
    private String gender = "";

    @SerializedName("email")
    private String email = "";

    @SerializedName("password")
    private String password = "";

    @SerializedName("phone")
    private String phone = "";

    @SerializedName("message")
    private String message = "";

    @SerializedName("status")
    private String status = "";

    @SerializedName("timestamp")
    private String timestamp = "";

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
