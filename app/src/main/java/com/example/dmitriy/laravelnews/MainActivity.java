package com.example.dmitriy.laravelnews;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
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
import com.example.dmitriy.laravelnews.LaravelNewsUnitClasses.LaravelNewsUnit;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnArticleClickInterface,
        SwipeRefreshLayout.OnRefreshListener,NewsInterface{

    public static final String ARTICLE_BODY="articleBody",ARTICLE_IMAGE="articleImage",ARTICLE_EXTRA="articleExtra";

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

    AppDatabase db;
    ArticleDao articleDao;
    ArticleAdapter adapter;
    String TAG ="myTag";
    List<Article> articles;
    Boolean firtstTimeAdapter =true;
    NewsHelper newsHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        db =  Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database").allowMainThreadQueries().build();
        articleDao = db.articleDao();
        newsHelper= new NewsHelper(this);
        fillRecyclerView();
    }
    void makeResponse() {
        failLinear.setVisibility(View.INVISIBLE);
        newsHelper.getNews();
    }
    void fillRecyclerView()
    {
        articles=articleDao.getAll();
        Log.d(TAG, "fillRecyclerView: "+articles.isEmpty());
        ReadFromDb readFromDb = new ReadFromDb();
        readFromDb.execute();
    }

    @Override
    public void onClick(View view) {
        failLinear.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setEnabled(false);
        fillRecyclerView();

    }

    @Override
    public void onArticleClick(View view,int position) {
        Log.d(TAG, "onArticleClick: "+String.valueOf(position));
        Intent intent = new Intent(this, ShowArticleActivity.class);
        intent.putExtra(ARTICLE_EXTRA,articles.get(position));
        intent.putExtra(ARTICLE_BODY,articles.get(position).getBody() );
        intent.putExtra(ARTICLE_IMAGE,articles.get(position).getImage() );
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: ");
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(false);
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        makeResponse();

    }

    @Override
    public void sucsess(Response<LaravelNewsUnit> response) {
        failLinear.setVisibility(View.INVISIBLE);
        articleDao.deleteAll();
        InsertInDb insertInDb = new InsertInDb();
        insertInDb.execute(response);
    }

    @Override
    public void fail(Throwable t) {
        progressBar.setVisibility(View.INVISIBLE);
        failLinear.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setEnabled(true);
        failTextView.setText(t.getLocalizedMessage());

    }
    private class ReadFromDb extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {
            articles=articleDao.getAll();
            Log.d(TAG, "doInBackground: load "+articles.isEmpty());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: load"+articles.isEmpty());
            if(!articles.isEmpty()){
                adapter = new ArticleAdapter(articles,MainActivity.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                Log.d(TAG, "onPostExecute: adapter setted");
                recyclerView.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);}
            else{ makeResponse(); }
        }
    }
    private class InsertInDb extends AsyncTask<Response<LaravelNewsUnit>,Void,Void>{

        @Override
        protected Void doInBackground(Response<LaravelNewsUnit>... responses) {
            for(Response<LaravelNewsUnit> response:responses) {
                Article article= new Article();
                for (Datum datum : response.body().getData()) {
                    article.setTitle(datum.getTitle());
                    Log.d(TAG, "doInBackground: insert"+article.title);
                    article.setImage(datum.getImage());
                    article.setBody(datum.getBody());
                    article.setId(datum.getId());
                    articleDao.insert(article);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute1: "+firtstTimeAdapter);
            if (firtstTimeAdapter){
                Log.d(TAG, "onPostExecute: true entered");
                fillRecyclerView();
                firtstTimeAdapter=false;}
            else{
                Log.d(TAG, "onPostExecute: false entered");
                recyclerView.getAdapter().notifyDataSetChanged();
            }
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            swipeRefreshLayout.setEnabled(true);
        }
    }
}
