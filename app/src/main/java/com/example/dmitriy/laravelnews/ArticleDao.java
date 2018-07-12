package com.example.dmitriy.laravelnews;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM article")
    List<Article> getAll();
    @Insert
    void insert(Article article);

    @Insert
    void insert(List<Article> employees);

    @Update
    void update(Article article);

    @Delete
    void delete(Article article);
    @Query("DELETE FROM article")
    void deleteAll();

}
/*@Dao
public interface EmployeeDao {

   @Query("SELECT * FROM employee")
   List<Employee> getAll();

   @Query("SELECT * FROM employee WHERE id = :id")
   Employee getById(long id);

   @Insert
   void insert(Employee employee);

   @Update
   void update(Employee employee);

   @Delete
   void delete(Employee employee);

}*/
