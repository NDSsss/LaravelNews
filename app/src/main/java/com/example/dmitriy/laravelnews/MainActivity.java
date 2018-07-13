package com.example.dmitriy.laravelnews;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.Datum;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnArticleClickInterface,SwipeRefreshLayout.OnRefreshListener{

    public static final String ARTICLE_BODY="articleBody",ARTICLE_IMAGE="articleImage";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.failLinear)
    LinearLayout failLinear;
    @BindView(R.id.failTextView)
    TextView failTextView;
    @BindView(R.id.failButton)
    Button failButton;

    Article.AppDatabase db;
    ArticleDao articleDao;
    DatumDao datumDao;
    Article.ArticleAdapter adapter;
    String TAG ="myTag";
    List<Article> articles;
    Boolean firtstTimeAdapter =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        db =  Room.databaseBuilder(getApplicationContext(),
                Article.AppDatabase.class, "database").allowMainThreadQueries().build();
        articleDao = db.articleDao();


        fillRecyclerView();


    }
    void makeResponse() {
        failLinear.setVisibility(View.INVISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://176.112.213.150/api_v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NewsApi newsApi = retrofit.create(NewsApi.class);
        Call<LaravelNewsUnit> messages = newsApi.getNews();
        messages.enqueue(new Callback<LaravelNewsUnit>() {
            @Override
            public void onResponse(Call<LaravelNewsUnit> call, Response<LaravelNewsUnit> response) {
                failLinear.setVisibility(View.INVISIBLE);
                Article article = new Article();
                articleDao.deleteAll();
                for (Datum datum:response.body().data) {
                    article.title=datum.title;
                    article.image=datum.image;
                    article.body=datum.body;
                    article.id=datum.id;
                    articleDao.insert(article);
                }
                if (firtstTimeAdapter){
                fillRecyclerView();
                firtstTimeAdapter=false;}
                else{
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<LaravelNewsUnit> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                failLinear.setVisibility(View.VISIBLE);
                failTextView.setText(t.getLocalizedMessage());

            }
        });
    }
    void fillRecyclerView()
    {
        articles=articleDao.getAll();
        adapter = new Article.ArticleAdapter(articles,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Log.d(TAG, "fillRecyclerView: ");
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        failLinear.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        fillRecyclerView();
        
    }

    @Override
    public void onArticleClick(View view,int position) {
        Log.d(TAG, "onArticleClick: "+String.valueOf(position));
        Intent intent = new Intent(this, ShowArticleActivity.class);
        intent.putExtra(ARTICLE_BODY,articles.get(position).body );
        intent.putExtra(ARTICLE_IMAGE,articles.get(position).image );
        startActivity(intent);
        //startActivityForResult(intent, ADD_REQUEST);
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: ");
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        makeResponse();
        swipeRefreshLayout.setRefreshing(false);
    }
}
