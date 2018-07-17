package com.example.dmitriy.laravelnews.LaravelNewsUnitClasses;

import java.util.ArrayList;
import java.util.List;

import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.NewsData;
import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.Status;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LaravelNewsUnit {
    @SerializedName("data")
    @Expose
    private ArrayList<NewsData> data = null;
    @SerializedName("status")
    @Expose
    private Status status;

    public ArrayList<NewsData> getData() {
        return data;
    }

    public void setData(ArrayList<NewsData> data) {
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
