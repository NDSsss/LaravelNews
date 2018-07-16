package com.example.dmitriy.laravelnews;

import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.LaravelNewsUnit;

import retrofit2.Response;

public interface NewsInterface {
    void sucsess(Response<LaravelNewsUnit> response);
    void fail(Throwable t);
}
