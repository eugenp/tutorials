package com.baeldung.blade.sample;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class AttributesExampleControllerLiveTest {

    @Test
    public void givenRequestAttribute_whenSet_thenRetrieveWithGet() throws Exception {
        final HttpUriRequest request = new HttpGet("http://localhost:9000/request-attribute-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo(AttributesExampleController.REQUEST_VALUE);
        }
    }

    @Test
    public void givenSessionAttribute_whenSet_thenRetrieveWithGet() throws Exception {
        final HttpUriRequest request = new HttpGet("http://localhost:9000/session-attribute-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(EntityUtils.toString(httpResponse.getEntity())).isEqualTo(AttributesExampleController.SESSION_VALUE);
        }
    }

    @Test
    public void givenHeader_whenSet_thenRetrieveWithGet() throws Exception {
        final HttpUriRequest request = new HttpGet("http://localhost:9000/header-example");
        request.addHeader("a-header","foobar");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(httpResponse.getHeaders("a-header")[0].getValue()).isEqualTo("foobar");
        }
    }

    @Test
    public void givenNoHeader_whenSet_thenRetrieveDefaultValueWithGet() throws Exception {
        final HttpUriRequest request = new HttpGet("http://localhost:9000/header-example");
        try (final CloseableHttpResponse httpResponse = HttpClientBuilder.create()
            .build()
            .execute(request);) {
            assertThat(httpResponse.getHeaders("a-header")[0].getValue()).isEqualTo(AttributesExampleController.HEADER);
        }
    }

}
