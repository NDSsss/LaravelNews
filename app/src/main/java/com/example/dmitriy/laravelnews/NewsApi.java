package com.example.dmitriy.laravelnews;

import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.LaravelNewsUnit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsApi {
    @GET("news")
    Call<LaravelNewsUnit> getNews();
}
