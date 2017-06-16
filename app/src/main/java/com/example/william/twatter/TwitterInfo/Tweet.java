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
    private String userID;
    private String tweetID;

    public Tweet(String author,String text, User user, String userID, String tweetID) {
        this.authorName = author;
        this.text = text+"\n";
        this.user = user;
        this.userID = userID;
        this.tweetID = tweetID;
    }



    public User getUser(){return user;}

    public String getUserID() {
        return userID;
    }

    public String getTweetID() {
        return tweetID;
    }
}
