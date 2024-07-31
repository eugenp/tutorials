package com.baeldung.httpclient.httpclient;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import com.baeldung.GetRequestMockServer;

class ApacheHttpClientUnitTest extends GetRequestMockServer {

    @Test
    void givenDeveloperUsedCloseableHttpResponse_whenExecutingGetRequest_thenStatusIsOk() throws IOException {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpGet httpGet = new HttpGet(simplePathUrl);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                EntityUtils.consume(entity);
                assertThat(response.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
            }
        }
    }

}
