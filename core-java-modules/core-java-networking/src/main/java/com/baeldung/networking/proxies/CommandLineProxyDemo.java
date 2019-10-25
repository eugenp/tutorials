package com.baeldung.networking.proxies;

import java.net.URL;
import java.net.URLConnection;

public class CommandLineProxyDemo {

    public static final String RESOURCE_URL = "http://www.google.com";

    public static void main(String[] args) throws Exception {

        URL url = new URL(RESOURCE_URL);
        URLConnection con = url.openConnection();
        System.out.println(UrlConnectionUtils.contentAsString(con));
    }

}