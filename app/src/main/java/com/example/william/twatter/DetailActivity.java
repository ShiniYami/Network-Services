package com.example.william.twatter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        Bundle b = getIntent().getExtras();
        int position = b.getInt("POSITION");
        TweetDataModel model = TweetDataModel.getInstance();
        Tweet tweet = model.getTweets().get(position);
        User user = tweet.getUser();
        TextView name =(TextView) findViewById(R.id.nameText);
        TextView location = (TextView) findViewById(R.id.locationText);
        TextView description = (TextView) findViewById(R.id.descriptionText);
        name.setText(user.getName());
        location.setText(user.getLocation());
        description.setText(user.getDescription());
    }
}
