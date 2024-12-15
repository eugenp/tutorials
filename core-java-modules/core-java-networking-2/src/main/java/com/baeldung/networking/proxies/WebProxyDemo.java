package com.baeldung.networking.proxies;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;

public class WebProxyDemo {

    private static final String URL_STRING = "http://www.google.com";

    public static void main(String... args) throws Exception {

        URL weburl = new URI(URL_STRING).toURL();
        Proxy webProxy 
          = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 3128));
        HttpURLConnection webProxyConnection 
            = (HttpURLConnection) weburl.openConnection(webProxy);
        System.out.println(UrlConnectionUtils.contentAsString(webProxyConnection));
    }

}
