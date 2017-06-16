package com.example.william.twatter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.william.twatter.TwitterInfo.Tweet;
import com.example.william.twatter.TwitterInfo.User;

/**
 * Created by William on 5/9/2017.
 */

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String tweetid = getIntent().getStringExtra("TWEETID");
        Log.d("TEST", tweetid);
        TweetDataModel model = TweetDataModel.getInstance();

        for (Tweet t:model.getTweets()) {
            if(t.getTweetID().equals(tweetid)){
                User user = t.getUser();
                TextView name =(TextView) findViewById(R.id.nameText);
                TextView location = (TextView) findViewById(R.id.locationText);
                TextView text = (TextView) findViewById(R.id.descriptionText);
                name.setText(user.getName());
                location.setText(user.getLocation());
                text.setText(t.getText());
            }
        }




    }
}
