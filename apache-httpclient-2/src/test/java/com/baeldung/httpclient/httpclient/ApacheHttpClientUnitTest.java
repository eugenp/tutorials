package com.baeldung.httpclient.httpclient;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ApacheHttpClientUnitTest extends GetRequestMockServer {

    @Test
    void givenDeveloperUsedHttpClient_whenExecutingGetRequest_thenStatusIsOkButSonarReportsAnIssue() throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(serviceOneUrl);
        HttpResponse response = httpClient.execute(httpGet);
        assertThat(response.getCode()).isEqualTo(HttpStatus.SC_OK);
    }

    @Test
    void givenDeveloperUsedCloseableHttpClient_whenExecutingGetRequest_thenStatusIsOk() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(serviceOneUrl);
            HttpResponse response = httpClient.execute(httpGet);
            assertThat(response.getCode()).isEqualTo(HttpStatus.SC_OK);
        }
    }

    @Test
    void givenDeveloperUsedHttpClientBuilder_whenExecutingGetRequest_thenStatusIsOk() throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGet = new HttpGet(serviceOneUrl);
            HttpResponse response = httpClient.execute(httpGet);
            assertThat(response.getCode()).isEqualTo(HttpStatus.SC_OK);
        }
    }

    @Test
    void givenDeveloperUsedCloseableHttpResponse_whenExecutingGetRequest_thenStatusIsOk() throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGet = new HttpGet(serviceOneUrl);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                EntityUtils.consume(entity);
                assertThat(response.getCode()).isEqualTo(HttpStatus.SC_OK);
            }
        }
    }

    @Test
    void givenDeveloperUsedSingleClient_whenExecutingTwoGetRequest_thenStatusIsOk() throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGetOne = new HttpGet(serviceOneUrl);
            try (CloseableHttpResponse responseOne = httpClient.execute(httpGetOne)) {
                HttpEntity entityOne = responseOne.getEntity();
                EntityUtils.consume(entityOne);
                assertThat(responseOne.getCode()).isEqualTo(HttpStatus.SC_OK);
            }

            HttpGet httpGetTwo = new HttpGet(serviceTwoUrl);
            try (CloseableHttpResponse responseTwo = httpClient.execute(httpGetTwo)) {
                HttpEntity entityTwo = responseTwo.getEntity();
                EntityUtils.consume(entityTwo);
                assertThat(responseTwo.getCode()).isEqualTo(HttpStatus.SC_OK);
            }
        }
    }

}
