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
 * This class Handles all the AsyncTasks and OAuth related calls.
 */

public class OAuthHandler {
    private static final OAuthHandler ourInstance = new OAuthHandler();

    //here we build the service that enables the existence of OAuth
    private final OAuth10aService service = new ServiceBuilder()
            .apiKey("QAwA46AAA7rw1LFz8VFg1IqlN")
            .apiSecret("lQfuIvToeG2LOkEq8uzFr3ZsjHz5pHHTmLX0wFLH6aD5Y7xFK0")
            .callback("http://placeholder.com/auth/callback")
            .build(TwitterApi.instance());
    private SharedPreferences keySets;
    private Activity activity;
    private TweetDataModel model = TweetDataModel.getInstance();
    private OAuth1AccessToken accessTokenFinal;

    public static OAuthHandler getInstance() {
        return ourInstance;
    }


    /**
     * This method helps us start the sign in process.
     * @return returns the request token and the authURL.
     */
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


    /**
     * This method gets the accessToken from the service or the sharedPreferences.
     * @param userVerified is the verifier we get from the webView.
     * @return the AccesToken.
     */
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


    /**
     * This method creates the OAuthURL.
     * @param requestToken is the given request token.
     * @return the url in string format.
     */
    public String createOathUrl(OAuth1RequestToken requestToken) {
        return service.getAuthorizationUrl(requestToken);

    }


    /**
     * This method calls the asyncTask to sign the request.
     * @param request is the request it just signed.
     * @return the signed request.
     */
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

    /**
     * This method calls the AsyncTask to send the request to twitter.
     * @param request is the request that needs to be sent to twitter
     * @param choice defines what kind of request it is.
     */
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
        //Sends the body and choice over to the TweetDataModel, so that it can add the data to the
        // model.
        model.loadResponse(body, choice);
    }

    /**
     * This method gets the requestToken from the OAuth service.
     * @return the gathered requestToken.
     */
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

    /**
     * This method calls the AsyncTask that builds the request using the given kind and url
     * provided in the RequestBuilderHelper.
     * @param helper is the given helper which contains the kind and the url.
     * @return the request which gets created in the AsyncTask.
     */
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


    /**
     * This class extends an AsyncTask and makes sure we get the URL and the requestToken from the
     * service.
     */
    public class signInTask extends AsyncTask<OAuthRequest, Integer, AccesTokenInfoHolder> {
        protected void onProgressUpdate(Integer... progress) {
        }

        @Override
        public AccesTokenInfoHolder doInBackground(OAuthRequest... params) {
            //This makes sure we get a requestToken.
            OAuth1RequestToken requestToken = setRequestToken();
            Log.d("REQTEST", requestToken.toString());
            //This creates an authURL to which we can redirect the user.
            String authUrl = createOathUrl(requestToken);
            Log.d("REQTEST2", authUrl);
            //here we store them as one so that we can return it.
            AccesTokenInfoHolder holder = new AccesTokenInfoHolder(requestToken, authUrl);
            return holder;
        }
    }

    /**
     * This class extends an AsyncTask and makes sure we create an accessToken and store it's
     * components afterwards, if it's already stored then we get the components out of the shared
     * preferences and create the accessToken with that.
     */
    public class oAuthAccessToken extends AsyncTask<AccesTokenInfoHolder, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(AccesTokenInfoHolder... params) {
            keySets = activity.getSharedPreferences("Twatter", 0);

            try {
                //here we check if we already have the required components to build the accessToken, if so we build it and ignore the rest.
                if (keySets.contains("ACCESS_TOKEN")) {
                    accessTokenFinal = new OAuth1AccessToken(keySets.getString("ACCESS_TOKEN", null), keySets.getString("ACCESS_TOKEN_SECRET", null));
                }
                //here we use the request token and the verifier to build the accessToken.
                else {
                    SharedPreferences.Editor settings = keySets.edit();
                    accessTokenFinal = service.getAccessToken(params[0].getRequestToken(), params[0].getContent());
                    //here we store the components of the accessToken in the shared preferences.
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

    /**
     * This class extends an AsyncTask and makes sure the request gets signed.
     */
    public class requestSigner extends AsyncTask<OAuthRequest, Integer, Boolean> {


        @Override
        protected Boolean doInBackground(OAuthRequest... params) {
            try {
                //here we sign the request given using the AccessToken and the request.
                service.signRequest(new OAuth1AccessToken(keySets.getString("ACCESS_TOKEN", null), keySets.getString("ACCESS_TOKEN_SECRET", null)), params[0]); // the access token from step 4
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    /**
     * This class extends an AsyncTask and makes sure we send the request, it also retrieves the
     * body from the response.
     */
    public class RequestSender extends AsyncTask<OAuthRequest, Integer, String> {
        @Override
        protected String doInBackground(OAuthRequest... params) {
            Response response;
            //here we send the request.
            response = params[0].send();
            String body = null;
            try {
                //here we get the String that contains the JSON file.
                body = response.getBody();

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return body;
        }
    }


    /**
     * This class extends an AsyncTask and makes sure we build a request.
     */
    public class RequestBuilder extends AsyncTask<RequestBuilderHelper, Integer, OAuthRequest> {
        @Override
        protected OAuthRequest doInBackground(RequestBuilderHelper... params) {
            String action = params[0].getAction();
            OAuthRequest request = null;
            //using this we can say which command we want to do and send the url, and it will build
            // the request this way.
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