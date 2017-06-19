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
import android.widget.TextView;

import com.example.william.twatter.TwitterInfo.Tweet;
import com.example.william.twatter.TwitterInfo.User;

import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by William on 6/19/2017.
 */

public class UserListAdapter extends ArrayAdapter {
    TweetDataModel model = TweetDataModel.getInstance();

    TextView name;
    TextView tag;
    TextView description;
    ImageView profilePic;


    public UserListAdapter(@NonNull Context context, @LayoutRes int resource,  @NonNull List objects) {
        super(context, resource, objects);


    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView = LayoutInflater.from(getContext()).inflate(R.layout.user_list_item, parent, false);
        if (customView == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            customView = vi.inflate(R.layout.user_list_item, null);
        }

        name = (TextView) customView.findViewById(R.id.tv_name);
        tag = (TextView) customView.findViewById(R.id.tv_tag);
        description = (TextView) customView.findViewById(R.id.tv_description);
        profilePic = (ImageView) customView.findViewById(R.id.iv_profilepic);

        User user = model.getSearchedUsers().get(position);

        name.setText(user.getName());
        String tagString = "@"+user.getScreenName();
        tag.setText(tagString);
        description.setText(user.getDescription());
        Picasso.with(getContext()).load(user.getUrl()).into(profilePic);


        return customView;
    }
}
