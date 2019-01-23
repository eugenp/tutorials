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

public class RouteExampleControllerLiveTest {

    @Test
    public void givenRoute_whenGet_thenCorrectOutput() throws Exception {
        final HttpUriRequest request = new HttpGet("http://localhost:9000/route-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("GET called");
        }
    }

    @Test
    public void givenRoute_whenPost_thenCorrectOutput() throws Exception {
        final HttpUriRequest request = new HttpPost("http://localhost:9000/route-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("POST called");
        }
    }

    @Test
    public void givenRoute_whenPut_thenCorrectOutput() throws Exception {
        final HttpUriRequest request = new HttpPut("http://localhost:9000/route-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("PUT called");
        }
    }

    @Test
    public void givenRoute_whenDelete_thenCorrectOutput() throws Exception {
        final HttpUriRequest request = new HttpDelete("http://localhost:9000/route-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("DELETE called");
        }
    }

    @Test
    public void givenAnotherRoute_whenGet_thenCorrectOutput() throws Exception {
        final HttpUriRequest request = new HttpGet("http://localhost:9000/another-route-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("GET called");
        }
    }

    @Test
    public void givenAllMatchRoute_whenGet_thenCorrectOutput() throws Exception {
        final HttpUriRequest request = new HttpGet("http://localhost:9000/allmatch-route-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("ALLMATCH called");
        }
    }

    @Test
    public void givenAllMatchRoute_whenPost_thenCorrectOutput() throws Exception {
        final HttpUriRequest request = new HttpPost("http://localhost:9000/allmatch-route-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("ALLMATCH called");
        }
    }

    @Test
    public void givenAllMatchRoute_whenPut_thenCorrectOutput() throws Exception {
        final HttpUriRequest request = new HttpPut("http://localhost:9000/allmatch-route-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("ALLMATCH called");
        }
    }

    @Test
    public void givenAllMatchRoute_whenDelete_thenCorrectOutput() throws Exception {
        final HttpUriRequest request = new HttpDelete("http://localhost:9000/allmatch-route-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("ALLMATCH called");
        }
    }

    @Test
    public void givenRequestAttribute_whenRenderedWithTemplate_thenCorrectlyEvaluateIt() throws Exception {
        final HttpUriRequest request = new HttpGet("http://localhost:9000/template-output-test");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo("<h1>Hello, Blade!</h1>");
        }
    }
    
}
