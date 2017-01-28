package com.example.martian.newsfeed;

/**
 * Created by martian on 22/1/17.
 */

public class News {
    private String title,date;
    public News(String mtitle,String mdate){
           title=mtitle;
           date=mdate;
    }

    public String getTitle() {
        return title;
    }




    public String getDate() {
        return date;
    }

    /*@Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", section='" + section + '\'' +
                '}';
    }*/
}


