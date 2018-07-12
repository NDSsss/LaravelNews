package com.example.dmitriy.laravelnews.LaravelNewsUnitClasses;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.dmitriy.laravelnews.Article;
import com.example.dmitriy.laravelnews.ArticleDao;
import com.example.dmitriy.laravelnews.DatumDao;

@Database(entities = {Article.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArticleDao articleDao();
}
