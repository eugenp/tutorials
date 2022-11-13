package com.baeldung.blade.sample;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class AppLiveTest {

    @Test
    public void givenBasicRoute_whenGet_thenCorrectOutput() throws Exception {
        final HttpUriRequest request = new HttpGet("http://localhost:9000/basic-route-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("GET called");
        }
    }

    @Test
    public void givenBasicRoute_whenPost_thenCorrectOutput() throws Exception {
        final HttpUriRequest request = new HttpPost("http://localhost:9000/basic-route-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("POST called");
        }
    }

    @Test
    public void givenBasicRoute_whenPut_thenCorrectOutput() throws Exception {
        final HttpUriRequest request = new HttpPut("http://localhost:9000/basic-route-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("PUT called");
        }
    }

    @Test
    public void givenBasicRoute_whenDelete_thenCorrectOutput() throws Exception {
        final HttpUriRequest request = new HttpDelete("http://localhost:9000/basic-route-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("DELETE called");
        }
    }
}
