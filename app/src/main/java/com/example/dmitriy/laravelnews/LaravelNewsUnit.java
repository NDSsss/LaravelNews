package com.example.dmitriy.laravelnews;

import java.util.List;

import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.Datum;
import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.Status;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LaravelNewsUnit {

    @SerializedName("data")
    @Expose
    public List<Datum> data = null;
    @SerializedName("status")
    @Expose
    public Status status;

}
