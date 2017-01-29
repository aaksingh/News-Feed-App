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
        Rnews=new ArrayList<News>();

        Log.i("Message","newsAdapter");
        NewsAdapter adapter = new NewsAdapter(this, Rnews);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        Log.i("Message","newsAdapter1");


         Log.i("Message","Not able to call");
         NewsFeedAsyncTask NewsSync=new NewsFeedAsyncTask();
         NewsSync.execute();

    }


    private class NewsFeedAsyncTask extends AsyncTask<URL,Void,List<News>>{

        @Override
        protected List<News> doInBackground(URL... urls) {
            Log.i("MESSAGE","In do in background");
            URL url=createURL(Rss_url);
            Log.i("MESSAGE",Rss_url);
            InputStream in=null;
            ArrayList<News> news=null;


            try {
                Log.i("MESSAGE","In try block");
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
                Log.i("MESSAGE","Before while loop");
                while (eventType!=XmlPullParser.END_DOCUMENT) {   Log.i("MESSAGE", "In while loop");

                    if (eventType == Xpp.START_TAG) {
                       // Log.i("MESSAGE", "In START_TAG");
                    }
                    Log.i("MESSAGE","After START_TAG loop");
                    if (Xpp.getName().equals("item")){
                        insideItem = true;
                        //Log.i("MESSAGE", "In Item Tag");
                        //Log.i("MESSAGE", "Hello");
                    }
                    //Log.i("MESSAGE","After item");
                    else if (Xpp.getName().equals("title")) {
                       // Log.i("MESSAGE", "Almost In title tag");
                        if (insideItem) {
                          //  Log.i("MESSAGE", "In title tag");
                                //Log.i("Title is ", Xpp.nextText());
                                //title = Xpp.nextText();
                        }
                    }
                   // Log.i("MESSAGE","After title");
                    else if (Xpp.getName().equals("pubDate")) {
                         //      Log.i("MESSAGE", "Almost In pubdate tag");
                        if (insideItem) {
                           // Log.i("MESSAGE", "In pubdate");
                            //Log.i("Publish Date is.", Xpp.nextText());
                            //date = Xpp.nextText();
                        }
                    }
                    Log.i("MESSAGE","After pubDate");
                    /*Rnews=new ArrayList<News>();
                    Rnews.add(new News(title,date));*/
                    insideItem = false;
                    eventType=Xpp.next();
                }Log.i("Message is ","After while loop");

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
            Log.i("MESSAGE","In createurl");
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

