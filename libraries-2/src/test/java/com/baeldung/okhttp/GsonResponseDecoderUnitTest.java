package com.baeldung.okhttp;

import com.baeldung.okhttp.gson.GsonResponseDecoder;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GsonResponseDecoderUnitTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Rule
    public MockWebServer server = new MockWebServer();

    private OkHttpClient client;

    private Gson gson = new Gson();

    @Before
    public void setUp() {
        client = new OkHttpClient.Builder()
                .build();
    }

    @Test
    public void whenReceivedSuccessfulResponse_thenExpectSimpleEntity() throws Exception {

        SimpleEntity mockResponse = new SimpleEntity("Baeldung");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(gson.toJson(mockResponse)));

        Request request = new Request.Builder()
                .url(server.url(""))
                .build();
        SimpleEntity response = new GsonResponseDecoder(gson)
                .decode(client.newCall(request).execute()
                        , SimpleEntity.class);

        Assert.assertEquals(mockResponse.getName(), response.getName());
    }

    @Test
    public void whenReceiveEmptyResponse_thenExpectDecodeException() throws Exception {

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(""));

        exceptionRule.expect(DecodeException.class);
        exceptionRule.expectMessage("Empty Response");

        Request request = new Request.Builder()
                .url(server.url(""))
                .build();
        new GsonResponseDecoder(gson)
                .decode(client.newCall(request).execute()
                        , SimpleEntity.class);

    }

    @Test
    public void whenReceiveNonEmptyErrorResponse_thenExpectSimpleEntity() throws Exception {
        SimpleEntity mockResponse = new SimpleEntity("Baeldung");
        server.enqueue(new MockResponse()
                .setResponseCode(400)
                .setHeader("Content-Type", "application/json")
                .setBody(gson.toJson(mockResponse)));

        Request request = new Request.Builder()
                .url(server.url(""))
                .build();
        SimpleEntity response = new GsonResponseDecoder(gson)
                .decode(client.newCall(request).execute(), SimpleEntity.class);

        Assert.assertEquals(mockResponse.getName(), response.getName());

    }


}
