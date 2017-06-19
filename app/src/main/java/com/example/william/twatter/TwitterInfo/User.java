package com.example.william.twatter.TwitterInfo;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.william.twatter.OAuthHandler;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by William on 5/9/2017.
 */

public class User {

    OAuthHandler handler = OAuthHandler.getInstance();

    private String name;
    private String location;
    private int age;
    private String description;
    private String url;
    private String screenName;
    private String backgroundColor;
    private String ID;
    private boolean followed;
    private boolean followRequestSent;

    public User(String name, String location, String description, String screenName, String url, String backgroundColor, String ID,boolean followed, boolean followRequestSent){
        this.name = name;
        this.location = location;
        this.description = description;
        this.screenName = screenName;
        this.url = url;
        this.backgroundColor = backgroundColor;
        this.ID = ID;
        this.followed = followed;
        this.followRequestSent = followRequestSent;
    }
    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getAge() {
        return age;
    }

    public String getDescription() {
        return description;
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

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
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
