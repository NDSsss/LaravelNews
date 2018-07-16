package com.example.dmitriy.laravelnews;

import android.arch.persistence.room.Room;
import android.content.Intent;
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
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if(!articles.isEmpty()){
                    adapter = new ArticleAdapter(articles,MainActivity.this);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);}
                else{ makeResponse(); }
            }
        };
        Runnable runnable = new Runnable() {
            public void run() {
                articles=articleDao.getAll();
                handler.sendEmptyMessage(0);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();


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
        intent.putExtra(ARTICLE_EXTRA,articles.get(position));
        intent.putExtra(ARTICLE_BODY,articles.get(position).getBody() );
        intent.putExtra(ARTICLE_IMAGE,articles.get(position).getImage() );
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: ");
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        makeResponse();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void sucsess(Response<LaravelNewsUnit> response) {
        failLinear.setVisibility(View.INVISIBLE);
        Article article = new Article();
        articleDao.deleteAll();
        for (Datum datum:response.body().data) {
            article.setTitle(datum.title);
            article.setImage(datum.image);
            article.setBody(datum.body);
            article.setId(datum.id);
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
    public void fail(Throwable t) {
        progressBar.setVisibility(View.INVISIBLE);
        failLinear.setVisibility(View.VISIBLE);
        failTextView.setText(t.getLocalizedMessage());

    }
}
