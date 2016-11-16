package org.baeldung.okhttp;

import okhttp3.*;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class OkHttpGetLiveTest {

    private static final String BASE_URL = "http://localhost:8080/spring-rest";

    @Test
    public void whenGetRequest_thenCorrect() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
          .url(BASE_URL + "/date")
          .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        assertThat(response.code(), equalTo(200));
    }

    @Test
    public void whenGetRequestWithQueryParameter_thenCorrect() throws IOException {

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + "/ex/bars").newBuilder();
        urlBuilder.addQueryParameter("id", "1");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
          .url(url)
          .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        assertThat(response.code(), equalTo(200));
    }

    @Test
    public void whenAsynchronousGetRequest_thenCorrect() throws InterruptedException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
          .url(BASE_URL + "/date")
          .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("OK");
            }

            public void onFailure(Call call, IOException e) {
            	fail();
            }
        });

        Thread.sleep(3000);
    }
}
