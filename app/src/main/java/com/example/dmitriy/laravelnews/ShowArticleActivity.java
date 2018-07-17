package com.example.dmitriy.laravelnews;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowArticleActivity extends AppCompatActivity {

    @BindView(R.id.showArticleSimple)
    SimpleDraweeView simple;
    @BindView(R.id.showArticleTextView)
    TextView textView;
    @BindView(R.id.myToolBar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_article);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if(getIntent()!=null) {
            Article article =(Article) getIntent().getSerializableExtra(MainActivity.ARTICLE_EXTRA);
            textView.setText(article.getBody());
            Uri uri = Uri.parse(MainActivity.IMAGE_BASE_URL+ article.getImage());
            simple.setImageURI(uri);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_article_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
