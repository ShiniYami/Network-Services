package com.example.william.twatter.TwitterInfo;

import com.github.scribejava.core.model.OAuth1RequestToken;

/**
 * Created by William on 6/15/2017.
 */

public class AccesTokenInfoHolder {

    OAuth1RequestToken requestToken;
    String string;

    public AccesTokenInfoHolder(OAuth1RequestToken requestToken, String string) {
        this.requestToken = requestToken;
        this.string = string;
    }

    public OAuth1RequestToken getRequestToken() {
        return requestToken;
    }

    public String getString() {
        return string;
    }
}
