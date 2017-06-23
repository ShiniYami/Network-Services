package com.example.william.twatter.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.william.twatter.R;
import com.example.william.twatter.Singletons.OAuthHandler;
import com.example.william.twatter.TwitterInfo.AccesTokenInfoHolder;
import com.github.scribejava.core.model.OAuth1RequestToken;

/**
 * This class is the actual launcher method, in here we check if the user has authorized before, if
 * they have we continue to twatterActivity, otherwise we make them log in and authorize, this will
 * redirect them to twatterActivity either way.
 */

public class MainActivity extends AppCompatActivity {
    private OAuthHandler handler = OAuthHandler.getInstance();


    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, TwatterActivity.class);
        handler.setActivity(this);

        //here we get the shared preferences.
        SharedPreferences keySets;
        keySets = getSharedPreferences("Twatter", 0);


        //here we check if the shared preferences are empty or not.
        if (!keySets.contains("ACCESS_TOKEN")) {
            WebView wv = (WebView) findViewById(R.id.slaveLabour);
            AccesTokenInfoHolder holder = handler.startOauth();
            String url = holder.getContent();
            final OAuth1RequestToken requestToken = holder.getRequestToken();
            wv.loadUrl(url);

            //this piece of code is triggered whenever the url changes
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.d("URLLOAD", url);
                    if (url.startsWith("http://placeholder.com/auth/callback")) {
                        Uri uri = Uri.parse(url);

                        //here we get the verifier from the url.
                        String verifier = uri.getQueryParameter("oauth_verifier");

                        //here we combine the requestToken and verifier so we can send it as one.
                        AccesTokenInfoHolder holder = new AccesTokenInfoHolder(requestToken, verifier);
                        Log.d("GIMMEVER", "veri");

                        //here we get the accessToken by sending over the holder.
                        Boolean accessToken = handler.getAccessToken(holder);
                        Log.d("GIMMEACCES", "acces");

                        //here we start the TwatterActivity.
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }
            });
        } else {
            Log.d("TOKEN", keySets.getString("ACCESS_TOKEN", ""));
            //here we create the new AccesToken by using it's stored info.
            handler.getAccessToken(new AccesTokenInfoHolder(null, "dummy"));
            //here we start the TwatterActivity.
            startActivity(intent);


        }
    }
}
