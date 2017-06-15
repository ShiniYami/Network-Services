package com.example.william.twatter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

import com.example.william.twatter.TwitterInfo.Tweet;
import com.example.william.twatter.TwitterInfo.User;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by William on 5/9/2017.
 */

class TweetDataModel {

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Tweet> tweets = new ArrayList<>();
    private static final TweetDataModel ourInstance = new TweetDataModel();
    private String mainUser;
    private TwatterActivity activity;

    static TweetDataModel getInstance() {
        return ourInstance;
    }


    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }


    public void loadResponse(String responseBody, int choice) {
        if (choice == 0) {
            choice0(responseBody);
        }
        if (choice == 1) {
            choice1(responseBody);
        }
        if (choice == 2) {
            choice2(responseBody);
        }
    }

    private void choice0(String responseBody) {
        Log.d("TEST1.1", responseBody);

        JSONArray jsonArray = new JSONArray();

        try {
//            JSONObject jsonobject = new JSONObject(responseBody);
            jsonArray = new JSONArray(responseBody);

            Log.d("TEST6.3.2", jsonArray.length() + "");

        } catch (JSONException e) {

            Log.d("TEST6.3.5", e.getMessage());
        }


        int x = jsonArray.length();
        Log.d("TEST5", x + "");
        for (int tweetCount = 0; tweetCount < x; tweetCount++) {
            JSONObject tweet = null;
            Log.d("TEST1", "HI");
            try {
                tweet = jsonArray.getJSONObject(tweetCount);
                Log.d("TEST2", "HI");
                String text = tweet.getString("text");
                JSONObject jSONUser = tweet.getJSONObject("user");
                Log.d("TEST3", "HI");
                String authorName = jSONUser.getString("name");
                String authorLocation = jSONUser.getString("location");
                if (authorLocation.equals("")) {
                    authorLocation = "N/A";
                }
                String description = jSONUser.getString("description");
                if (description.equals("")) {
                    description = "N/A";
                }
                User user = new User(authorName, authorLocation, description,"N/A");
                Tweet newTweet = new Tweet(authorName, text, user);
                tweets.add(newTweet);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void choice1(String responseBody) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(responseBody);
        } catch (JSONException e) {
        }
        try {
            Log.d("TEST2", "HI");
            mainUser = jsonObject.getString("screen_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void choice2(String responseBody) {
        User user = null;

        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            String profileImageURL = jsonObject.getString("profile_image_url");
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            String location = jsonObject.getString("location");
            user = new User(name,location,description, mainUser);
            user.setProfile_image_url(profileImageURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        users.add(user);
        activity.setInfo();
    }


    public TweetDataModel() {

    }

    public String getMainUser() {
        return mainUser;
    }

    public void setActivity(TwatterActivity activity) {
        this.activity = activity;
    }

    public TwatterActivity getActivity() {
        return activity;
    }
    //    public void loadJSONFromFile(Context context) {//stolen from yoran on 12th of september, 1944, SS bunker 200 meters underground
//        String json = null;
//
//        InputStream is = null;
//        try {
//            is = context.getAssets().open("test.json");
//            Scanner scanner = new Scanner(is);
//            StringBuilder builder = new StringBuilder();
//
//            while (scanner.hasNextLine()) {
//                builder.append(scanner.nextLine());
//            }
//            JSONObject object = new JSONObject(builder.toString());
//            JSONArray tweetArray = object.getJSONArray("statuses");
//            int x = tweetArray.length();
//            for (int tweetCount = 0; tweetCount < tweetArray.length(); tweetCount++) {
//                JSONObject tweet = tweetArray.getJSONObject(tweetCount);
//                String id = tweet.getString("id_str");
//                String text = tweet.getString("text");
//                JSONObject jSONUser = tweet.getJSONObject("user");
//                User user = new User(jSONUser.getString("name"), jSONUser.getString("location"), jSONUser.getString("description"));
//                Tweet newTweet = new Tweet(id, text, user);
//                tweets.add(newTweet);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//    }


}

