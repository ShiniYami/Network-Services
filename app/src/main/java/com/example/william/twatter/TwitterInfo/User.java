package com.example.william.twatter.TwitterInfo;

import java.util.ArrayList;

/**
 * Created by William on 5/9/2017.
 */

public class User {
    private String name;
    private String location;
    private int age;
    private String description;
    private String profile_image_url;
    private String screenName;

    public User(String name,String location,String description, String screenName){
        this.name = name;
        this.location = location;
        this.description = description;
        this.screenName = screenName;
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

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getScreenName() {
        return screenName;
    }
}
