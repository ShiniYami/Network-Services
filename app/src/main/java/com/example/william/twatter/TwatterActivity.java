package com.example.william.twatter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.william.twatter.TwitterInfo.User;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;

import java.io.IOException;

public class TwatterActivity extends AppCompatActivity implements TweetListFragment.itemClicked {
    TweetDataModel model = TweetDataModel.getInstance();
    ImageView imageView;
    TextView nameTextView;
    TextView screennameTextView;
    TextView descriptionTextView;
    RelativeLayout layout;
    OAuthHandler handler = OAuthHandler.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twatter);

        model.setActivity(this);


        Log.d("LOG2", "Hey");
        imageView = (ImageView) findViewById(R.id.imageView);
        nameTextView = (TextView) findViewById(R.id.nameHolder);
        screennameTextView = (TextView) findViewById(R.id.tagHolder);
        descriptionTextView = (TextView) findViewById(R.id.descriptionHolder);
        layout = (RelativeLayout) findViewById(R.id.relativeLayout);


        final OAuthRequest request1 = handler.makeRequest(new RequestBuilderHelper("GET", "https://api.twitter.com/1.1/account/settings.json"));


        handler.signRequest(request1);


        handler.sendRequest(request1, 1);

        String name = model.getMainUser();
        getInfo(name);
        getTimeLine(name);


    }

    public void getTimeLine(String name) {
        OAuthRequest request;
        if (name.equals(model.getMainUser())) {
            request = handler.makeRequest(new RequestBuilderHelper("GET", "https://api.twitter.com/1.1/statuses/home_timeline.json?count=20"));
        } else {
            request = handler.makeRequest(new RequestBuilderHelper("GET", "https://api.twitter.com/1.1/statuses/user_timeline.json?count=20&user_id=%40" + name + "&screen_name=%40" + name + ""));
        }
        handler.signRequest(request);
        handler.sendRequest(request, 0);
    }

    @Override
    public void click(int position, int kind,Activity activity) {
        if(kind == 1){
            activity.finish();
        }

        if (position == 1) {
            getInfo(model.getMainUser());
            getTimeLine(model.getMainUser());
        } else if (position == 2) {
            Intent intent = new Intent(TwatterActivity.this, MessageActivity.class);
            startActivity(intent);
        } else if (position == 3) {

        } else if (position == 4) {

        }
    }

    public void getInfo(String name) {
        final OAuthRequest request2 = handler.makeRequest(new RequestBuilderHelper("GET", "https://api.twitter.com/1.1/users/show.json?screen_name=%40" + name + "&user_id=%40" + name));
        handler.signRequest(request2);
        handler.sendRequest(request2, 2);
    }

    public void setInfo(String ID) {
        for (User user : model.getUsers()) {
            if (user.getID().equals(ID)) {
                imageView.setImageBitmap(user.getProfile_image_Bitmap());
                String screenName = "@" + user.getScreenName();
                nameTextView.setText(user.getName());
                screennameTextView.setText(screenName);
                descriptionTextView.setText(user.getDescription());
                //layout.setBackgroundColor(Color.parseColor(user.getBackgroundColor()));
            }
        }
    }

    public void refreshListView() {
        TweetListFragment fragment = (TweetListFragment) getFragmentManager().findFragmentById(R.id.listfrag);
        fragment.refreshListView();

    }

    public void sendTweet(String message, Activity activity) {
        activity.finish();
        final OAuthRequest request3 = handler.makeRequest(new RequestBuilderHelper("POST", "https://api.twitter.com/1.1/statuses/update.json?status=" + message + "&display_coordinates=false"));
        handler.signRequest(request3);
        handler.sendRequest(request3, 3);
    }

}
