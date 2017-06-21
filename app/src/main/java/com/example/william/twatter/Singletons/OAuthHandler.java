package com.example.william.twatter.Singletons;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.william.twatter.Helper.RequestBuilderHelper;
import com.example.william.twatter.TwitterInfo.AccesTokenInfoHolder;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by William on 6/7/2017.
 */

public class OAuthHandler {
    private static final OAuthHandler ourInstance = new OAuthHandler();
    private final OAuth10aService service = new ServiceBuilder()
            .apiKey("QAwA46AAA7rw1LFz8VFg1IqlN")
            .apiSecret("lQfuIvToeG2LOkEq8uzFr3ZsjHz5pHHTmLX0wFLH6aD5Y7xFK0")
            .callback("http://placeholder.com/auth/callback")
            .build(TwitterApi.instance());
    Response response;
    private SharedPreferences keySets;
    private Activity activity;
    private TweetDataModel model = TweetDataModel.getInstance();
    private OAuth1AccessToken accessTokenFinal;


    public OAuthHandler() {
    }

    public static OAuthHandler getInstance() {
        return ourInstance;
    }

    public SharedPreferences getKeySets() {
        return keySets;
    }

    public AccesTokenInfoHolder startOauth() {
        try {
            return new signInTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean getAccessToken(AccesTokenInfoHolder userVerified) {
        try {
            Boolean accessToken = new oAuthAccessToken().execute(userVerified).get();
            return accessToken;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String createOathUrl(OAuth1RequestToken requestToken) {
        return service.getAuthorizationUrl(requestToken);

    }

    public OAuthRequest signRequest(OAuthRequest request) {
        boolean signedRequest = false;
        try {
            signedRequest = new requestSigner().execute(request).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return request;
    }

    public void sendRequest(OAuthRequest request, int choice) {
        String body = null;
        Log.d("TEST111", "hi");
        try {

            body = new RequestSender().execute(request).get();
            Log.d("TEST211", "hi");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("TEST111", body);
        Log.d("TEST111", choice + "");
        model = TweetDataModel.getInstance();
        model.loadResponse(body, choice);
    }

    private OAuth1RequestToken setRequestToken() {
        OAuth1RequestToken requestToken = null;
        try {
            requestToken = service.getRequestToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestToken;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public OAuthRequest makeRequest(RequestBuilderHelper helper) {
        try {
            return new RequestBuilder().execute(helper).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class signInTask extends AsyncTask<OAuthRequest, Integer, AccesTokenInfoHolder> {
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        public AccesTokenInfoHolder doInBackground(OAuthRequest... params) {

            OAuth1RequestToken requestToken = setRequestToken();
            Log.d("REQTEST", requestToken.toString());
            String authUrl = createOathUrl(requestToken);
            Log.d("REQTEST2", authUrl);
            AccesTokenInfoHolder holder = new AccesTokenInfoHolder(requestToken, authUrl);
            return holder;
        }
    }

    public class oAuthAccessToken extends AsyncTask<AccesTokenInfoHolder, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(AccesTokenInfoHolder... params) {
            keySets = activity.getSharedPreferences("Twatter", 0);

            try {
                if (keySets.contains("ACCESS_TOKEN")) {
                    accessTokenFinal = new OAuth1AccessToken(keySets.getString("ACCESS_TOKEN", null), keySets.getString("ACCESS_TOKEN_SECRET", null));
                } else {
                    SharedPreferences.Editor settings = keySets.edit();
                    accessTokenFinal = service.getAccessToken(params[0].getRequestToken(), params[0].getContent());
                    settings.putString("ACCESS_TOKEN", accessTokenFinal.getToken());
                    settings.putString("ACCESS_TOKEN_SECRET", accessTokenFinal.getTokenSecret());
                    settings.apply();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
    }

    public class requestSigner extends AsyncTask<OAuthRequest, Integer, Boolean> {


        @Override
        protected Boolean doInBackground(OAuthRequest... params) {
            try {
                service.signRequest(new OAuth1AccessToken(keySets.getString("ACCESS_TOKEN", null), keySets.getString("ACCESS_TOKEN_SECRET", null)), params[0]); // the access token from step 4
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    public class RequestSender extends AsyncTask<OAuthRequest, Integer, String> {
        @Override
        protected String doInBackground(OAuthRequest... params) {
            Response response;
            response = params[0].send();
            String body = null;
            try {
                body = response.getBody();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return body;
        }
    }

    public class RequestBuilder extends AsyncTask<RequestBuilderHelper, Integer, OAuthRequest> {
        @Override
        protected OAuthRequest doInBackground(RequestBuilderHelper... params) {
            String action = params[0].getAction();
            OAuthRequest request = null;
            if (action.equals("GET")) {
                request = new OAuthRequest(Verb.GET, params[0].getUrl(), service);
            }
            if (action.equals("PUT")) {
                request = new OAuthRequest(Verb.PUT, params[0].getUrl(), service);
            }
            if (action.equals("POST")) {
                request = new OAuthRequest(Verb.POST, params[0].getUrl(), service);
            }
            if (action.equals("DELETE")) {
                request = new OAuthRequest(Verb.DELETE, params[0].getUrl(), service);
            }
            return request;
        }


    }

}