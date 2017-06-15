package com.example.william.twatter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.william.twatter.TwitterInfo.AccesTokenInfoHolder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;

import static com.github.scribejava.core.model.Verb.GET;

public class MainActivity extends AppCompatActivity {
    TweetDataModel model = TweetDataModel.getInstance();
    OAuthHandler handler = OAuthHandler.getInstance();


    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, TwatterActivity.class);
        handler.setActivity(this);

        try{ handler.getKeySets().getString("ACCES_TOKEN",null);
            handler.getAccessToken(new AccesTokenInfoHolder(null,"dummy"));
            startActivity(intent);
        } catch (NullPointerException e){
            WebView wv = (WebView) findViewById(R.id.slaveLabour);
            AccesTokenInfoHolder holder = handler.startOauth();
            String url = holder.getString();
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
//                    handler.oathVerifier = resourceRequest.getUrl().toString();
//                    handler.accesTokenTask.start();
                        startActivity(intent);
                        //...
                        return true;
                    }
                    return false;
                }
            });
        }


//   handler.setAccesToken();

//        handler.beenVerified = true;
//        handler.setAccesToken();


    }
}
