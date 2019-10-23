package com.baeldung.okhttp;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpRedirectLiveTest {

    @Test
    public void whenSetFollowRedirects_thenNotRedirected() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().followRedirects(false).build();

        Request request = new Request.Builder().url("http://t.co/I5YYd9tddw").build();

        Call call = client.newCall(request);
        Response response = call.execute();

        assertThat(response.code(), equalTo(301));
    }
}
