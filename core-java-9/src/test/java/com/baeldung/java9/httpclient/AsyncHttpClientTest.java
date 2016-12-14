package com.baeldung.java9.httpclient;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.HttpStatusCode.OK_200;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyProcessor;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import org.slf4j.LoggerFactory;

import org.junit.Assert;

import org.slf4j.Logger;

public class AsyncHttpClientTest {

	private static final Logger logger = LoggerFactory.getLogger(AsyncHttpClientTest.class);
    private static final String serverHost = "localhost";
    private static final int serverPort = 8080;
    private static final String baseURL = "http://" + serverHost + ":" + serverPort;
    private static final String requestBody = "{username: 'foo', password: 'bar'}";

    private Iterator<URI> uriIterator;
    private ClientAndServer mockServer;
    private HttpClient httpClient;
    
    @Before
    public void init() throws URISyntaxException {
        ArrayList<URI> URIs = new ArrayList<>();
        URIs.add(new URI(baseURL + "/login"));
        URIs.add(new URI(baseURL + "/java"));
        URIs.add(new URI(baseURL + "/http"));
        URIs.add(new URI(baseURL + "/java"));
        URIs.add(new URI(baseURL + "/http"));
        uriIterator = URIs.iterator();
        
        HttpClient.Builder hcBuilder = HttpClient.create();
        this.httpClient = hcBuilder.followRedirects(Redirect.ALWAYS).build();

        setupMockHttpServer();
    }

    @Test
    public void asyncResponseBody() throws IOException, InterruptedException, URISyntaxException, ExecutionException{
    	Path file = Paths.get(".", "responses");
    	HttpResponse response = httpClient
    			.request(new URI(baseURL + "/http"))
    			.GET()
    			.response();
		CompletableFuture<Path> futureResponseWrite =  response.bodyAsync(HttpResponse.asFile(file));
		// do some other stuff
		Path res = futureResponseWrite.get();
		Assert.assertTrue(res.getNameCount() > 0);
    }
    
    @Test
    public void asynchronousRequests() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        long before = System.currentTimeMillis();
        logger.info("@@@@@  "+ before);       
        CompletableFuture<Void> futureResponses = httpClient
                .request(uriIterator.next())
                .body(HttpRequest.fromString(requestBody))
                .POST()
                .responseAsync()
                .thenComposeAsync(this::checkResponseAndFireRequest);
 
        futureResponses.join();        
        logger.info("End. Elapsed "+ (System.currentTimeMillis() - before) + " ms.");

    }

    private CompletableFuture<Void> checkResponseAndFireRequest(HttpResponse response) {
        int respCode = response.statusCode();
        if (respCode == 200) {
        	logger.info("Success body is [" +  response.body(HttpResponse.asString()) + "]");

            CompletableFuture<Void> download1 = httpClient
            		.request(uriIterator.next())
            		.GET()
            		.responseAsync()
            		.thenAcceptAsync(AsyncHttpClientTest::processResponseBody);          
            CompletableFuture<Void> download2 = httpClient
            		.request(uriIterator.next())
            		.GET()
            		.responseAsync()
            		.thenAcceptAsync(AsyncHttpClientTest::processResponseBody);
            CompletableFuture<Void> download3 = httpClient
            		.request(uriIterator.next())
            		.GET()
            		.responseAsync()
            		.thenAcceptAsync(AsyncHttpClientTest::processResponseBody);
            CompletableFuture<Void> download4 = httpClient
            		.request(uriIterator.next())
            		.GET()
            		.responseAsync()
            		.thenAcceptAsync(AsyncHttpClientTest::processResponseBody);
            return CompletableFuture.allOf(download1, download2, download3, download4);
        } else {
            CompletableFuture<Void> error = new CompletableFuture<>();
            error.completeExceptionally(new Exception("Bad http response code " + response.statusCode()));
            return error;
        }
    }

    private static String processResponseBody(HttpResponse response) {
        String responseBody = response.body(HttpResponse.asString());
        String status;
        if (!responseBody.contains("message")) {
            status = "FAIL";
        } else {
            status = "SUCCESS";
        }
        // intensive long operation for parsing body
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        logger.info("Status is " + status);
        return status;
    }


    private void setupMockHttpServer() {
        mockServer = startClientAndServer(serverPort);
        
        MockServerClient client = new MockServerClient("127.0.0.1", serverPort);
        client.when(request()
                        .withMethod("POST")
                        .withPath("/login")
                        .withBody(requestBody)
                    )
              .respond(response()
                        .withStatusCode(OK_200.code())
                        .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"), new Header("Cache-Control", "public, max-age=86400"))
                        .withBody("{ message: 'Successful login' }")
                        .withDelay(new Delay(MILLISECONDS, 10)
                       )
               );
        client.when(request()
                .withMethod("GET")
                .withPath("/java")
                )
              .respond(response()
                      .withStatusCode(OK_200.code())
                      .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"), new Header("Cache-Control", "public, max-age=86400"))
                      .withBody("{ message: 'The Java Stuff' }")
                      .withDelay(new Delay(MILLISECONDS, 3000)
                      )
              );
        
        client.when(request()
                .withMethod("GET")
                .withPath("/http")
                )
              .respond(response()
                      .withStatusCode(OK_200.code())
                      .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"), new Header("Cache-Control", "public, max-age=86400"))
                      .withBody("{ message: 'The Http Coolest Stuff' }")
                      .withDelay(new Delay(MILLISECONDS, 3000)
                      )
              );
    }

    @After
    public void stopProxy() {
        mockServer.stop();
    }
}
