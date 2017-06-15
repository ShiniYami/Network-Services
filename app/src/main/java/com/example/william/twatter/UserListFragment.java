package com.example.william.twatter;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.william.twatter.TwitterInfo.User;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by William on 5/9/2017.
 */

public class UserListFragment extends Fragment {
    public TweetDataModel model = TweetDataModel.getInstance();



    ArrayList<User> users = model.getUsers();
    public interface ItemSelectedListener{
        public void userSelected(User user);
    }
    ItemSelectedListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            listener = (ItemSelectedListener) activity;
        }
        catch (Exception e){
            throw new ClassCastException(activity + "Must be implemented");
        }
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_user_list,container,false);
        ListView lv = (ListView) rootView.findViewById(R.id.userlist);


        CustomAdapter adapt = new CustomAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,users);
        lv.setAdapter(adapt);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listener.userSelected(users.get(position));
            }
        });
        return rootView;
    }
}
