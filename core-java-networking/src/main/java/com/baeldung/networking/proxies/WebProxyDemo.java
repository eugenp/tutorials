package com.baeldung.networking.proxies;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class WebProxyDemo {

    private static final String URL_STRING = "http://www.google.com";

    public static void main(String... args) throws IOException {

        URL weburl = new URL(URL_STRING);
        Proxy webProxy 
          = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 3128));
        HttpURLConnection webProxyConnection 
            = (HttpURLConnection) weburl.openConnection(webProxy);
        System.out.println(UrlConnectionUtils.contentAsString(webProxyConnection));
    }

}
