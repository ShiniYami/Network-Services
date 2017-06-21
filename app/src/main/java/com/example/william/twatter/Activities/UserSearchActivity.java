package com.example.william.twatter.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.william.twatter.Adapters.UserListAdapter;
import com.example.william.twatter.R;
import com.example.william.twatter.Singletons.TweetDataModel;
import com.example.william.twatter.TwitterInfo.User;

public class UserSearchActivity extends AppCompatActivity {

    private TweetDataModel model = TweetDataModel.getInstance();

    private ListView lv;
    private UserListAdapter adapt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);

        lv = (ListView) findViewById(R.id.user_list);

        adapt = new UserListAdapter(this, R.layout.user_list_item, model.getSearchedUsers());
        lv.setAdapter(adapt);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User user = model.getSearchedUsers().get(position);


                model.getActivity().getInfo(user.getScreenName());
                model.getActivity().getTimeLine(user.getScreenName(), false);
                model.getActivity().click(4, 1, UserSearchActivity.this);
            }
        });
    }
}
