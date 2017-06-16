package com.example.william.twatter;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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


        Log.d("LOG2","Hey");
        imageView = (ImageView) findViewById(R.id.imageView);
        nameTextView = (TextView) findViewById(R.id.nameHolder);
        screennameTextView = (TextView) findViewById(R.id.tagHolder);
        descriptionTextView = (TextView) findViewById(R.id.descriptionHolder);
        layout = (RelativeLayout) findViewById(R.id.relativeLayout);


        final OAuthRequest request = handler.makeRequest(new RequestBuilderHelper("GET","https://api.twitter.com/1.1/statuses/home_timeline.json?count=20"));
        final OAuthRequest request1 = handler.makeRequest(new RequestBuilderHelper("GET","https://api.twitter.com/1.1/account/settings.json"));

        handler.signRequest(request);
        handler.signRequest(request1);

        handler.sendRequest(request,0);
        handler.sendRequest(request1,1);

        String name = model.getMainUser();
        final OAuthRequest request2 = handler.makeRequest(new RequestBuilderHelper("GET","https://api.twitter.com/1.1/users/show.json?screen_name=%40"+name+"&user_id=%40"+name));
        handler.signRequest(request2);
        handler.sendRequest(request2,2);




    }

        @Override
    public void click(int position) {
            Intent detailIntent = new Intent(TwatterActivity.this,DetailActivity.class);
            detailIntent.putExtra("POSITION",position);
            startActivity(detailIntent);
    }

    public void setInfo(){
        String name = model.getMainUser();
    for (User user : model.getUsers()) {
        if(user.getScreenName().equals(name)){
            imageView.setImageBitmap(user.getProfile_image_Bitmap());
            String screenName = "@"+name;
            nameTextView.setText(user.getName());
            screennameTextView.setText(screenName);
            descriptionTextView.setText(user.getDescription());
            //layout.setBackgroundColor(Color.parseColor(user.getBackgroundColor()));
        }
    }}

}
