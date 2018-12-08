package com.baeldung.okhttp;

import static com.baeldung.client.Consts.APPLICATION_PORT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.junit.Before;
import org.junit.Test;

public class OkHttpGetLiveTest {

    private static final String BASE_URL = "http://localhost:" + APPLICATION_PORT + "/spring-rest";

    OkHttpClient client;

    @Before
    public void init() {

        client = new OkHttpClient();
    }

    @Test
    public void whenGetRequest_thenCorrect() throws IOException {
        final Request request = new Request.Builder().url(BASE_URL + "/date").build();

        final Call call = client.newCall(request);
        final Response response = call.execute();

        assertThat(response.code(), equalTo(200));
    }

    @Test
    public void whenGetRequestWithQueryParameter_thenCorrect() throws IOException {
        final HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "/ex/bars").newBuilder();
        urlBuilder.addQueryParameter("id", "1");

        final String url = urlBuilder.build().toString();

        final Request request = new Request.Builder().url(url).build();

        final Call call = client.newCall(request);
        final Response response = call.execute();

        assertThat(response.code(), equalTo(200));
    }

    @Test
    public void whenAsynchronousGetRequest_thenCorrect() throws InterruptedException {
        final Request request = new Request.Builder().url(BASE_URL + "/date").build();

        final Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("OK");
            }

            @Override
            public void onFailure(Call call, IOException e) {
                fail();
            }
        });

        Thread.sleep(3000);
    }
}
