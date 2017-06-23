package com.example.william.twatter.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.william.twatter.Fragments.TweetListFragment;
import com.example.william.twatter.Helper.RequestBuilderHelper;
import com.example.william.twatter.R;
import com.example.william.twatter.Singletons.OAuthHandler;
import com.example.william.twatter.Singletons.TweetDataModel;
import com.example.william.twatter.TwitterInfo.User;
import com.github.scribejava.core.model.OAuthRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * This class displays the home screen, which is the TwatterActivity.
 */

public class TwatterActivity extends AppCompatActivity implements TweetListFragment.itemClicked {

    private TweetDataModel model = TweetDataModel.getInstance();
    private ImageView imageView;
    private TextView nameTextView;
    private TextView screennameTextView;
    private TextView descriptionTextView;

    private RelativeLayout layout;
    private Button followButton;
    private OAuthHandler handler = OAuthHandler.getInstance();


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
        followButton = (Button) findViewById(R.id.followButton);

        //make the follow button invisible so that you wont follow yourself.
        followButton.setVisibility(View.GONE);

//here we make the request
        final OAuthRequest request1 = handler.makeRequest(new RequestBuilderHelper("GET", "https://api.twitter.com/1.1/account/settings.json"));

//here we sign the request
        handler.signRequest(request1);

//here we send the request
        handler.sendRequest(request1, 1);
//set the name to the main user, so his timeline and info will show.
        String name = model.getMainUser();
        
        getInfo(name);
        getTimeLine(name, true);


    }

    public void getTimeLine(String name, boolean useHomeTimeline) {
        OAuthRequest request;
        if (name.equals(model.getMainUser()) && useHomeTimeline) {
            request = handler.makeRequest(new RequestBuilderHelper("GET", "https://api.twitter.com/1.1/statuses/home_timeline.json?count=20"));
        } else {
            request = handler.makeRequest(new RequestBuilderHelper("GET", "https://api.twitter.com/1.1/statuses/user_timeline.json?count=20&user_id=%40" + name + "&screen_name=%40" + name + ""));
        }
        handler.signRequest(request);
        handler.sendRequest(request, 0);
    }

    @Override
    public void click(int position, int kind, Activity activity) {
        if (kind == 1) {
            activity.finish();
        }

        if (position == 1) {
            getInfo(model.getMainUser());
            getTimeLine(model.getMainUser(), true);
        } else if (position == 2) {
            Intent intent = new Intent(TwatterActivity.this, MessageActivity.class);
            startActivity(intent);
        } else if (position == 3) {
            Intent intent = new Intent(TwatterActivity.this, SearchActivity.class);
            startActivity(intent);
        } else if (position == 4) {

        }
    }

    public void getInfo(String name) {
        final OAuthRequest request2 = handler.makeRequest(new RequestBuilderHelper("GET", "https://api.twitter.com/1.1/users/show.json?screen_name=%40" + name + "&user_id=%40" + name));
        handler.signRequest(request2);
        handler.sendRequest(request2, 2);
    }


    public void setInfo(String ID) {
        followButton.setClickable(true);

        if (!ID.equals(model.getMainUserID())) {
            followButton.setVisibility(View.VISIBLE);
        } else {
            followButton.setVisibility(View.GONE);
        }
        for (final User user : model.getUsers()) {
            if (user.getID().equals(ID)) {
                final User u = user;
                Picasso.with(getApplicationContext()).load(user.getUrl()).into(imageView);
                String screenName = "@" + user.getScreenName();
                nameTextView.setText(user.getName());
                screennameTextView.setText(screenName);
                descriptionTextView.setText(user.getDescription());
                String isFollowed = "Follow";
                followButton.setText(isFollowed);
                if (user.isFollowed()) {
                    isFollowed = "Unfollow";
                    followButton.setText(isFollowed);

                }
                followButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        if (u.isFollowed()) {
                            unFollowUser(u);
                            String string = "Follow";
                            u.setFollowed(false);
                            followButton.setText(string);
                            Toast.makeText(model.getActivity(), "User has been unfollowed",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            followUser(u);
                            String string = "UnFollow";
                            u.setFollowed(true);
                            followButton.setText(string);
                            Toast.makeText(model.getActivity(), "User has been followed",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                });
                if (u.isFollowRequestSent()) {
                    followButton.setClickable(false);
                }
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getTimeLine(user.getScreenName(), false);
                    }
                });
            }
        }
    }

    public void refreshListView() {
        TweetListFragment fragment = (TweetListFragment) getFragmentManager().findFragmentById(R.id.listfrag);
        fragment.refreshListView();

    }


    public void sendTweet(String message, Activity activity) {
        activity.finish();
        try {
            message = encode(message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final OAuthRequest request3 = handler.makeRequest(new RequestBuilderHelper("POST", "https://api.twitter.com/1.1/statuses/update.json?status=" + message + "&display_coordinates=false"));
        handler.signRequest(request3);
        handler.sendRequest(request3, 3);
    }

    public String encode(String string) throws UnsupportedEncodingException {
        String encodedString = URLEncoder.encode(string, "UTF-8");
        System.out.format("'%s'\n", encodedString);
        return encodedString;
    }

    public void startDetailActivity(int position) {
        Intent intent = new Intent(TwatterActivity.this, DetailActivity.class);
        intent.putExtra("POSITION", position);
        startActivity(intent);
    }

    public void followUser(User user) {
        String screenname = user.getScreenName();
        final OAuthRequest request3 = handler.makeRequest(new RequestBuilderHelper("POST", "https://api.twitter.com/1.1/friendships/create.json?screen_name=%40" + screenname + "&user_id=%40" + screenname));
        handler.signRequest(request3);
        handler.sendRequest(request3, 3);
    }

    public void unFollowUser(User user) {
        String screenname = user.getScreenName();
        final OAuthRequest request3 = handler.makeRequest(new RequestBuilderHelper("POST", "https://api.twitter.com/1.1/friendships/destroy.json?screen_name=%40" + screenname + "&user_id=%40" + screenname));
        handler.signRequest(request3);
        handler.sendRequest(request3, 3);
    }

    public void startUserSearchActivity() {
        Intent intent = new Intent(TwatterActivity.this, UserSearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}
