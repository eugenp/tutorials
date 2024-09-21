package com.baeldung.asynchttpclient;

import io.netty.handler.codec.http.HttpHeaders;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.AsyncHttpClientConfig;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Request;
import org.asynchttpclient.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AsyncHttpClientLiveTest {

    private static AsyncHttpClient HTTP_CLIENT;

    @Before
    public void setup() {
        AsyncHttpClientConfig clientConfig = Dsl.config().setConnectTimeout(15000).setRequestTimeout(15000).build();
        HTTP_CLIENT = Dsl.asyncHttpClient(clientConfig);
    }

    @Test
    public void givenHttpClient_executeSyncGetRequest() {

        BoundRequestBuilder boundGetRequest = HTTP_CLIENT.prepareGet("http://www.baeldung.com");

        Future<Response> responseFuture = boundGetRequest.execute();
        try {
            Response response = responseFuture.get(5000, TimeUnit.MILLISECONDS);
            assertNotNull(response);
            assertEquals(200, response.getStatusCode());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenHttpClient_executeAsyncGetRequest() {

        // execute an unbound GET request
        Request unboundGetRequest = Dsl.get("http://www.baeldung.com").build();

        HTTP_CLIENT.executeRequest(unboundGetRequest, new AsyncCompletionHandler<Integer>() {
            @Override
            public Integer onCompleted(Response response) {

                int resposeStatusCode = response.getStatusCode();
                assertEquals(200, resposeStatusCode);
                return resposeStatusCode;
            }
        });

        // execute a bound GET request
        BoundRequestBuilder boundGetRequest = HTTP_CLIENT.prepareGet("http://www.baeldung.com");

        boundGetRequest.execute(new AsyncCompletionHandler<Integer>() {
            @Override
            public Integer onCompleted(Response response) {
                int resposeStatusCode = response.getStatusCode();
                assertEquals(200, resposeStatusCode);
                return resposeStatusCode;
            }
        });

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenHttpClient_executeAsyncGetRequestWithAsyncHandler() {

        // execute an unbound GET request
        Request unboundGetRequest = Dsl.get("http://www.baeldung.com").build();

        HTTP_CLIENT.executeRequest(unboundGetRequest, new AsyncHandler<Integer>() {

            int responseStatusCode = -1;

            @Override
            public State onStatusReceived(HttpResponseStatus responseStatus) {
                responseStatusCode = responseStatus.getStatusCode();
                return State.CONTINUE;
            }

            @Override
            public State onHeadersReceived(HttpHeaders headers) {
                return State.CONTINUE;
            }

            @Override
            public State onBodyPartReceived(HttpResponseBodyPart bodyPart) {
                return State.CONTINUE;
            }

            @Override
            public void onThrowable(Throwable t) {

            }

            @Override
            public Integer onCompleted() {
                assertEquals(200, responseStatusCode);
                return responseStatusCode;
            }
        });

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenHttpClient_executeAsyncGetRequestWithListanableFuture() {
        // execute an unbound GET request
        Request unboundGetRequest = Dsl.get("http://www.baeldung.com").build();

        ListenableFuture<Response> listenableFuture = HTTP_CLIENT.executeRequest(unboundGetRequest);
        listenableFuture.addListener(() -> {
            Response response;
            try {
                response = listenableFuture.get(5000, TimeUnit.MILLISECONDS);
                assertEquals(200, response.getStatusCode());
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }, Executors.newCachedThreadPool());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
