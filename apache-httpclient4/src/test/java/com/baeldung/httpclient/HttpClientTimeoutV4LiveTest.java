package com.baeldung.httpclient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.baeldung.GetRequestMockServer;

class HttpClientTimeoutV4LiveTest extends GetRequestMockServer {

    private CloseableHttpResponse response;

    @AfterEach
    public final void after() throws IllegalStateException, IOException {
        ResponseUtil.closeResponse(response);
    }


    @Test
    void givenUsingNewApi_whenSettingTimeoutViaRequestConfig_thenCorrect() throws IOException {
        final int timeout = 2;
        final RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        final HttpGet request = new HttpGet("http://www.github.com");

        response = client.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    void givenUsingNewApi_whenSettingTimeoutViaSocketConfig_thenCorrect() throws IOException {
        final int timeout = 2;

        final SocketConfig config = SocketConfig.custom().setSoTimeout(timeout * 1000).build();
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultSocketConfig(config).build();

        final HttpGet request = new HttpGet("http://www.github.com");

        response = client.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    @Test
    void givenUsingNewApi_whenSettingTimeoutViaHighLevelApi_thenCorrect() throws IOException {
        final int timeout = 5;

        final RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        final HttpGet request = new HttpGet("http://www.github.com");

        response = client.execute(request);

        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    }

    /**
     * This simulates a timeout against a domain with multiple routes/IPs to it (not a single raw IP)
     */
    @Test
    @Disabled
    void givenTimeoutIsConfigured_whenTimingOut_thenTimeoutException() throws IOException {
        final int timeout = 3;

        final RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000).setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
        final CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

        final HttpGet request = new HttpGet("http://www.google.com:81");

        assertThrows(ConnectTimeoutException.class, () -> {
            client.execute(request);
        });

    }

    @Test
    final void givenLowTimeout_whenExecutingRequestWithTimeout_thenException() {
        final RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(5)
            .setConnectTimeout(5)
            .setSocketTimeout(2)
            .build();

        final CloseableHttpClient client = HttpClientBuilder.create()
            .setDefaultRequestConfig(requestConfig)
            .build();

        final HttpGet request = new HttpGet("http://www.github.com");

        assertThrows(ConnectTimeoutException.class, () -> {
            response = client.execute(request);
        });
    }


    @Test
    void whenSecuredRestApiIsConsumed_then200OK() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        int timeout = 20; // seconds
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout * 1000)
            .setConnectTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();
        HttpGet getMethod = new HttpGet(simplePathUrl);
        getMethod.setConfig(requestConfig);

        int hardTimeout = 5; // seconds
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getMethod.abort();
            }
        };
        new Timer(true).schedule(task, hardTimeout * 1000);

        HttpResponse response = httpClient.execute(getMethod);
        System.out.println("HTTP Status of response: " + response.getStatusLine().getStatusCode());
    }

}
