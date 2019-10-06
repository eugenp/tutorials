package com.baeldung.okhttp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.InputStreamReader;

public class ResponseDecoderUnitTest {

    @Rule public ExpectedException exceptionRule = ExpectedException.none();

    @Rule public MockWebServer server = new MockWebServer();

    SimpleEntity sampleResponse;

    MockResponse mockResponse;

    OkHttpClient client;

    @Before
    public void setUp() {
        sampleResponse = new SimpleEntity("Baeldung");
        client = new OkHttpClient.Builder().build();
        mockResponse = new MockResponse()
          .setResponseCode(200)
          .setHeader("Content-Type", "application/json")
          .setBody(new Gson().toJson(sampleResponse));
    }

    @Test
    public void givenJacksonDecoder_whenGetStringOfResponse_thenExpectSimpleEntity() throws Exception {
        server.enqueue(mockResponse);
        Request request = new Request.Builder()
          .url(server.url(""))
          .build();
        ResponseBody responseBody = client
          .newCall(request)
          .execute()
          .body();

        Assert.assertNotNull(responseBody);
        Assert.assertNotEquals(0, responseBody.contentLength());

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleEntity entity = objectMapper.readValue(responseBody.string(), SimpleEntity.class);

        Assert.assertNotNull(entity);
        Assert.assertEquals(sampleResponse.getName(), entity.getName());
    }

    @Test
    public void givenGsonDecoder_whenGetByteStreamOfResponse_thenExpectSimpleEntity() throws Exception {
        server.enqueue(mockResponse);
        Request request = new Request.Builder()
          .url(server.url(""))
          .build();
        ResponseBody responseBody = client
          .newCall(request)
          .execute()
          .body();

        Assert.assertNotNull(responseBody);
        Assert.assertNotEquals(0, responseBody.contentLength());

        Gson gson = new Gson();
        SimpleEntity entity = gson.fromJson(new InputStreamReader(responseBody.byteStream()), SimpleEntity.class);

        Assert.assertNotNull(entity);
        Assert.assertEquals(sampleResponse.getName(), entity.getName());
    }

    @Test
    public void givenGsonDecoder_whenGetStringOfResponse_thenExpectSimpleEntity() throws Exception {
        server.enqueue(mockResponse);
        Request request = new Request.Builder()
          .url(server.url(""))
          .build();
        ResponseBody responseBody = client
          .newCall(request)
          .execute()
          .body();

        Assert.assertNotNull(responseBody);

        Gson gson = new Gson();
        SimpleEntity entity = gson.fromJson(responseBody.string(), SimpleEntity.class);

        Assert.assertNotNull(entity);
        Assert.assertEquals(sampleResponse.getName(), entity.getName());
    }

}
