package com.p1694151.myapplication.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jaspinderkaur on 07/03/18.
 */

public class Event {
    public Event() {
    }

    @SerializedName("Description")
    private String Description = "";
    @SerializedName("Start_Date")
    private String Start_Date = "";
    @SerializedName("End_Date")
    private String End_Date = "";
    @SerializedName("Start_Time")
    private String Start_Time = "";
    @SerializedName("End_Time")
    private String End_Time = "";
    @SerializedName("Address")
    private String Address = "";
    @SerializedName("City")
    private String City = "";
    @SerializedName("Postal_Code")
    private String Postal_Code = "";
    @SerializedName("Country")
    private String Country = "";

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStart_Date() {
        return Start_Date;
    }

    public void setStart_Date(String start_Date) {
        Start_Date = start_Date;
    }

    public String getEnd_Date() {
        return End_Date;
    }

    public void setEnd_Date(String end_Date) {
        End_Date = end_Date;
    }

    public String getStart_Time() {
        return Start_Time;
    }

    public void setStart_Time(String start_Time) {
        Start_Time = start_Time;
    }

    public String getEnd_Time() {
        return End_Time;
    }

    public void setEnd_Time(String end_Time) {
        End_Time = end_Time;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPostal_Code() {
        return Postal_Code;
    }

    public void setPostal_Code(String postal_Code) {
        Postal_Code = postal_Code;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
