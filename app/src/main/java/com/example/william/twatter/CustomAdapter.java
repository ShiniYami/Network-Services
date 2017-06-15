package com.example.william.twatter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.william.twatter.TwitterInfo.User;

import java.util.List;

/**
 * Created by William on 5/9/2017.
 */

public class CustomAdapter extends ArrayAdapter<User> {
    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView = LayoutInflater.from(getContext()).inflate(R.layout.user_list_item, parent, false);

        return customView;
    }
}
