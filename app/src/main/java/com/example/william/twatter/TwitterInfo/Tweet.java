package com.example.william.twatter.TwitterInfo;

/**
 * Created by William on 5/9/2017.
 */

public class Tweet {
    private User user;
    private String authorName = "Unknown";
    private String text = "Empty";
    private String tweetID;
    private String timeOfPost;
    private String url;

    public Tweet(String author, String text, User user, String tweetID, String timeOfPost, String url) {
        this.authorName = author;
        this.text = text+"\n";
        this.user = user;

        this.tweetID = tweetID;
        this.timeOfPost = timeOfPost;
        this.url = url;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser(){return user;}

    public String getTweetID() {
        return tweetID;
    }

    public String getTimeOfPost() {
        return timeOfPost;
    }

    public String getUrl() {
        return url;
    }
}
