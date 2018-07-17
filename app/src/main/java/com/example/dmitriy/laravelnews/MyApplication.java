package com.example.dmitriy.laravelnews;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {
    static AppDatabase db;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        db =  Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, getResources().getString(R.string.DATABASE_NAME)).allowMainThreadQueries().build();
    }
}
