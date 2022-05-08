package com.baeldung.httpclient.ssl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class HttpClientSSLBypassUnitTest {

    @Test
    public void whenHttpsRequest_thenCorrect() throws IOException, InterruptedException {
        final Properties props = System.getProperties();
        props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());

        HttpClient httpClient = HttpClient.newBuilder()
          .build();

        HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://www.testingmcafeesites.com/"))
          .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.FALSE.toString());

        Assertions.assertEquals(200, response.statusCode());
    }
}
