package com.example.william.twatter.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.william.twatter.Helper.RequestBuilderHelper;
import com.example.william.twatter.R;
import com.example.william.twatter.Singletons.OAuthHandler;
import com.example.william.twatter.Singletons.TweetDataModel;
import com.github.scribejava.core.model.OAuthRequest;

import java.io.UnsupportedEncodingException;

public class SearchActivity extends AppCompatActivity {
    private int choice = 0;
    private TweetDataModel model = TweetDataModel.getInstance();
    private OAuthHandler handler = OAuthHandler.getInstance();
    private EditText editTextSearch;
    private Button searchButton;
    private Button userSearchButton;
    private TwatterActivity activity = model.getActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_search);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        searchButton = (Button) findViewById(R.id.bttn_search);
        userSearchButton = (Button) findViewById(R.id.bttn_user_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = 1;
                search();
            }
        });
        userSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = 2;
                search();
            }
        });
    }

    public void search() {
        String search = editTextSearch.getText().toString();
        finish();
        try {
            search = model.getActivity().encode(search);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OAuthRequest request;
        if (choice == 1) {
            activity.setInfo(model.getMainUserID());
            request = handler.makeRequest(new RequestBuilderHelper("GET", "https://api.twitter.com/1.1/search/tweets.json?q=" + search));
            handler.signRequest(request);
            handler.sendRequest(request, 0);
        } else {
            request = handler.makeRequest(new RequestBuilderHelper("GET", "https://api.twitter.com/1.1/users/search.json?q=" + search));
            handler.signRequest(request);
            handler.sendRequest(request, 4);
        }

    }
}
