package com.baeldung.networking.proxies;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class DirectProxyDemo {

    private static final String URL_STRING = "http://www.google.com";

    public static void main(String... args) throws IOException, URISyntaxException {

        URL weburl = new URI(URL_STRING).toURL();
        HttpURLConnection directConnection 
          = (HttpURLConnection) weburl.openConnection(Proxy.NO_PROXY);
        System.out.println(UrlConnectionUtils.contentAsString(directConnection));
    }

}
