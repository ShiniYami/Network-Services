package com.example.william.twatter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.william.twatter.TwitterInfo.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 5/9/2017.
 */

public class TweetListAdapter extends ArrayAdapter {
    TweetDataModel model = TweetDataModel.getInstance();
    ArrayList<Tweet> tweets ;
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

        tweets = model.getTweets();
        int k = tweets.size();
        TextView name = (TextView) customView.findViewById(R.id.authorName);
        TextView text = (TextView) customView.findViewById(R.id.tweetText);
        Tweet tweet = tweets.get(position);
        name.setText(tweet.getAuthorName());
        text.setText(tweet.getText());

        return customView;
    }
}
