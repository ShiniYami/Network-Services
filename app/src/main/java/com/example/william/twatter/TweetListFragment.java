package com.example.william.twatter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.william.twatter.TwitterInfo.Tweet;
import com.example.william.twatter.TwitterInfo.User;

import java.util.ArrayList;

/**
 * Created by William on 5/9/2017.
 */

public class TweetListFragment extends Fragment {

    TweetDataModel model = TweetDataModel.getInstance();
    OAuthHandler handler = OAuthHandler.getInstance();

    ListView lv;
    TweetListAdapter adapt;
    ImageView button1;
    ImageView button2;
    ImageView button3;
    ImageView button4;

    ArrayList<Tweet> tweets;

    public interface itemClicked {
        public void click(int position);
    }

    itemClicked listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (itemClicked) activity;
        } catch (ClassCastException ccex) {
            throw new ClassCastException(activity + "Must be implemented");
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tweet_list, container);//false means it won't be attached to the rootView by itself, because android will already do that for me

        lv = (ListView) rootView.findViewById(R.id.tweetList);
        button1 = (ImageView) rootView.findViewById(R.id.ImageView1);
        button2 = (ImageView) rootView.findViewById(R.id.ImageView2);
        button3 = (ImageView) rootView.findViewById(R.id.ImageView3);
        button4 = (ImageView) rootView.findViewById(R.id.ImageView4);

        tweets = model.getTweets();
        adapt = new TweetListAdapter(getActivity(), R.layout.tweet_list_item, tweets);
        lv.setAdapter(adapt);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(3);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(4);
            }
        });

        return rootView;
    }


    public void refreshListView(){
        adapt.notifyDataSetChanged();
    }

}
