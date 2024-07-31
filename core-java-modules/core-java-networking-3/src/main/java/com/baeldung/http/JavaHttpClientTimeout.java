package com.baeldung.http;

import java.net.http.HttpClient;
import java.time.Duration;

public class JavaHttpClientTimeout {
    static HttpClient getHttpClientWithTimeout(int seconds) {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(seconds))
                .build();
    }
}
