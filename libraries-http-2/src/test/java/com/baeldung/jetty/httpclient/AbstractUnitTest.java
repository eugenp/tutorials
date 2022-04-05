package com.baeldung.jetty.httpclient;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractUnitTest {

   protected static HttpClient httpClient;
   protected static Server server;
   protected static final String CONTENT = "Hello World!";
    
    protected static void startClient() {
        httpClient = new HttpClient();
        try {
            httpClient.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected static void startServer(Handler handler, int port) {
        server = new Server(port);
        server.setHandler(handler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected String uri(int port) {
        return "http://localhost:" + port;
    }

}