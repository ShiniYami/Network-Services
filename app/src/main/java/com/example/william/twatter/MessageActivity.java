package com.example.william.twatter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MessageActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    TweetDataModel model = TweetDataModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.tweetButton);
        imageView1 = (ImageView) findViewById(R.id.ImageView1);
        imageView2 = (ImageView) findViewById(R.id.ImageView2);
        imageView3 = (ImageView) findViewById(R.id.ImageView3);
        imageView4 = (ImageView) findViewById(R.id.ImageView4);

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
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.getActivity().click(4, 1, MessageActivity.this);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });


    }

    private void sendMessage() {
        String string = String.valueOf(editText.getText());
        try {
            string = encode(string);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("STRING", string);
        model.getActivity().sendTweet(string,MessageActivity.this);
    }

    public String encode(String string) throws UnsupportedEncodingException {
        String encodedString = URLEncoder.encode(string, "UTF-8");
        System.out.format("'%s'\n", encodedString);
        return encodedString;
    }

}
