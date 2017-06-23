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

public class MainActivity extends AppCompatActivity {
    private OAuthHandler handler = OAuthHandler.getInstance();


    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, TwatterActivity.class);
        handler.setActivity(this);
        SharedPreferences keySets;
        keySets = getSharedPreferences("Twatter", 0);

        if (!keySets.contains("ACCESS_TOKEN")) {
            WebView wv = (WebView) findViewById(R.id.slaveLabour);
            AccesTokenInfoHolder holder = handler.startOauth();
            String url = holder.getContent();
            final OAuth1RequestToken requestToken = holder.getRequestToken();
            wv.loadUrl(url);
            wv.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.d("URLLOAD", url);
                    if (url.startsWith("http://placeholder.com/auth/callback")) {
                        Uri uri = Uri.parse(url);
                        String verifier = uri.getQueryParameter("oauth_verifier");
                        AccesTokenInfoHolder holder = new AccesTokenInfoHolder(requestToken, verifier);
                        Log.d("GIMMEVER", "veri");
                        Boolean accessToken = handler.getAccessToken(holder);
                        Log.d("GIMMEACCES", "acces");
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }
            });
        } else {
            Log.d("TOKEN", keySets.getString("ACCESS_TOKEN", ""));
            handler.getAccessToken(new AccesTokenInfoHolder(null, "dummy"));
            startActivity(intent);


        }
    }
}
