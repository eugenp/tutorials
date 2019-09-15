package com.baeldung.jetty.httpclient;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;


public abstract class AbstractTest {

    protected HttpClient httpClient;
    protected Server server;
    protected static final String CONTENT = "Hello World!";
    
    @Before
    public void init() {
        startServer(new RequestHandler());
        startClient();
    }

    private void startClient() {
        httpClient = new HttpClient();
        try {
            httpClient.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void startServer(Handler handler) {
        server = new Server(8080);
        server.setHandler(handler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void dispose() throws Exception {
        if (httpClient != null) {
            httpClient.stop();
        }
        if (server != null) {
            server.stop();
        }
    }

    protected String uri() {
        return "http://localhost:8080";
    }
}
