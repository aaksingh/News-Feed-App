package com.example.martian.newsfeed;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by martian on 22/1/17.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context,ArrayList<News> news){
       super(context,0,news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View listItemView=convertView;
        if(convertView==null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_layout,parent,false);
        }
        News currentNews = getItem(position);
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentNews.getTitle());

        TextView date = (TextView) listItemView.findViewById(R.id.date);
        date.setText(currentNews.getDate());

        return listItemView;
    }
}
