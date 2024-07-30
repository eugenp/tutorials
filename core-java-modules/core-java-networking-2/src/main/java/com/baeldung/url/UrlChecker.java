package com.baeldung.url;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
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
        try {
            final URL url = new URI(address).toURL();
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod(method);
            return huc.getResponseCode();
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
    }

}
