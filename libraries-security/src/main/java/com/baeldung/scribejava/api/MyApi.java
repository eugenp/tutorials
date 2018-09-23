package com.baeldung.scribejava.api;

import com.github.scribejava.core.builder.api.DefaultApi20;

public class MyApi extends DefaultApi20 {

    private MyApi() {
    }

    private static class InstanceHolder {
        private static final MyApi INSTANCE = new MyApi();
    }

    public static MyApi instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "http://localhost:8080/oauth/token";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return null;
    }
}
