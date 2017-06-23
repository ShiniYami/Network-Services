package com.example.william.twatter.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.william.twatter.R;
import com.example.william.twatter.Singletons.TweetDataModel;
import com.example.william.twatter.TwitterInfo.Tweet;
import com.example.william.twatter.TwitterInfo.User;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * This class lets you see the detail screen.
 */

public class DetailActivity extends AppCompatActivity {

    TextView name;
    TextView location;
    TextView text;
    ImageView profilePicture;
    TextView url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int position = getIntent().getIntExtra("POSITION", 0);

        TweetDataModel model = TweetDataModel.getInstance();

        Tweet t = model.getTweets().get(position);
                User user = t.getUser();

                name = (TextView) findViewById(R.id.nameText);
                location = (TextView) findViewById(R.id.locationText);
                text = (TextView) findViewById(R.id.descriptionText);
                profilePicture = (ImageView) findViewById(R.id.Profile_img);
                url = (TextView) findViewById(R.id.textView3);

                name.setText(user.getName());
                location.setText(user.getLocation());
                text.setText(t.getText());
                Picasso.with(getApplicationContext()).load(user.getUrl()).into(profilePicture);
                final String urlString = t.getUrl();
                url.setText(urlString);


    }
}
