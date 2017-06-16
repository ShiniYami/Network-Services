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
import android.widget.ListView;

import com.example.william.twatter.TwitterInfo.Tweet;

import java.util.ArrayList;

/**
 * Created by William on 5/9/2017.
 */

public class TweetListFragment extends Fragment {

    TweetDataModel model = TweetDataModel.getInstance();
    OAuthHandler handler = OAuthHandler.getInstance();

    ListView lv ;
    TweetListAdapter adapt;

    ArrayList<Tweet>tweets;

    public interface itemClicked{
        public void click(int position);
    }
    itemClicked listener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (itemClicked) activity;
        }
        catch(ClassCastException ccex){
            throw new ClassCastException(activity + "Must be implemented") ;
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tweet_list,container);//false means it won't be attached to the rootView by itself, because android will already do that for me
        lv = (ListView) rootView.findViewById(R.id.tweetList);
        tweets = model.getTweets();
        adapt = new TweetListAdapter(getActivity(),R.layout.tweet_list_item,tweets);
        lv.setAdapter(adapt);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.click(position);

            }
        });

        return rootView;
    }



}
