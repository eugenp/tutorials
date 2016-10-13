package org.baeldung.okhttp;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpMiscLiveTest {

    private static final String BASE_URL = "http://localhost:8080/spring-rest";
    private static Logger logger = LoggerFactory.getLogger(OkHttpMiscLiveTest.class);

    @Test
    public void whenSetRequestTimeout_thenFail() throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
          .readTimeout(1, TimeUnit.SECONDS)
          .build();

        Request request = new Request.Builder()
          .url(BASE_URL + "/delay/2")  // This URL is served with a 2 second delay.
          .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.close();
    }

    @Test
    public void whenCancelRequest_thenCorrect() throws IOException {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
          .url(BASE_URL + "/delay/2")  // This URL is served with a 2 second delay.
          .build();

        final int seconds = 1;

        final long startNanos = System.nanoTime();
        final Call call = client.newCall(request);

        // Schedule a job to cancel the call in 1 second.
        executor.schedule(new Runnable() {
            public void run() {

                logger.debug("Canceling call: " + (System.nanoTime() - startNanos) / 1e9f);
                call.cancel();
                logger.debug("Canceled call: " + (System.nanoTime() - startNanos) / 1e9f);
            }
        }, seconds, TimeUnit.SECONDS);

        try {

            logger.debug("Executing call: " + (System.nanoTime() - startNanos) / 1e9f);
            Response response = call.execute();
            logger.debug("Call was expected to fail, but completed: " + (System.nanoTime() - startNanos) / 1e9f, response);

        } catch (IOException e) {

            logger.debug("Call failed as expected: " + (System.nanoTime() - startNanos) / 1e9f, e);
        }
    }

    @Test
    public void  whenSetResponseCache_thenCorrect() throws IOException {

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File("src/test/resources/cache");
        Cache cache = new Cache(cacheDirectory, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
          .cache(cache)
          .build();

        Request request = new Request.Builder()
          .url("http://publicobject.com/helloworld.txt")
          .build();

        Response response1 = client.newCall(request).execute();
        logResponse(response1);

        Response response2 = client.newCall(request).execute();
        logResponse(response2);
    }

    private void logResponse(Response response) throws IOException {

    	logger.debug("Response response:          " + response);
        logger.debug("Response cache response:    " + response.cacheResponse());
        logger.debug("Response network response:  " + response.networkResponse());
        logger.debug("Response responseBody:      " + response.body().string());
    }
}
