package com.example.william.twatter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static android.R.attr.format;

/**
 * Created by William on 5/9/2017.
 */

class TweetDataModel {

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<User> searchedUsers = new ArrayList<>();
    private ArrayList<Tweet> tweets = new ArrayList<>();
    private static TweetDataModel ourInstance = new TweetDataModel();
    OAuthHandler handler = OAuthHandler.getInstance();
    private String mainUser;
    private String mainUserID;
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

    public ArrayList<User> getSearchedUsers() {
        return searchedUsers;
    }

    public void loadResponse(String responseBody, int choice) {
        Log.d("TEST311", "hi");
        if (choice == 0) {
            choice0(responseBody);
        }
        if (choice == 1) {
            choice1(responseBody);
        }
        if (choice == 2) {
            choice2(responseBody);
        }
        if (choice == 4) {
            choice4(responseBody);
        }
    }

    private void choice0(String responseBody) {
        tweets.clear();
        JSONArray jsonArray = new JSONArray();
        Log.d("TEST1.1", responseBody);
        try {
            JSONObject object = new JSONObject(responseBody);
            jsonArray = object.getJSONArray("statuses");
        } catch (JSONException e) {


            try {
//            JSONObject jsonobject = new JSONObject(responseBody);
                jsonArray = new JSONArray(responseBody);

                Log.d("TEST6.3.2", jsonArray.length() + "");

            } catch (JSONException e2) {

                Log.d("TEST6.3.5", e2.getMessage());
            }
        }


        int x = jsonArray.length();
        Log.d("TEST5", x + "");
        for (int tweetCount = 0; tweetCount < x; tweetCount++) {
            Log.d("TEST1", "HI");
            try {
                JSONObject tweet = jsonArray.getJSONObject(tweetCount);
                Log.d("TEST2", "HI");
                String text = tweet.getString("text");
                JSONObject jSONUser = tweet.getJSONObject("user");
                String url = "none";
                try {
                    JSONObject entities = tweet.getJSONObject("entities");
                    JSONArray urls = entities.getJSONArray("urls");
                    url = urls.getJSONObject(0).getString("url");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("TEST3", "HI");
                String authorName = jSONUser.getString("name");
                String authorLocation = jSONUser.getString("location");
                String profilePictureURL = jSONUser.getString("profile_image_url");
                profilePictureURL = profilePictureURL.replace("_normal", "");
                String screenName = jSONUser.getString("screen_name");
                String timeOfPost = tweet.getString("created_at");
                timeOfPost = timeOfPost.replace("+0000", "");
                String[] split = timeOfPost.split(" ");
                String[] split2 = split[3].split(":");
                int hour = Integer.parseInt(split2[0]);
                hour += 2;
                if (hour > 23) {
                    hour = hour - 24;
                }
                split2[0] = String.valueOf(hour);
                String sum = "";
                for (int i = 0; i < split2.length; i++) {
                    if (i + 1 == split2.length) {
                        sum += split2[i];
                    } else {
                        sum += split2[i] + ":";
                    }
                }
                split[3] = sum;
                String sum2 = "";
                for (int i = 0; i < split.length; i++) {
                    if (i - 1 == split.length) {
                        sum2 += split[i];
                    } else {
                        sum2 += split[i] + " ";
                    }
                }
                timeOfPost = sum2;
                if (authorLocation.equals("")) {
                    authorLocation = "N/A";
                }
                String description = jSONUser.getString("description");
                if (description.equals("")) {
                    description = "N/A";
                }
                String tweetID = tweet.getString("id");
                String userID = jSONUser.getString("id");
                String backgroundColor = "#" + jSONUser.getString("profile_background_color");

                boolean followed = jSONUser.getBoolean("following");
                boolean followRequestSent = jSONUser.getBoolean("follow_request_sent");
                User user = new User(authorName, authorLocation, description, screenName, profilePictureURL, backgroundColor, userID, followed, followRequestSent);
                Tweet newTweet = new Tweet(authorName, text, user, userID, tweetID, timeOfPost, url);
                tweets.add(newTweet);
                int count = 0;
                for (User u : users) {
                    if (user.getScreenName().equals(u.getScreenName())) {
                        count++;
                    }
                }
                if (count == 0) {
                    users.add(user);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        activity.refreshListView();

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
        String ID = "";
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            String profileImageURL = jsonObject.getString("profile_image_url");
            profileImageURL = profileImageURL.replace("_normal", "");
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            String location = jsonObject.getString("location");
            String backgroundColor = "#" + jsonObject.getString("profile_background_color");
            ID = jsonObject.getString("id");
            String screenName = jsonObject.getString("screen_name");
            boolean followed = jsonObject.getBoolean("following");
            boolean followRequestSent = jsonObject.getBoolean("follow_request_sent");
            Log.d("FOLLOW", followed + "");
            Log.d("FOLLOWREQ", followRequestSent + "");
            if (screenName.equals(mainUser)) {
                mainUserID = ID;
            }
            int count = 0;
            for (User u : users) {
                if (u.getID().equals(ID)) {
                    count++;
                    u.setUrl(profileImageURL);
                    u.setName(name);
                    u.setDescription(description);
                    u.setLocation(location);
                    u.setFollowed(followed);
                    u.setFollowRequestSent(followRequestSent);
                }
            }
            if (count == 0) {
                User user = new User(name, location, description, screenName, profileImageURL, backgroundColor, ID, followed, followRequestSent);
                users.add(user);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        activity.setInfo(ID);
    }

    private void choice4(String responseBody) {
        searchedUsers.clear();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = new JSONArray(responseBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object;
            try {
                object = jsonArray.getJSONObject(i);

                String ID = object.getString("id");
                String profileImageURL = object.getString("profile_image_url");
                profileImageURL = profileImageURL.replace("_normal", "");
                String name = object.getString("name");
                String description = object.getString("description");
                String location = object.getString("location");
                boolean followed = object.getBoolean("following");
                boolean followRequestSent = object.getBoolean("follow_request_sent");
                String screenName = object.getString("screen_name");
                String backgroundColor = object.getString("profile_background_color");
                int count = 0;
                User user = new User(name, location, description, screenName, profileImageURL, backgroundColor, ID, followed, followRequestSent);
                searchedUsers.add(user);
                for (User u : users) {
                    if (u.getID().equals(ID)) {
                        count++;
                        u.setUrl(profileImageURL);
                        u.setName(name);
                        u.setDescription(description);
                        u.setLocation(location);
                        u.setFollowed(followed);
                        u.setFollowRequestSent(followRequestSent);
                    }
                }
                if (count == 0) {
                    users.add(user);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        activity.startUserSearchActivity();
    }


    public TweetDataModel() {

    }

    public String getMainUser() {
        return mainUser;
    }

    public String getMainUserID() {
        return mainUserID;
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

