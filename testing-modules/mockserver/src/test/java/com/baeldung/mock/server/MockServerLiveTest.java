package com.baeldung.mock.server;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpForward;
import org.mockserver.verify.VerificationTimes;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpClassCallback.callback;
import static org.mockserver.model.HttpForward.forward;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

public class MockServerLiveTest {

    private static ClientAndServer mockServer;

    @BeforeClass
    public static void startServer() {
        mockServer = startClientAndServer(1080);
    }

    @Test
    public void whenPostRequestMockServer_thenServerReceived() {
        createExpectationForInvalidAuth();
        hitTheServerWithPostRequest();
        verifyPostRequest();
    }

    @Test
    public void whenPostRequestForInvalidAuth_then401Received() {
        createExpectationForInvalidAuth();
        HttpResponse response = hitTheServerWithPostRequest();
        assertEquals(401, response.getStatusLine()
            .getStatusCode());
    }

    @Test
    public void whenGetRequest_ThenForward() {
        createExpectationForForward();
        hitTheServerWithGetRequest("index.html");
        verifyGetRequest();

    }

    private void verifyPostRequest() {
        new MockServerClient("localhost", 1080).verify(request().withMethod("POST")
            .withPath("/validate")
            .withBody(json("{\"username\": \"foo\", \"password\": \"bar\"}")), VerificationTimes.exactly(1));
    }

    private void verifyGetRequest() {
        new MockServerClient("localhost", 1080).verify(request().withMethod("GET")
            .withPath("/index.html"), VerificationTimes.exactly(1));
    }

    private HttpResponse hitTheServerWithPostRequest() {
        String url = "http://127.0.0.1:1080/validate";
        HttpClient client = HttpClientBuilder.create()
            .build();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/json");
        HttpResponse response = null;

        try {
            StringEntity stringEntity = new StringEntity("{username: 'foo', password: 'bar'}");
            post.getRequestLine();
            post.setEntity(stringEntity);
            response = client.execute(post);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    private HttpResponse hitTheServerWithGetRequest(String page) {
        String url = "http://127.0.0.1:1080/" + page;
        HttpClient client = HttpClientBuilder.create()
            .build();
        org.apache.http.HttpResponse response = null;
        HttpGet get = new HttpGet(url);
        try {
            response = client.execute(get);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    private void createExpectationForInvalidAuth() {
        new MockServerClient("127.0.0.1", 1080).when(request().withMethod("POST")
                .withPath("/validate")
                .withHeader("Content-type", "application/json")
                .withBody(json("{\"username\": \"foo\", \"password\": \"bar\"}")), exactly(1))
            .respond(response().withStatusCode(401)
                .withHeaders(new Header("Content-Type", "application/json; charset=utf-8"), new Header("Cache-Control", "public, max-age=86400"))
                .withBody("{ message: 'incorrect username and password combination' }")
                .withDelay(TimeUnit.SECONDS, 1));
    }

    private void createExpectationForForward() {
        new MockServerClient("127.0.0.1", 1080).when(request().withMethod("GET")
                .withPath("/index.html"), exactly(1))
            .forward(forward().withHost("www.mock-server.com")
                .withPort(80)
                .withScheme(HttpForward.Scheme.HTTP));
    }

    private void createExpectationForCallBack() {
        mockServer.when(request().withPath("/callback"))
            .respond(callback().withCallbackClass("com.baeldung.mock.server.ExpectationCallbackHandler"));
    }

    @AfterClass
    public static void stopServer() {
        mockServer.stop();
    }
}
