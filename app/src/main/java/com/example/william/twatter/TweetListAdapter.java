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
import android.widget.ImageButton;
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
    ImageButton profileImageButton;
    TextView tag;

    public TweetListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_list_item, parent, false);
        if (customView == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            customView = vi.inflate(R.layout.tweet_list_item, null);
        }

        name = (TextView) customView.findViewById(R.id.authorName);
        text = (TextView) customView.findViewById(R.id.tweetText);
        profileImageButton = (ImageButton) customView.findViewById(R.id.profile_img);
        tag = (TextView) customView.findViewById(R.id.tag_holder);

        tweets = model.getTweets();
        Log.d("TEST1.1.1.1.1",model.getUsers().get(1).getName());
        users = model.getUsers();

        Tweet tweet = tweets.get(position);
        User user = users.get(position);


        profileImageButton.setImageBitmap(user.getProfile_image_Bitmap());
        name.setText(tweet.getAuthorName());
        text.setText(tweet.getText());
        tag.setText(user.getScreenName());


        return customView;
    }


}
