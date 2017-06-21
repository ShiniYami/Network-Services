package com.example.william.twatter;

/**
 * Created by William on 6/15/2017.
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
