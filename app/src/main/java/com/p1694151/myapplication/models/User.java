package com.p1694151.myapplication.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by paalwinder on 06/03/18.
 */

public class User {
    public User() {
    }
    @SerializedName("userid")
    private String userid = "";

    @SerializedName("FirstName")
    private String firstname = "";

    @SerializedName("LastName")
    private String lastname = "";

    @SerializedName("DOB")
    private String dob = "";

    @SerializedName("Gender")
    private String gender = "";

    @SerializedName("Email")
    private String email = "";

    @SerializedName("password")
    private String password = "";

    @SerializedName("Phone")
    private String phone = "";

    @SerializedName("Message")
    private String message = "";

    @SerializedName("Status")
    private String status = "";

    @SerializedName("Timestamp")
    private String timestamp = "";

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

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
