package com.example.dmitriy.laravelnews.LaravelNewsUnitClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("status_code")
    @Expose
    public Integer statusCode;
    @SerializedName("status_http")
    @Expose
    public Integer statusHttp;

}
