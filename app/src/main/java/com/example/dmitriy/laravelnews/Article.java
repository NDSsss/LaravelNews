package com.example.dmitriy.laravelnews;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Article {
    @PrimaryKey Integer id;
    public String title;
    public String body;
    public String image;
}
/*@Entity
public class Person {
    @PrimaryKey String name;
    int age;
    String favoriteColor;
}*/
