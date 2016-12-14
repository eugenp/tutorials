package com.baeldung.java9.httpclient;

import org.junit.After;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.LongConsumer;
import java.util.stream.Stream;

import javax.security.auth.login.LoginException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndProxy;
import org.mockserver.integration.ClientAndServer;

import static org.mockserver.integration.ClientAndProxy.startClientAndProxy;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.HttpForward.forward;
import static org.mockserver.model.Header.header;
import static org.mockserver.model.HttpResponse.notFoundResponse;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.matchers.Times.exactly;
import static java.util.concurrent.TimeUnit.*;
import static org.mockserver.model.HttpForward.Scheme.HTTP;
import static org.mockserver.model.HttpStatusCode.*;
import org.mockserver.model.Delay;
import org.mockserver.model.Header;
import org.mockserver.model.NottableString;
import org.mockserver.model.Parameter;
import static java.util.concurrent.TimeUnit.SECONDS;

public class AsyncHttpClientTest {

    private static final int size = 3;
    private ArrayList<URI> URIs = new ArrayList<>();
    private Iterator<URI> uriIterator;
    private String status;
    private HttpRequest[] requests = new HttpRequest[size];
    private CompletableFuture<HttpResponse>[] futureResponses = new CompletableFuture[size];
    private static final AtomicInteger counter = new AtomicInteger(0);
    // private ClientAndProxy proxy;
    private ClientAndServer mockServer;
    private static final String serverHost = "localhost";
    private static final int serverPort = 8080;
    private static final String baseURL = "http://" + serverHost + ":" + serverPort;

    private static final String requestBody = "{username: 'foo', password: 'bar'}";

    @Before
    public void init() throws URISyntaxException {
        URIs.add(new URI(baseURL + "/login"));
        URIs.add(new URI(baseURL + "/java"));
        URIs.add(new URI(baseURL + "/http"));
        uriIterator = URIs.iterator();

        setupMockHttpServer();
    }

    @Test
    public void asynchronousGet() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        long before = System.currentTimeMillis();
//        CompletableFuture<HttpResponse> futureResponses1 = HttpRequest
//                .create(uriIterator.next())
//                .body(HttpRequest.fromString(requestBody))
//                .POST()
//                .responseAsync();
//        
        
        CompletableFuture<HttpResponse> futureResponses0 = HttpRequest
                .create(uriIterator.next())
                .body(HttpRequest.fromString(requestBody))
                .POST()
                .responseAsync();
                // CompletableFuture<HttpResponse> futureResponses1 = futureResponses0
                //.thenCombineAsync(futureResponses1, this::checkResponseAndFireRequest)
               CompletableFuture<Void> download1 = futureResponses0
                    .thenComposeAsync(this::checkResponseAndFireRequest)
                    .thenAccept(this::processResponseBody);
               CompletableFuture<Void> download2 = futureResponses0
                    .thenComposeAsync(this::checkResponseAndFireRequest)
                    .thenAccept(this::processResponseBody);
        // futureResponses1
        //futureResponses0.thenAccept(this::processResponseBody).get();
        
        Thread.sleep(2000);
        System.out.println("End.");

        download1.get();
        download2.get();
        
        // for(int i=0; i< URIs.length; i++){
        // futureResponses[i] = HttpRequest.create(URIs[i]).GET().responseAsync().exceptionally(this::processException);
        //
        // //futureResponses[i].get();
        // }
        // futureResponses[0].thenAccept(this::processResponse).

        // futureResponses[0].exceptionally(this::processException)
        // .thenAccept(this::processResponse);

        // HttpResponse resp = futureResponses[0].get();
        // HttpHeaders hs = resp.headers();
        // assertTrue("There should be more then 1 header.", hs.map().size() > 1);
    }

    private CompletableFuture<HttpResponse> checkResponseAndFireRequest(HttpResponse response) {
        System.out.println(Thread.currentThread());
        if (response.statusCode() == 200) {
            System.out.println("Success "+ Thread.currentThread() + 
                    "---" + response.body(HttpResponse.asString()));
            int c = counter.incrementAndGet();
            return HttpRequest.create(uriIterator.next()).GET().responseAsync();
        } else {
            CompletableFuture<HttpResponse> error = new CompletableFuture<>();
            error.completeExceptionally(new Exception("Bad http response code " + response.statusCode()));
            return error;
        }
    }

    private void processResponseBody(HttpResponse response) {
        String responseBody = response.body(HttpResponse.asString());
        if (!responseBody.contains("Success")) {
            status = "FAIL";
        } else {
            status = "SUCCESS";
        }
        System.out.println("Status is " + status+ "---" + Thread.currentThread());
    }

    private HttpResponse processException(Throwable th) {
        System.out.println("Exception Caught");
        th.printStackTrace();
        return null;
    }

    @After
    public void stopProxy() {
        // proxy.stop();
        mockServer.stop();
    }

    private void setupMockHttpServer() {
        mockServer = startClientAndServer(serverPort);
        // proxy = startClientAndProxy(serverPort + 10);

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
                        .withDelay(new Delay(SECONDS, 1)
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
                      .withDelay(new Delay(MILLISECONDS, 2500)
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
                      .withDelay(new Delay(MILLISECONDS, 50)
                      )
              );
    }

}
