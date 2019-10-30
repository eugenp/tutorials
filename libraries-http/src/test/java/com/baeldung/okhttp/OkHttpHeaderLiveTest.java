package com.baeldung.okhttp;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpHeaderLiveTest {

    private static final String SAMPLE_URL = "http://www.github.com";

    OkHttpClient client;

    @Before
    public void init() {

        client = new OkHttpClient();
    }

    @Test
    public void whenSetHeader_thenCorrect() throws IOException {
        Request request = new Request.Builder().url(SAMPLE_URL).addHeader("Content-Type", "application/json").build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.close();
    }

    @Test
    public void whenSetDefaultHeader_thenCorrect() throws IOException {

        OkHttpClient clientWithInterceptor = new OkHttpClient.Builder().addInterceptor(new DefaultContentTypeInterceptor("application/json")).build();

        Request request = new Request.Builder().url(SAMPLE_URL).build();

        Call call = clientWithInterceptor.newCall(request);
        Response response = call.execute();
        response.close();
    }
}
