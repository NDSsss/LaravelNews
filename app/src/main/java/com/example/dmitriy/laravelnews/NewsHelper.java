package com.example.dmitriy.laravelnews;

import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.LaravelNewsUnit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsHelper implements Callback<LaravelNewsUnit>{
    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .build();
    private Call<LaravelNewsUnit> messages;
    private Retrofit retrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://176.112.213.150/api_v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private NewsApi newsApi = retrofit.create(NewsApi.class);
    private NewsInterface newsInterface;
    public NewsHelper(NewsInterface newsInterface) {
        this.newsInterface=newsInterface;
        messages = newsApi.getNews();
    }
    void getNews()
    {
        messages.clone().enqueue(this);
    }

    @Override
    public void onResponse(Call<LaravelNewsUnit> call, Response<LaravelNewsUnit> response) {
        newsInterface.sucsess(response);
    }

    @Override
    public void onFailure(Call<LaravelNewsUnit> call, Throwable t) {
        newsInterface.fail(t);
    }
}
