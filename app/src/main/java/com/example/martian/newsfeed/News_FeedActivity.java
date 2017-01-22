package com.example.martian.newsfeed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class News_FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__feed);


        ArrayList<News> news = new ArrayList<News>();

        news.add(new News("Aakash", "Aakash", "Aakash", "Aakash", "Aakash"));

        NewsAdapter item = new NewsAdapter(this, news);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(item);
    }
}