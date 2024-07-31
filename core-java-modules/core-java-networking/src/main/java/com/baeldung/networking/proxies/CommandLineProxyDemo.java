package com.baeldung.networking.proxies;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class CommandLineProxyDemo {

    public static final String RESOURCE_URL = "http://www.google.com";

    public static void main(String[] args) throws Exception {

        URL url = new URI(RESOURCE_URL).toURL();
        URLConnection con = url.openConnection();
        System.out.println(UrlConnectionUtils.contentAsString(con));
    }

}