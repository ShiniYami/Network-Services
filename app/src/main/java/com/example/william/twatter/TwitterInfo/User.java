package com.example.william.twatter.TwitterInfo;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.william.twatter.OAuthHandler;

import java.util.ArrayList;

/**
 * Created by William on 5/9/2017.
 */

public class User {

    OAuthHandler handler = OAuthHandler.getInstance();

    private String name;
    private String location;
    private int age;
    private String description;
    private Bitmap profile_image_Bitmap;
    private String screenName;

    public User(String name,String location,String description, String screenName, Bitmap profile_image_Bitmap){
        this.name = name;
        this.location = location;
        this.description = description;
        this.screenName = screenName;
        this.profile_image_Bitmap = profile_image_Bitmap;
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

    public Bitmap getProfile_image_Bitmap() {
        return profile_image_Bitmap;
    }

    public void setProfile_image_Bitmap(Bitmap profile_image_Bitmap) {
        this.profile_image_Bitmap = profile_image_Bitmap;
    }

    public String getScreenName() {
        return screenName;
    }
}
