package com.baeldung.jetty;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jetty.server.Server;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link JettyServerFactory}.
 * 
 * @author Donato Rimenti
 *
 */
public class JettyServerFactoryUnitTest {

    /**
     * Tests that when a base server is provided a request returns a status 404.
     * 
     * @throws Exception
     */
    @Test
    public void givenBaseServer_whenHttpRequest_thenStatus404() throws Exception {
        Server server = JettyServerFactory.createBaseServer();
        server.start();

        int statusCode = sendGetRequest();

        Assert.assertEquals(404, statusCode);
        server.stop();
    }

    /**
     * Tests that when a web app server is provided a request returns a status
     * 200.
     * 
     * @throws Exception
     */
    @Test
    public void givenWebAppServer_whenHttpRequest_thenStatus200() throws Exception {
        Server server = JettyServerFactory.createWebAppServer();
        server.start();

        int statusCode = sendGetRequest();

        Assert.assertEquals(200, statusCode);
        server.stop();
    }

    /**
     * Tests that when a multi handler server is provided a request returns a
     * status 200.
     * 
     * @throws Exception
     */
    @Test
    public void givenMultiHandlerServerServer_whenHttpRequest_thenStatus200() throws Exception {
        Server server = JettyServerFactory.createMultiHandlerServer();
        server.start();

        int statusCode = sendGetRequest();

        Assert.assertEquals(200, statusCode);
        server.stop();
    }

    /**
     * Sends a default HTTP GET request to the server and returns the response
     * status code.
     * 
     * @return the status code of the response
     * @throws Exception
     */
    private int sendGetRequest() throws Exception {
        HttpHost target = new HttpHost("localhost", JettyServerFactory.SERVER_PORT);
        HttpRequest request = new HttpGet(JettyServerFactory.APP_PATH);
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(target, request);
        return response.getStatusLine().getStatusCode();
    }

}
