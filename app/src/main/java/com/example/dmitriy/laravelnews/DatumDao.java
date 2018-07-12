package com.example.dmitriy.laravelnews;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.Datum;

import java.util.List;
@Dao
public interface DatumDao {
    @Query("SELECT * FROM Datum")
    List<Datum> getAll();
    @Insert
    void insert(Datum datum);

    @Insert
    void insert(List<Datum> datumList);

    @Update
    void update(Datum datum);

    @Delete
    void delete(Datum datum);
}
