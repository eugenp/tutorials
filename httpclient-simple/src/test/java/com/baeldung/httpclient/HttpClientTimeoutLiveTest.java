package com.baeldung.httpclient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.ConnectTimeoutException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;

import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;

import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.util.Timeout;


import com.baeldung.handler.CustomHttpClientResponseHandler;

class HttpClientTimeoutLiveTest {

    @Test
    void givenUsingNewApi_whenSettingTimeoutViaHighLevelApi_thenCorrect() throws IOException {
        final int timeout = 2;

        ConnectionConfig connConfig = ConnectionConfig.custom()
            .setConnectTimeout(timeout, TimeUnit.MILLISECONDS)
            .setSocketTimeout(timeout, TimeUnit.MILLISECONDS)
            .build();

        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(Timeout.ofMilliseconds(2000L))
            .build();

        BasicHttpClientConnectionManager cm = new BasicHttpClientConnectionManager();
        cm.setConnectionConfig(connConfig);


        final HttpGet request = new HttpGet("http://www.github.com");

        try (CloseableHttpClient client = HttpClientBuilder.create()
            .setDefaultRequestConfig(requestConfig)
            .setConnectionManager(cm)
            .build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }

    @Test
    void givenUsingNewApi_whenSettingTimeoutViaSocketConfig_thenCorrect() throws IOException {
        final int timeout = 2000;
        final SocketConfig config = SocketConfig.custom().setSoTimeout(timeout, TimeUnit.MILLISECONDS).build();

        BasicHttpClientConnectionManager cm = new BasicHttpClientConnectionManager();
        cm.setSocketConfig(config);

        final HttpGet request = new HttpGet("http://www.github.com");

        try (CloseableHttpClient client = HttpClientBuilder.create()
            .setConnectionManager(cm)
            .build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(request, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }
    }


    /**
     * This simulates a timeout against a domain with multiple routes/IPs to it (not a single raw IP)
     */
    @Disabled
    void givenTimeoutIsConfigured_whenTimingOut_thenTimeoutException() throws IOException {
        final int timeout = 3;

        ConnectionConfig connConfig = ConnectionConfig.custom()
            .setConnectTimeout(timeout, TimeUnit.MILLISECONDS)
            .setSocketTimeout(timeout, TimeUnit.MILLISECONDS)
            .build();

        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(Timeout.ofMilliseconds(3000L))
            .build();

        BasicHttpClientConnectionManager cm = new BasicHttpClientConnectionManager();
        cm.setConnectionConfig(connConfig);

        final HttpGet request = new HttpGet("http://www.google.com:81");

        assertThrows(ConnectTimeoutException.class, () -> {
            try (CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(cm)
                .build();

                CloseableHttpResponse response = (CloseableHttpResponse) client
                    .execute(request, new CustomHttpClientResponseHandler())) {

                final int statusCode = response.getCode();
                assertThat(statusCode, equalTo(HttpStatus.SC_OK));
            }
        });
    }
    
    @Test
    void whenSecuredRestApiIsConsumed_then200OK() throws IOException {
        int timeout = 20000; // milliseconds

        ConnectionConfig connConfig = ConnectionConfig.custom()
            .setConnectTimeout(timeout, TimeUnit.MILLISECONDS)
            .setSocketTimeout(timeout, TimeUnit.MILLISECONDS)
            .build();

        RequestConfig requestConfig = RequestConfig.custom()
            .setConnectionRequestTimeout(Timeout.ofMilliseconds(20000L))
            .build();

        HttpGet getMethod = new HttpGet("http://localhost:8082/httpclient-simple/api/bars/1");
        getMethod.setConfig(requestConfig);


        BasicHttpClientConnectionManager cm = new BasicHttpClientConnectionManager();
        cm.setConnectionConfig(connConfig);

        int hardTimeout = 5000; // milliseconds
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                getMethod.abort();
            }
        };
        new Timer(true).schedule(task, hardTimeout);

        try (CloseableHttpClient client = HttpClientBuilder.create()
            .setDefaultRequestConfig(requestConfig)
            .setConnectionManager(cm)
            .build();

            CloseableHttpResponse response = (CloseableHttpResponse) client
                .execute(getMethod, new CustomHttpClientResponseHandler())) {

            final int statusCode = response.getCode();
            System.out.println("HTTP Status of response: " + statusCode);
            assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }

    }
    
}
