package com.baeldung.mock.url;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlFetcher {

    private URL url;

    public UrlFetcher(URL url) throws IOException {
        this.url = url;
    }

    public boolean isUrlAvailable() throws IOException {
        return getResponseCode() == HttpURLConnection.HTTP_OK;
    }

    private int getResponseCode() throws IOException {
        HttpURLConnection con = (HttpURLConnection) this.url.openConnection();
        return con.getResponseCode();
    }
}
