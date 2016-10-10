package org.baeldung.okhttp;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpMiscTest {

    private static final String BASE_URL = "http://localhost:8080/spring-rest";

    //@Test
    public void whenSetRequestTimeoutUsingOkHttp_thenFail() throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
          //.connectTimeout(10, TimeUnit.SECONDS)
          //.writeTimeout(10, TimeUnit.SECONDS)
          .readTimeout(1, TimeUnit.SECONDS)
          .build();

        Request request = new Request.Builder()
          .url(BASE_URL + "/delay/2")  // This URL is served with a 2 second delay.
          .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.close();
    }

    //@Test
    public void whenCancelRequestUsingOkHttp_thenCorrect() throws IOException {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
          .url(BASE_URL + "/delay/2")  // This URL is served with a 2 second delay.
          .build();

        final int seconds = 1;
        //final int seconds = 3;

        final long startNanos = System.nanoTime();
        final Call call = client.newCall(request);

        // Schedule a job to cancel the call in 1 second.
        executor.schedule(new Runnable() {
            public void run() {

                System.out.printf("%.2f Canceling call.%n", (System.nanoTime() - startNanos) / 1e9f);
                call.cancel();
                System.out.printf("%.2f Canceled call.%n", (System.nanoTime() - startNanos) / 1e9f);
            }
        }, seconds, TimeUnit.SECONDS);

        try {

          System.out.printf("%.2f Executing call.%n", (System.nanoTime() - startNanos) / 1e9f);
          Response response = call.execute();
          System.out.printf("%.2f Call was expected to fail, but completed: %s%n", (System.nanoTime() - startNanos) / 1e9f, response);

        } catch (IOException e) {

          System.out.printf("%.2f Call failed as expected: %s%n", (System.nanoTime() - startNanos) / 1e9f, e);
        }
    }

    @Test
    public void  whenSetResponseCacheUsingOkHttp_thenCorrect() throws IOException {

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File("src/test/resources/cache");
        Cache cache = new Cache(cacheDirectory, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                //.cacheControl(CacheControl.FORCE_NETWORK)
                //.cacheControl(CacheControl.FORCE_CACHE)
                .build();

        Response response1 = client.newCall(request).execute();

        String responseBody1 = response1.body().string();

        System.out.println("Response 1 response:          " + response1);
        System.out.println("Response 1 cache response:    " + response1.cacheResponse());
        System.out.println("Response 1 network response:  " + response1.networkResponse());
        System.out.println("Response 1 responseBody:      " + responseBody1);

        Response response2 = client.newCall(request).execute();

        String responseBody2 = response2.body().string();

        System.out.println("Response 2 response:          " + response2);
        System.out.println("Response 2 cache response:    " + response2.cacheResponse());
        System.out.println("Response 2 network response:  " + response2.networkResponse());
        System.out.println("Response 2 responseBody:      " + responseBody2);
    }
}
