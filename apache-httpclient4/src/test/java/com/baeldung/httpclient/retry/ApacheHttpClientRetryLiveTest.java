package com.baeldung.httpclient.retry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Objects;

import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApacheHttpClientRetryLiveTest {

    private Integer requestCounter;
    private CloseableHttpClient httpClient;

    @BeforeEach
    void setUp() {
        requestCounter = 0;
    }

    @AfterEach
    void tearDown() throws IOException {
        if (httpClient != null) {
            httpClient.close();
        }
    }

    private void createDefaultApacheHttpClient() {
        this.httpClient = HttpClientBuilder
          .create()
          .addInterceptorFirst((HttpRequestInterceptor) (httpRequest, httpContext) -> {
              requestCounter++;
          }).build();
    }

    private void createFailingHttpClient() {
        this.httpClient = HttpClientBuilder
          .create()
          .addInterceptorFirst((HttpRequestInterceptor) (httpRequest, httpContext) -> requestCounter++)
          .addInterceptorLast((HttpResponseInterceptor) (httpResponse, httpContext) -> {
              throw new IOException();
          })
          .build();
    }

    private void createHttpClientWithRetryHandler() {
        this.httpClient = HttpClientBuilder
          .create()
          .addInterceptorFirst((HttpRequestInterceptor) (httpRequest, httpContext) -> requestCounter++)
          .addInterceptorLast((HttpResponseInterceptor) (httpRequest, httpContext) -> { throw new IOException(); })
          .setRetryHandler(new DefaultHttpRequestRetryHandler(6, true))
          .build();
    }

    private void createHttpClientWithCustomRetryHandler() {
        this.httpClient = HttpClientBuilder
          .create()
          .addInterceptorFirst((HttpRequestInterceptor) (httpRequest, httpContext) -> requestCounter++)
          .addInterceptorLast((HttpResponseInterceptor) (httpRequest, httpContext) -> { throw new IOException(); })
          .setRetryHandler((exception, executionCount, context) -> {
              if (executionCount < 5 && Objects.equals("GET", ((HttpClientContext) context).getRequest().getRequestLine().getMethod())) {
                  return true;
              } else {
                  return false;
              }
          })
          .build();
    }

    private void createHttpClientWithRetriesDisabled() {
        this.httpClient = HttpClientBuilder
          .create()
          .addInterceptorFirst((HttpRequestInterceptor) (httpRequest, httpContext) -> requestCounter++)
          .addInterceptorLast((HttpResponseInterceptor) (httpRequest, httpContext) -> { throw new IOException(); })
          .disableAutomaticRetries()
          .build();
    }

    @Test
    void givenDefaultConfiguration_whenReceivedIOException_thenRetriesPerformed() {
        createFailingHttpClient();
        assertThrows(IOException.class, () -> httpClient.execute(new HttpGet("https://httpstat.us/200")));
        assertThat(requestCounter).isEqualTo(4);
    }

    @Test
    void givenDefaultConfiguration_whenDomainNameNotResolved_thenNoRetryApplied() {
        createDefaultApacheHttpClient();
        HttpGet request = new HttpGet(URI.create("http://domain.that.does.not.exist:80/api/v1"));

        assertThrows(UnknownHostException.class, () -> httpClient.execute(request));
        assertThat(requestCounter).isEqualTo(1);
    }

    @Test
    void givenDefaultConfiguration_whenGotInternalServerError_thenNoRetryLogicApplied() throws IOException {
        createDefaultApacheHttpClient();
        HttpGet request = new HttpGet(URI.create("https://httpstat.us/500"));

        CloseableHttpResponse response = assertDoesNotThrow(() -> httpClient.execute(request));
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(500);
        assertThat(requestCounter).isEqualTo(1);
        response.close();
    }

    @Test
    void givenDefaultConfiguration_whenHttpPatchRequest_thenRetryIsNotApplied() {
        createFailingHttpClient();
        HttpPatch request = new HttpPatch(URI.create("https://httpstat.us/500"));

        assertThrows(IOException.class, () -> httpClient.execute(request));
        assertThat(requestCounter).isEqualTo(1);
    }

    @Test
    void givenDefaultConfiguration_whenHttpPutRequest_thenRetryIsNotApplied() {
        createFailingHttpClient();
        HttpPut request = new HttpPut(URI.create("https://httpstat.us/500"));

        assertThrows(IOException.class, () -> httpClient.execute(request));
        assertThat(requestCounter).isEqualTo(1);
    }

    @Test
    void givenConfiguredRetryHandler_whenHttpPostRequest_thenRetriesPerformed() {
        createHttpClientWithRetryHandler();

        HttpPost request = new HttpPost(URI.create("https://httpstat.us/200"));

        assertThrows(IOException.class, () -> httpClient.execute(request));
        assertThat(requestCounter).isEqualTo(7);
    }

    @Test
    void givenCustomRetryHandler_whenUnknownHostException_thenRetryAnyway() {
        createHttpClientWithCustomRetryHandler();

        HttpGet request = new HttpGet(URI.create("https://domain.that.does.not.exist/200"));

        assertThrows(IOException.class, () -> httpClient.execute(request));
        assertThat(requestCounter).isEqualTo(5);
    }

    @Test
    void givenDisabledRetries_whenExecutedHttpRequestEndUpWithIOException_thenRetryIsNotApplied() {
        createHttpClientWithRetriesDisabled();
        HttpGet request = new HttpGet(URI.create("https://httpstat.us/200"));

        assertThrows(IOException.class, () -> httpClient.execute(request));
        assertThat(requestCounter).isEqualTo(1);
    }
}
