package com.baeldung.okhttp;

import com.baeldung.okhttp.jackson.JacksonResponseDecoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class JacksonResponseDecoderUnitTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Rule
    public MockWebServer server = new MockWebServer();

    private OkHttpClient client;

    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        client = new OkHttpClient.Builder()
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void whenReceivedSuccessfulResponse_thenExpectSimpleEntity() throws Exception {

        SimpleEntity mockResponse = new SimpleEntity("Baeldung");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
                .setBody(objectMapper.writeValueAsString(mockResponse)));

        Request request = new Request.Builder()
                .url(server.url(""))
                .build();
        SimpleEntity response = new JacksonResponseDecoder(objectMapper)
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
        new JacksonResponseDecoder(objectMapper)
                .decode(client.newCall(request).execute()
                        , SimpleEntity.class);

    }

    @Test
    public void whenReceiveNonEmptyErrorResponse_thenExpectSimpleEntity() throws Exception {
        SimpleEntity mockResponse = new SimpleEntity("Baeldung");
        server.enqueue(new MockResponse()
                .setResponseCode(400)
                .setHeader("Content-Type", "application/json")
                .setBody(objectMapper.writeValueAsString(mockResponse)));

        Request request = new Request.Builder()
                .url(server.url(""))
                .build();
        SimpleEntity response = new JacksonResponseDecoder(objectMapper)
                .decode(client.newCall(request).execute(), SimpleEntity.class);

        Assert.assertEquals(mockResponse.getName(), response.getName());

    }


}
