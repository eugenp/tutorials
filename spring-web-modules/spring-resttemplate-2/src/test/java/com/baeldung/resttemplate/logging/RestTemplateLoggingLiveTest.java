package com.baeldung.resttemplate.logging;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateLoggingLiveTest {

    private static final String baseUrl = "http://localhost:8080/spring-rest";
    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateLoggingLiveTest.class);

    @Test
    public void givenHttpClientConfiguration_whenSendGetForRequestEntity_thenRequestResponseFullLog() {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        final ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/persons", "my request body", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("[\"Lucie\",\"Jackie\",\"Danesh\",\"Tao\"]", response.getBody());
    }

    @Test
    public void givenLoggingInterceptorConfiguration_whenSendGetForRequestEntity_thenRequestResponseCustomLog() {

        RestTemplate restTemplate = null;
        if (LOGGER.isDebugEnabled()) {
            ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(
                    new SimpleClientHttpRequestFactory());
            restTemplate = new RestTemplate(factory);
        } else {
            restTemplate = new RestTemplate();
        }

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new LoggingInterceptor());
        restTemplate.setInterceptors(interceptors);

        final ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "/persons", "my request body", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("[\"Lucie\",\"Jackie\",\"Danesh\",\"Tao\"]", response.getBody());
    }
}
