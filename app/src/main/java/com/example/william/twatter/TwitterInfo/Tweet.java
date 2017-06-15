package com.example.william.twatter.TwitterInfo;

/**
 * Created by William on 5/9/2017.
 */

public class Tweet {
    public String getAuthorName() {
        return authorName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    private User user;
    private String authorName = "Unknown";
    private String text = "Empty";
    public Tweet(String author,String text, User user) {
        this.authorName = author;
        this.text = text;
        this.user = user;
    }


    public void setAuthorName(String name) {
        authorName = name;
    }
    public User getUser(){return user;}
}
