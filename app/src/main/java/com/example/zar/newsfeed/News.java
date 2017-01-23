package com.example.zar.newsfeed;

/**
 * Created by Zar on 12/29/2016.
 */

public class News {
    private String title;

    private String content;
    private String url;

    public News(String title,  String content, String url)
    {
        this.title=title;
        this.content=content;
        this.url=url;

    }

    public String getTitle() {
        return title;
    }



    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

}
