package com.example.william.twatter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.william.twatter.TwitterInfo.User;
import com.github.scribejava.core.model.OAuthRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import it.sephiroth.android.library.picasso.Picasso;

public class TwatterActivity extends AppCompatActivity implements TweetListFragment.itemClicked {
    public int choice = 0;
    private TweetDataModel model = TweetDataModel.getInstance();
    private ImageView imageView;
    private TextView nameTextView;
    private TextView screennameTextView;
    private TextView descriptionTextView;
    private EditText editTextSearch;
    private Button searchButton;
    private Button userSearchButton;
    private PopupWindow pw;
    private RelativeLayout layout;
    private Button followButton;
    private OAuthHandler handler = OAuthHandler.getInstance();

    private int deviceHeight;
    private int deviceWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twatter);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        deviceHeight = displayMetrics.heightPixels;
        deviceWidth = displayMetrics.widthPixels;
        model.setActivity(this);


        Log.d("LOG2", "Hey");
        imageView = (ImageView) findViewById(R.id.imageView);
        nameTextView = (TextView) findViewById(R.id.nameHolder);
        screennameTextView = (TextView) findViewById(R.id.tagHolder);
        descriptionTextView = (TextView) findViewById(R.id.descriptionHolder);
        layout = (RelativeLayout) findViewById(R.id.relativeLayout);
        followButton = (Button) findViewById(R.id.followButton);

        followButton.setVisibility(View.GONE);


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
    public void click(int position, int kind, Activity activity) {
        if (kind == 1) {
            activity.finish();
        }

        if (position == 1) {
            getInfo(model.getMainUser());
            getTimeLine(model.getMainUser());
        } else if (position == 2) {
            Intent intent = new Intent(TwatterActivity.this, MessageActivity.class);
            startActivity(intent);
        } else if (position == 3) {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            pw = new PopupWindow(inflater.inflate(R.layout.popup_search, null, false), deviceWidth, deviceHeight, true);

            View pwContentView = pw.getContentView();

            editTextSearch = (EditText) pwContentView.findViewById(R.id.editTextSearch);
            searchButton = (Button) pwContentView.findViewById(R.id.bttn_search);
            userSearchButton = (Button) pwContentView.findViewById(R.id.bttn_user_search);

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choice = 1;
                    search();
                }
            });
            userSearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choice = 2;
                    search();
                }
            });
            pw.showAtLocation(this.findViewById(R.id.listfrag), Gravity.CENTER, 0, 0);
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
        for (User user : model.getUsers()) {
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


                //layout.setBackgroundColor(Color.parseColor(user.getBackgroundColor()));
            }
        }
    }

    public void refreshListView() {
        TweetListFragment fragment = (TweetListFragment) getFragmentManager().findFragmentById(R.id.listfrag);
        fragment.refreshListView();

    }

    public void search() {
        String search = editTextSearch.getText().toString();
        pw.dismiss();
        try {
            search = encode(search);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OAuthRequest request;
        if (choice == 1) {
            setInfo(model.getMainUserID());
            request = handler.makeRequest(new RequestBuilderHelper("GET", "https://api.twitter.com/1.1/search/tweets.json?q=" + search));
            handler.signRequest(request);
            handler.sendRequest(request, 0);
        } else {
            request = handler.makeRequest(new RequestBuilderHelper("GET", "https://api.twitter.com/1.1/users/search.json?q=" + search));
            handler.signRequest(request);
            handler.sendRequest(request, 4);
        }

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

    public void startDetailActivity(String tweetID) {
        Intent intent = new Intent(TwatterActivity.this, DetailActivity.class);
        intent.putExtra("TWEETID", tweetID);
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

}
