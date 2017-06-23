package com.example.william.twatter.Helper;

/**
 * This class holds the action of the request and the url of the request.
 */

public class RequestBuilderHelper {
    private String action;
    private String url;

    public RequestBuilderHelper(String action, String url) {
        this.action = action;
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public String getUrl() {
        return url;
    }
}
