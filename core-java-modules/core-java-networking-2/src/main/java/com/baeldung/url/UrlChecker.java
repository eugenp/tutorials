package com.baeldung.url;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlChecker {

    public int getResponseCodeForURL(String address) throws IOException {
        return getResponseCodeForURLUsing(address, "GET");
    }

    public int getResponseCodeForURLUsingHead(String address) throws IOException {
        return getResponseCodeForURLUsing(address, "HEAD");
    }

    private int getResponseCodeForURLUsing(String address, String method) throws IOException {
        HttpURLConnection.setFollowRedirects(false); // Set follow redirects to false
        final URL url = new URL(address);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.setRequestMethod(method);
        return huc.getResponseCode();
    }

}
