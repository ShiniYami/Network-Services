package com.example.william.twatter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.william.twatter.TwitterInfo.Tweet;
import com.example.william.twatter.TwitterInfo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 5/9/2017.
 */

public class TweetListAdapter extends ArrayAdapter {

    TweetDataModel model = TweetDataModel.getInstance();
    OAuthHandler handler = OAuthHandler.getInstance();

    ArrayList<Tweet> tweets;
    ArrayList<User> users;

    TextView name;
    TextView text;
    ImageView profileImageView;
    TextView tag;
    TextView time;

    public TweetListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_list_item, parent, false);
        if (customView == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            customView = vi.inflate(R.layout.tweet_list_item, null);
        }

        name = (TextView) customView.findViewById(R.id.authorName);
        text = (TextView) customView.findViewById(R.id.tweetText);
        profileImageView = (ImageView) customView.findViewById(R.id.profile_img);
        tag = (TextView) customView.findViewById(R.id.tag_holder);
        time = (TextView) customView.findViewById(R.id.time_holder);

        tweets = model.getTweets();
        Log.d("TEST1.1.1.1.1", model.getUsers().get(1).getName());
        users = model.getUsers();

        final Tweet tweet = tweets.get(position);
        final User user = tweet.getUser();



        profileImageView.setImageBitmap(user.getProfile_image_Bitmap());
        name.setText(tweet.getAuthorName());
        text.setText(tweet.getText());
        String tagString = "@" + user.getScreenName();
        tag.setText(tagString);



        time.setText(tweet.getTimeOfPost());

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TEST4.4.4", user.getID());
                model.getActivity().getInfo(user.getScreenName());
                model.getActivity().getTimeLine(user.getScreenName());
            }
        });

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getActivity().startDetailActivity(tweet.getTweetID());
            }
        });


        return customView;
    }


}
