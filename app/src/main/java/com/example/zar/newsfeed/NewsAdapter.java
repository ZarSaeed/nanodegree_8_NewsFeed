package com.example.zar.newsfeed;


import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Zar on 12/29/2016.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> newsArrayList)
    {
        super(context,0,newsArrayList);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(convertView==null)
        {
            view= LayoutInflater.from(getContext()).inflate(R.layout.news_feed_list,parent,false);
        }

        TextView title= (TextView) view.findViewById(R.id.txt_title);
       /* View layout_browser= view.findViewById(R.id.layout_click);*/
        TextView content= (TextView) view.findViewById(R.id.txt_content);

        News news=getItem(position);

        title.setText(news.getTitle());
        content.setText(news.getContent());




        return view;
    }
}
