package com.baeldung.httpclient.httpclient;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.Test;

class ApacheHttpClientUnitTest extends GetRequestMockServer {

    @Test
    void givenDeveloperUsedHttpClient_whenExecutingGetRequest_thenStatusIsOkButSonarReportsAnIssue() throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(serviceOneUrl);
        httpClient.execute(httpGet, response -> {
                assertThat(response.getCode()).isEqualTo(HttpStatus.SC_OK);
                return response;
        });
    }

    @Test
    void givenDeveloperUsedCloseableHttpClient_whenExecutingGetRequest_thenStatusIsOk() throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(serviceOneUrl);
            httpClient.execute(httpGet, response -> {
                assertThat(response.getCode()).isEqualTo(HttpStatus.SC_OK);
                return response;
            });
        }
    }

    @Test
    void givenDeveloperUsedHttpClientBuilder_whenExecutingGetRequest_thenStatusIsOk() throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGet = new HttpGet(serviceOneUrl);
            httpClient.execute(httpGet, response -> {
                assertThat(response.getCode()).isEqualTo(HttpStatus.SC_OK);
                return response;
            });
        }
    }

    @Test
    void givenDeveloperUsedSingleClient_whenExecutingTwoGetRequest_thenStatusIsOk() throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGetOne = new HttpGet(serviceOneUrl);
            httpClient.execute(httpGetOne, responseOne -> {
                HttpEntity entityOne = responseOne.getEntity();
                EntityUtils.consume(entityOne);
                assertThat(responseOne.getCode()).isEqualTo(HttpStatus.SC_OK);
                return responseOne;
            });

            HttpGet httpGetTwo = new HttpGet(serviceTwoUrl);
            httpClient.execute(httpGetTwo, responseTwo -> {
                HttpEntity entityTwo = httpGetTwo.getEntity();
                EntityUtils.consume(entityTwo);
                assertThat(responseTwo.getCode()).isEqualTo(HttpStatus.SC_OK);
                return responseTwo;
            });
        }
    }

}
