package com.example.zar.newsfeed;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<News> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.list_item);
        NewsAsynTask asynTask=new NewsAsynTask();
        String url="https://ajax.googleapis.com/ajax/services/feed/find?v=1.0&q=dance";
        asynTask.execute(url);
    }

    private String httpCall(String urlInput)
    {

        try {
            URL url=new URL(urlInput);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            InputStream is=httpURLConnection.getInputStream();
            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader br=new BufferedReader(isr);

            String data="";
            while (true)
            {
                String temp=br.readLine();
                if(temp==null)
                {
                    break;
                }
                data +=temp;
            }

            return data;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;

    }


    public ArrayList<News> convertJasonToNews(String jason)
    {
        arrayList=new ArrayList<News>();
        try {
            JSONObject jsonObject=new JSONObject(jason);
            JSONObject response=jsonObject.getJSONObject("responseData");
            JSONArray entries=response.getJSONArray("entries");

            for (int i=0; i<entries.length(); i++)
            {
                JSONObject arrayIndex=entries.getJSONObject(i);
                String title=arrayIndex.getString("title");
                title=title.replaceAll("[<b>\\</b>]","");
                String content=arrayIndex.getString("contentSnippet");
                content=content.replaceAll("[<b>\\<b>]","");
                String url=arrayIndex.getString("link");

                arrayList.add(new News(title,content,url));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return  arrayList;
    }

    public  class NewsAsynTask extends AsyncTask<String,Void,ArrayList<News>>
    {

        @Override
        protected ArrayList<News> doInBackground(String... params) {
            String jason=httpCall(params[0]);
            arrayList=convertJasonToNews(jason);
            return arrayList;
        }

        @Override
        protected void onPostExecute(final ArrayList<News> newsArrayList) {
        final NewsAdapter newsAdapter=new NewsAdapter(MainActivity.this,newsArrayList);
            listView.setAdapter(newsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    News news=newsArrayList.get(position);
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(news.getUrl()));
                    startActivity(intent);
                }
            });

        }
    }


}
