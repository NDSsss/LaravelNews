package com.example.dmitriy.laravelnews;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

@Entity
public class Article {
    @PrimaryKey Integer id;
    public String title;
    public String body;
    public String image;

    public static class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {

        List<Article> articles;
        OnArticleClickInterface articleClick;

        public ArticleAdapter(List<Article> articles,OnArticleClickInterface onArticleClickInterface)
        {
            this.articles=articles;
            this.articleClick=onArticleClickInterface;
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
            holder.body.setText(article.body);
            Uri uri = Uri.parse("http://176.112.213.150"+article.image);
            holder.simpleDraweeView.setImageURI(uri);
        }

        @Override
        public int getItemCount() {
            return articles.size();
        }


    }

    @Database(entities = {Article.class}, version = 1)
    public abstract static class AppDatabase extends RoomDatabase {
        public abstract ArticleDao articleDao();
    }
}
/*@Entity
public class Person {
    @PrimaryKey String name;
    int age;
    String favoriteColor;
}*/
