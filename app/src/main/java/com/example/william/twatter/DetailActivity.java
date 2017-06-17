package com.example.william.twatter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.william.twatter.TwitterInfo.Tweet;
import com.example.william.twatter.TwitterInfo.User;

/**
 * Created by William on 5/9/2017.
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

        String tweetid = getIntent().getStringExtra("TWEETID");
        Log.d("TEST", tweetid);
        TweetDataModel model = TweetDataModel.getInstance();

        for (Tweet t : model.getTweets()) {
            if (t.getTweetID().equals(tweetid)) {
                User user = t.getUser();

                name = (TextView) findViewById(R.id.nameText);
                location = (TextView) findViewById(R.id.locationText);
                text = (TextView) findViewById(R.id.descriptionText);
                profilePicture = (ImageView) findViewById(R.id.Profile_img);
                url = (TextView) findViewById(R.id.textView3);

                name.setText(user.getName());
                location.setText(user.getLocation());
                text.setText(t.getText());
                profilePicture.setImageBitmap(user.getProfile_image_Bitmap());
                final String urlString = t.getUrl();
                url.setText(urlString);

                url.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startWebViewActivity(urlString);
                    }
                });
            }
        }


    }

    public void startWebViewActivity(String url) {
        Intent intent = new Intent(DetailActivity.this, WebViewActivity.class);
        intent.putExtra("URL", url);
        startActivity(intent);
    }
}
