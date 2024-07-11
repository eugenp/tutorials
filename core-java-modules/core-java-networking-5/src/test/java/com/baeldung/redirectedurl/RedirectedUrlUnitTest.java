package com.baeldung.redirectedurl;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class RedirectedUrlUnitTest {
    String canonicalUrl = "http://www.baeldung.com/";
    String expectedRedirectedUrl = "https://www.baeldung.com/";

    @Test
    public void givenOriginalUrl_whenFindRedirectUrlUsingHttpURLConnection_thenCorrectRedirectedUrlReturned() throws IOException, URISyntaxException {
        URL url = new URI(canonicalUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(true);
        int status = connection.getResponseCode();
        String redirectedUrl = null;
        if (status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_MOVED_TEMP) {
            redirectedUrl = connection.getHeaderField("Location");
        }
        connection.disconnect();
        assertEquals(expectedRedirectedUrl, redirectedUrl);
    }

    @Test
    public void givenOriginalUrl_whenFindRedirectUrlUsingHttpClient_thenCorrectRedirectedUrlReturned() throws IOException {
        RequestConfig config = RequestConfig.custom()
                .setRedirectsEnabled(false)
                .build();
        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(config)
                .build()) {
            HttpGet httpGet = new HttpGet(canonicalUrl);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getStatusLine().getStatusCode();
                String redirectedUrl = null;
                if (statusCode == HttpURLConnection.HTTP_MOVED_PERM || statusCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                    org.apache.http.Header[] headers = response.getHeaders("Location");
                    if (headers.length > 0) {
                        redirectedUrl = headers[0].getValue();
                    }
                }
                assertEquals(expectedRedirectedUrl, redirectedUrl);
            }
        }
    }

}
