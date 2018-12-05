package com.baeldung.networking.proxies;

import java.net.URL;
import java.net.URLConnection;

public class SystemPropertyProxyDemo {

    public static final String RESOURCE_URL = "http://www.google.com";

    public static void main(String[] args) throws Exception {

        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "3128");

        URL url = new URL(RESOURCE_URL);
        URLConnection con = url.openConnection();
        System.out.println(UrlConnectionUtils.contentAsString(con));

        System.setProperty("http.proxyHost", null);
        // proxy will no longer be used for http connections
    }

}
