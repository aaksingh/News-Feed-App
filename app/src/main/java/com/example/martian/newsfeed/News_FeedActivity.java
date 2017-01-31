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

    private static final String Rss_url="http://www.pcworld.com/index.rss";
    private String title,date;
    private ArrayList<News> Rnews;
    private String  itemcontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__feed);
        Rnews=new ArrayList<News>();

        Log.i("Message","newsAdapter");
        NewsAdapter adapter = new NewsAdapter(this, Rnews);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        Log.i("Message","newsAdapter1");


         Log.i("Message","Not able to call");
         NewsFeedAsyncTask NewsSync=new NewsFeedAsyncTask();
         NewsSync.execute(Rss_url);

    }


    private class NewsFeedAsyncTask extends AsyncTask<String,Void,List<News>>{


        protected List<News> doInBackground(String... params){

            String strUrl=params[0];
            InputStream stream=null;
            ArrayList<News> Newsdata=new ArrayList<News>();
            try{
                URL url=new URL(strUrl);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setReadTimeout(10 * 1000);
                connection.setConnectTimeout(10 * 1000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();
                int response = connection.getResponseCode();
                Log.d("debug","The Response is " + response);
                stream=connection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(stream,null);
                int eventType = xpp.getEventType();
                News newData = null;

                while (eventType!=XmlPullParser.END_DOCUMENT){

                    if(eventType==XmlPullParser.START_TAG){

                        Log.i("Start Document is : ",""+xpp.getName());
                    }

                    else if(eventType==XmlPullParser.TEXT){

                        Log.i("The content of TEXT is ",""+itemcontent);
                    }

                    else if(eventType==XmlPullParser.END_TAG){
                        Log.i("Message","End Tag");
                        if (xpp.getName().equals("item")){
                            Log.i("item ","is "+xpp.getName());

                        }
                        else if (xpp.getName().equals("title")){
                            Log.i("Title ","is"+xpp.getName());
                            //itemcontent=xpp.getAttributeValue(null,"title");
                            itemcontent=xpp.getText();
                            Log.i("The"," "+itemcontent);
                        }
                        else if (xpp.getName().equals("link")){
                            Log.i("Link is","is"+xpp.getName());
                        }
                        else if (xpp.getName().equals("pubdate")){
                            Log.i("pubDate","is"+xpp.getName());
                        }
                    }
                    eventType=xpp.next();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            return Newsdata;
        }
    }
}

