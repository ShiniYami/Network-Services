package com.example.william.twatter.TwitterInfo;

/**
 * Created by William on 5/9/2017.
 */

public class User {


    private String name;
    private String location;
    private int age;
    private String description;
    private String url;
    private String screenName;
    private String ID;
    private boolean followed;
    private boolean followRequestSent;

    public User(String name, String location, String description, String screenName, String url, String ID, boolean followed, boolean followRequestSent) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.screenName = screenName;
        this.url = url;
        this.ID = ID;
        this.followed = followed;
        this.followRequestSent = followRequestSent;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getID() {
        return ID;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    public boolean isFollowRequestSent() {
        return followRequestSent;
    }

    public void setFollowRequestSent(boolean followRequestSent) {
        this.followRequestSent = followRequestSent;
    }
}
