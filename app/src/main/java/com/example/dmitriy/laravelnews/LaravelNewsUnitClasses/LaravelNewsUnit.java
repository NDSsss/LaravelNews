package com.example.dmitriy.laravelnews.LaravelNewsUnitClasses;

import java.util.List;

import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.Datum;
import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.Status;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LaravelNewsUnit {
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("status")
    @Expose
    private Status status;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
