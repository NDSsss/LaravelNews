package com.example.dmitriy.laravelnews.LaravelNewsUnitClasses;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.dmitriy.laravelnews.Article;
import com.example.dmitriy.laravelnews.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {

    List<Article> articles;

    public ArticleAdapter(List<Article> articles)
    {
        this.articles=articles;
    }
    public class ArticleHolder extends RecyclerView.ViewHolder{
        public TextView body;
        SimpleDraweeView simpleDraweeView;

        public ArticleHolder(final View itemView) {
            super(itemView);
            body =(TextView) itemView.findViewById(R.id.textViewTitle);
            simpleDraweeView = itemView.findViewById(R.id.my_image_view);
        }


    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View articleView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.atricle_item,parent,false);

        return new ArticleHolder(articleView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        Article article = articles.get(position);
        Log.d("myTag", article.body);
        holder.body.setText(article.body);
        Uri uri = Uri.parse("http://176.112.213.150"+article.image);
        holder.simpleDraweeView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        Log.d("myTag", "getItemCount: "+String.valueOf(articles.size()));
        return articles.size();
    }


}
