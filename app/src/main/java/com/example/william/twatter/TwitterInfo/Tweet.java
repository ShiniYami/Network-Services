package com.example.william.twatter.TwitterInfo;

/**
 * This class is used to hold all of the necessary data that you get from parsing the JSON when the GET request is sent to twitter
 */

public class Tweet {
    private User user;
    private String authorName = "Unknown";
    private String text = "Empty";

    private String timeOfPost;
    private String url;

    public Tweet(String author, String text, User user, String timeOfPost, String url) {
        this.authorName = author;
        this.text = text+"\n";
        this.user = user;

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

    public String getTimeOfPost() {
        return timeOfPost;
    }

    public String getUrl() {
        return url;
    }
}
