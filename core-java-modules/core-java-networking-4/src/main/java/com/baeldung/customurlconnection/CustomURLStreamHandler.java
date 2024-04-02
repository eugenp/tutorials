package com.baeldung.customurlconnection;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class CustomURLStreamHandler extends URLStreamHandler {

    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        // Create and return an instance of CustomURLConnection
        return new CustomURLConnection(u);
    }
}