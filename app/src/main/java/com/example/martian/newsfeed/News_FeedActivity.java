package com.example.martian.newsfeed;

import android.app.usage.UsageEvents;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLog;
import android.util.Log;
import android.util.Xml;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News_FeedActivity extends AppCompatActivity {
    public static final String LOG_TAG = News_FeedActivity.class.getSimpleName();

    private static final String Rss_url="http://timesofindia.indiatimes.com/rssfeedstopstories.cms";
    private String title,date;
    private ArrayList<News> Rnews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__feed);

        Rnews = new  ArrayList<News>();



        NewsAdapter adapter = new NewsAdapter(this, Rnews);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);


         Log.i("Message","Not able to call");
         NewsFeedAsyncTask NewsSync=new NewsFeedAsyncTask();
         NewsSync.execute();

    }


    private class NewsFeedAsyncTask extends AsyncTask<URL,Void,List<News>>{

        @Override
        protected List<News> doInBackground(URL... urls) {
            Log.i("MESSAGE","In do in background");
            URL url=createURL(Rss_url);
            InputStream in=null;
            ArrayList<News> news=null;


            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10 * 1000);
                connection.setConnectTimeout(10 * 1000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                in=connection.getInputStream();

                XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser Xpp=factory.newPullParser();

                Xpp.setInput(in,null);

                 boolean insideItem=false;
                int eventType=Xpp.getEventType();
                //Start the while loop
                while (eventType!=XmlPullParser.START_DOCUMENT){
                    if(eventType==XmlPullParser.START_TAG){
                        if(Xpp.getName().equalsIgnoreCase("item")){
                            insideItem=true;
                        }else if(Xpp.getName().equalsIgnoreCase("title")){
                            if(insideItem) {
                                Log.i("Title is ",Xpp.nextText());
                            }
                        }else if(Xpp.getName().equalsIgnoreCase("pubDate")){
                            if (insideItem) {
                                Log.i("Publish Date is.", Xpp.nextText());
                            }
                        }
                    }
                    insideItem = false;

                    eventType=Xpp.next();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (XmlPullParserException e) {

            }
            return news;
        }
        private URL createURL(String rss_url) {
            URL url = null;
            try {
                url = new URL(rss_url);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }
    }
}

