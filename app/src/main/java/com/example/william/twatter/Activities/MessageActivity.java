package com.example.william.twatter.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.william.twatter.R;
import com.example.william.twatter.Singletons.TweetDataModel;

/**
 * This class displays the activity in which you can post tweets.
 */

public class MessageActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private TweetDataModel model = TweetDataModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.tweetButton);
        imageView1 = (ImageView) findViewById(R.id.ImageView1);
        imageView2 = (ImageView) findViewById(R.id.ImageView2);
        imageView3 = (ImageView) findViewById(R.id.ImageView3);


        //this is the menu at the bottom.
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getActivity().click(1, 1, MessageActivity.this);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getActivity().click(2, 1, MessageActivity.this);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getActivity().click(3, 1, MessageActivity.this);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }


    /**
     * This method will make it so the tweet gets posted.
     */
    private void sendMessage() {
        String string = String.valueOf(editText.getText());
        model.getActivity().sendTweet(string,MessageActivity.this);
    }

}
