package com.example.dmitriy.laravelnews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("news")
    Call<LaravelNewsUnit> getNews();
}
