package com.baeldung.resttemplate.proxy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateCustomizerLiveTest {

    RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new RestTemplateBuilder(new ProxyCustomizer()).build();
    }

    @Test
    public void givenRestTemplate_whenRequestedWithProxy_thenResponseBodyIsOk() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://httpbin.org/get", String.class);

        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));
    }

    private static class ProxyCustomizer implements RestTemplateCustomizer {

        @Override
        public void customize(RestTemplate restTemplate) {
            HttpHost proxy = new HttpHost("201.91.82.155", 3128);
            HttpClient httpClient = HttpClientBuilder.create()
                .setRoutePlanner(new DefaultProxyRoutePlanner(proxy) {
                    @Override
                    public HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
                        return super.determineProxy(target, request, context);
                    }
                })
                .build();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        }
    }
}
