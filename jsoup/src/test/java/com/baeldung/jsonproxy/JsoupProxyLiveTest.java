package com.baeldung.jsonproxy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import org.jsoup.Jsoup;
import org.junit.Test;

public class JsoupProxyLiveTest {

    @Test
    public void whenUsingHostAndPort_thenConnect() throws IOException {
        Jsoup.connect("https://spring.io/blog")
            .proxy("200.216.227.141", 53281)
            .get();
    }

    @Test
    public void whenUsingProxyClass_thenConnect() throws IOException {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("200.216.227.141", 53281));

        Jsoup.connect("https://spring.io/blog")
            .proxy(proxy)
            .get();
    }
}
