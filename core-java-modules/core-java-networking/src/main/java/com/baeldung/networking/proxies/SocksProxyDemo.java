package com.baeldung.networking.proxies;

import java.io.IOException;
import java.net.*;

public class SocksProxyDemo {

    private static final String URL_STRING = "http://www.google.com";
    private static final String SOCKET_SERVER_HOST = "someserver.baeldung.com";
    private static final int SOCKET_SERVER_PORT = 1111;

    public static void main(String... args) throws Exception {

        URL weburl = new URI(URL_STRING).toURL();
        Proxy socksProxy 
          = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080));
        HttpURLConnection socksConnection 
          = (HttpURLConnection) weburl.openConnection(socksProxy);
        System.out.println(UrlConnectionUtils.contentAsString(socksConnection));

        Socket proxySocket = new Socket(socksProxy);
        InetSocketAddress socketHost 
          = new InetSocketAddress(SOCKET_SERVER_HOST, SOCKET_SERVER_PORT);
        proxySocket.connect(socketHost);
        // do stuff with the socket
    }

}
