package com.baeldung.httpclient;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.SocketTimeoutException;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.apache.hc.core5.util.Timeout;
import org.junit.jupiter.api.Test;

public class HttpAsyncClientV5LiveTest {

    @Test
    void givenTimeoutIsConfigured_whenTimeOut_thenTimeoutException() {
        final int timeout = 3;

        RequestConfig config = RequestConfig.custom()
            .setConnectTimeout(Timeout.ofMilliseconds(timeout * 1000))
            .setConnectionRequestTimeout(Timeout.ofMilliseconds(timeout * 1000))
            .setResponseTimeout(Timeout.ofMilliseconds(timeout * 1000))
            .build();
        CloseableHttpClient client = HttpClientBuilder.create()
            .setDefaultRequestConfig(config)
            .build();

        ClassicHttpRequest request = ClassicRequestBuilder.get("http://www.google.com:81")
            .build();

        assertThrows(SocketTimeoutException.class, () -> {
            client.execute(request, response -> response);
        });
    }

}
