package com.example.william.twatter.Singletons;

import android.util.Log;

import com.example.william.twatter.Activities.TwatterActivity;
import com.example.william.twatter.TwitterInfo.Tweet;
import com.example.william.twatter.TwitterInfo.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * This class is our singleton datamodel, where we store the arrayLists.
 */

public class TweetDataModel {

    private static TweetDataModel ourInstance = new TweetDataModel();
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<User> searchedUsers = new ArrayList<>();
    private ArrayList<Tweet> tweets = new ArrayList<>();
    private String mainUser;
    private String mainUserID;
    private TwatterActivity activity;

    public static TweetDataModel getInstance() {
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


    /**
     * This method will redirect the responseBody to the choice given.
     * @param responseBody is the String which contains a JSON file.
     * @param choice is the choice given, it corresponds to what kind of request we make in the
     *               first place.
     */
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
        if (choice == 3) {
            //ignore, because it is a post
        }
        if (choice == 4) {
            choice4(responseBody);
        }
    }

    /**
     * This method will convert the responseBody into information we need, such as the users and
     * the tweets.
     * @param responseBody is the String which contains a JSON file.
     */
    private void choice0(String responseBody) {
        //make sure the tweets are empty, because we don't want to be showing the wrong tweets.
        tweets.clear();

        JSONArray jsonArray = new JSONArray();
        Log.d("TEST1.1", responseBody);
        try {
            //here we make the responseBody into a JSONObject and afterwards into a JSONArray.
            JSONObject object = new JSONObject(responseBody);
            jsonArray = object.getJSONArray("statuses");
        } catch (JSONException e) {
            //if that doesn't work, we make the responseBody into a JSONArray instead.

            try {
                jsonArray = new JSONArray(responseBody);

                Log.d("TEST6.3.2", jsonArray.length() + "");

            } catch (JSONException e2) {

                Log.d("TEST6.3.5", e2.getMessage());
            }
        }


        int x = jsonArray.length();
        Log.d("TEST5", x + "");
        //here for every tweet in the JSONArray we add the Tweet information to the Tweets
        // ArrayList.
        for (int tweetCount = 0; tweetCount < x; tweetCount++) {
            Log.d("TEST1", "HI");
            try {
                JSONObject tweet = jsonArray.getJSONObject(tweetCount);
                Log.d("TEST2", "HI");
                String text = tweet.getString("text");

                //here we get the User from the Tweet so that we can add the users to the users
                // ArrayList.
                JSONObject jSONUser = tweet.getJSONObject("user");
                String url = "none";
                try {
                    //here we get the url from the tweet, so we can add it to the Tweet later.
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
                //here we remove some of the String because it looks ugly.
                timeOfPost = timeOfPost.replace("+0000", "");
                //here we split the String so that we can change a certain part of it.
                String[] split = timeOfPost.split(" ");
                //here we split that String so that we can change the hours to the right GMT.
                String[] split2 = split[3].split(":");
                //here we add the GMT to the hours.
                int hour = Integer.parseInt(split2[0]);
                hour += 2;
                //here we make sure that the hours won't excel 24 hours.
                if (hour > 23) {
                    hour = hour - 24;
                }
                split2[0] = String.valueOf(hour);
                //here we revert the split.
                String sum = "";
                for (int i = 0; i < split2.length; i++) {
                    if (i + 1 == split2.length) {
                        sum += split2[i];
                    } else {
                        sum += split2[i] + ":";
                    }
                }
                //here we revert the other split
                split[3] = sum;
                String sum2 = "";
                for (int i = 0; i < split.length; i++) {
                    if (i - 1 == split.length) {
                        sum2 += split[i];
                    } else {
                        sum2 += split[i] + " ";
                    }
                }
                //this result in the same String but with the hours changed.
                timeOfPost = sum2;

                if (authorLocation.equals("")) {
                    authorLocation = "N/A";
                }
                String description = jSONUser.getString("description");
                if (description.equals("")) {
                    description = "N/A";
                }
                String userID = jSONUser.getString("id");

                boolean followed = jSONUser.getBoolean("following");
                boolean followRequestSent = jSONUser.getBoolean("follow_request_sent");
                //here we create a User.
                User user = new User(authorName, authorLocation, description, screenName, profilePictureURL, userID, followed, followRequestSent);
                //here we create a Tweet.
                Tweet newTweet = new Tweet(authorName, text, user, timeOfPost, url);
                tweets.add(newTweet);
                int count = 0;
                //here we check if the user does already exist in the users ArrayList.
                for (User u : users) {
                    if (user.getScreenName().equals(u.getScreenName())) {
                        count++;
                    }
                }
                if (count == 0) {
                    //here we add the user if it isn't already in there.
                    users.add(user);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //here we refresh the listView so that we see the changes.
        activity.refreshListView();

    }

    /**
     * This method gets the screenName of the Main User.
     * @param responseBody is the String which contains a JSON file.
     */
    private void choice1(String responseBody) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(responseBody);
        } catch (JSONException e) {
        }
        try {
            Log.d("TEST2", "HI");
            //here we set the MainUser so we always know who is logged in, without having to make a
            // new request every time.
            mainUser = jsonObject.getString("screen_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method adds a user to the users ArrayList.
     * @param responseBody is the String which contains a JSON file.
     */
    private void choice2(String responseBody) {
        String ID = "";
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            String profileImageURL = jsonObject.getString("profile_image_url");
            profileImageURL = profileImageURL.replace("_normal", "");
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            String location = jsonObject.getString("location");
            ID = jsonObject.getString("id");
            String screenName = jsonObject.getString("screen_name");
            boolean followed = jsonObject.getBoolean("following");
            boolean followRequestSent = jsonObject.getBoolean("follow_request_sent");

            if (screenName.equals(mainUser)) {
                //if the mainUser is called, his ID is added as another identifier of the main user.
                mainUserID = ID;
            }
            int count = 0;

            //if the user already exists in the arraylist we update their changeable information,
            // otherwise we add the user to the users ArrayList.
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
                User user = new User(name, location, description, screenName, profileImageURL, ID, followed, followRequestSent);
                users.add(user);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        activity.setInfo(ID);
    }


    /**
     * This method will get all the users in the JSON file and puts them into the users ArrayList.
     * @param responseBody
     */
    private void choice4(String responseBody) {
        //makes sure this arrayList gets cleared.
        searchedUsers.clear();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = new JSONArray(responseBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //for every user in the JSONArray we get the user information, and add them to the searchedUsers and users ArrayLists.
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

                int count = 0;
                User user = new User(name, location, description, screenName, profileImageURL, ID, followed, followRequestSent);
                searchedUsers.add(user);
                //if the user already exists in the arraylist we update their changeable information,
                // otherwise we add the user to the users ArrayList.
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
        //here we make sure that the activity that displays the information will get started.
        activity.startUserSearchActivity();
    }

    public String getMainUser() {
        return mainUser;
    }

    public String getMainUserID() {
        return mainUserID;
    }

    public TwatterActivity getActivity() {
        return activity;
    }

    public void setActivity(TwatterActivity activity) {
        this.activity = activity;
    }

}

