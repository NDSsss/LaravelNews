package com.example.dmitriy.laravelnews;

import android.arch.persistence.room.Room;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.AppDatabase;
import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.ArticleAdapter;
import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.Datum;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    AppDatabase db;
    ArticleDao articleDao;
    DatumDao datumDao;
    ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        db =  Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database").allowMainThreadQueries().build();
        articleDao = db.articleDao();
        makeResponse();
        List<Article> articles=articleDao.getAll();
        Log.d("myTag", "onCreate: ");
        adapter = new ArticleAdapter(articles);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }
    void makeResponse() {
        articleDao.deleteAll();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://176.112.213.150/api_v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NewsApi newsApi = retrofit.create(NewsApi.class);
        Call<LaravelNewsUnit> messages = newsApi.getNews();
        messages.enqueue(new Callback<LaravelNewsUnit>() {
            @Override
            public void onResponse(Call<LaravelNewsUnit> call, Response<LaravelNewsUnit> response) {

                Article article = new Article();
                for (Datum datum:response.body().data) {
                    article.title=datum.title;
                    article.image=datum.image;
                    article.body=datum.body;
                    article.id=datum.id;
                    articleDao.insert(article);
                }

            }

            @Override
            public void onFailure(Call<LaravelNewsUnit> call, Throwable t) {

            }
        });
    }
}
