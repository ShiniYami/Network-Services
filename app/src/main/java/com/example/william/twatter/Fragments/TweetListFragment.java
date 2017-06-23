package com.example.william.twatter.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.william.twatter.Adapters.TweetListAdapter;
import com.example.william.twatter.R;
import com.example.william.twatter.Singletons.TweetDataModel;
import com.example.william.twatter.TwitterInfo.Tweet;

import java.util.ArrayList;

/**
 * Created by William on 5/9/2017.
 * Fragment that holds the tweet timeline of the current user
 */
public class TweetListFragment extends Fragment {

    itemClicked listener;
    private TweetDataModel model = TweetDataModel.getInstance();
    private ListView lv;
    private TweetListAdapter adapt;
    private ImageView button1;
    private ImageView button2;
    private ImageView button3;
    private ArrayList<Tweet> tweets;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //sets the itemClicked llistener
            listener = (itemClicked) activity;
        } catch (ClassCastException ccex) {
            throw new ClassCastException(activity + "Must be implemented");
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tweet_list, container);
        lv = (ListView) rootView.findViewById(R.id.tweetList);
        button1 = (ImageView) rootView.findViewById(R.id.ImageView1);
        button2 = (ImageView) rootView.findViewById(R.id.ImageView2);
        button3 = (ImageView) rootView.findViewById(R.id.ImageView3);

        tweets = model.getTweets();
        adapt = new TweetListAdapter(getActivity(), R.layout.tweet_list_item, tweets);
        lv.setAdapter(adapt);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                model.getActivity().startDetailActivity(position);
            }
        });
        //Sends the position of the button that was clicked to the TwatterActivity so that it can
        // perform the right action.
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

        return rootView;
    }

    /**
     * Refreshed the view to show updated info
     */
    public void refreshListView(){
        adapt.notifyDataSetChanged();
    }

    /**
     * Interface for the buttn click listener for the buttons at the bottom of the TwatterActivity
     */
    public interface itemClicked {
        void click(int position, int kind, Activity activity);
    }

}
