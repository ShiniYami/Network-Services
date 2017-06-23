package com.example.william.twatter.TwitterInfo;

import com.github.scribejava.core.model.OAuth1RequestToken;

/**
 * This class holds the given request token and String, this is used for the signInTask.
 */

public class AccesTokenInfoHolder {

    OAuth1RequestToken requestToken;
    String content;

    public AccesTokenInfoHolder(OAuth1RequestToken requestToken, String content) {
        this.requestToken = requestToken;
        this.content = content;
    }

    public OAuth1RequestToken getRequestToken() {
        return requestToken;
    }

    public String getContent() {
        return content;
    }
}
