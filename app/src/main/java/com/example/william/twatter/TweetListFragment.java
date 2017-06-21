package com.example.william.twatter;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.william.twatter.TwitterInfo.Tweet;

import java.util.ArrayList;

/**
 * Created by William on 5/9/2017.
 */

public class TweetListFragment extends Fragment {

    itemClicked listener;
    private TweetDataModel model = TweetDataModel.getInstance();
    private OAuthHandler handler = OAuthHandler.getInstance();
    private ListView lv;
    private TweetListAdapter adapt;
    private ImageView button1;
    private ImageView button2;
    private ImageView button3;
    private ImageView button4;
    private ArrayList<Tweet> tweets;

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
                listener.click(1,0, getActivity());
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(2,0,getActivity());
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(3,0,getActivity());
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(4,0,getActivity());
            }
        });

        return rootView;
    }

    public void refreshListView(){
        adapt.notifyDataSetChanged();
    }


    public interface itemClicked {
        public void click(int position, int kind, Activity activity);
    }

}
