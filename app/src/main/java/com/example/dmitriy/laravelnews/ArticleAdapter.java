package com.example.dmitriy.laravelnews;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {

    List<Article> articles;
    OnArticleClickInterface articleClick;
    String baseUri;

    public ArticleAdapter(List<Article> articles,OnArticleClickInterface onArticleClickInterface,String baseUri)
    {
        this.articles=articles;
        this.articleClick=onArticleClickInterface;
        this.baseUri=baseUri;
    }
    public class ArticleHolder extends RecyclerView.ViewHolder{
        public TextView body;
        SimpleDraweeView simpleDraweeView;
        LinearLayout linearLayout;

        public ArticleHolder(final View itemView) {
            super(itemView);
            body =(TextView) itemView.findViewById(R.id.textViewTitle);
            simpleDraweeView = itemView.findViewById(R.id.my_image_view);
            linearLayout = itemView.findViewById(R.id.article_lianer);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    articleClick.onArticleClick(itemView,getAdapterPosition());
                }
            });
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
        holder.body.setText(article.getBody());
        Uri uri = Uri.parse(baseUri+article.getImage());
        holder.simpleDraweeView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


}