package org.baeldung.okhttp;

import java.io.IOException;

import org.junit.Test;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpHeaderLiveTest {

    private static final String SAMPLE_URL = "http://www.github.com";

    @Test
    public void whenSetHeader_thenCorrect() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
          .url(SAMPLE_URL)
          .addHeader("Content-Type", "application/json")
          .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.close();
    }

    @Test
    public void whenSetDefaultHeader_thenCorrect() throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(new DefaultContentTypeInterceptor("application/json"))
          .build();

        Request request = new Request.Builder()
          .url(SAMPLE_URL)
          .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.close();
    }


}
