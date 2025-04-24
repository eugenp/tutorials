package com.baeldung.java11.httpclient.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.Test;

public class HttpResponseUnitTest {

    @Test
    public void shouldReturnStatusOKWhenSendGetRequest() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("https://postman-echo.com/get"))
            .version(HttpClient.Version.HTTP_2)
            .GET()
            .build();

        HttpResponse<String> response = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode(), equalTo(HttpURLConnection.HTTP_OK));
        assertNotNull(response.body());
    }

    @Test
    public void shouldResponseURIDifferentThanRequestURIWhenRedirect() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://stackoverflow.com"))
            .version(HttpClient.Version.HTTP_2)
            .GET()
            .build();
        HttpResponse<String> response = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build()
            .send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(request.uri()
            .toString(), equalTo("http://stackoverflow.com"));
        assertThat(response.uri()
            .toString(), equalTo("https://stackoverflow.com/questions"));
    }

}
