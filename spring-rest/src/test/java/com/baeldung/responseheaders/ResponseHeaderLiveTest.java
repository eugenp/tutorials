package com.baeldung.responseheaders;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ResponseHeaderLiveTest {

    private static final String BASE_URL = "http://localhost:8082/spring-rest";
    private static final String SINGLE_BASE_URL = BASE_URL + "/single-response-header";
    private static final String FILTER_BASE_URL = BASE_URL + "/filter-response-header";
    private static final String SERVICE_SINGLE_RESPONSE_HEADER = "Baeldung-Example-Header";
    private static final String SERVICE_FILTER_RESPONSE_HEADER = "Baeldung-Example-Filter-Header";

    @Autowired
    private TestRestTemplate template;

    @Test
    public void whenHttpServletResponseRequest_thenObtainResponseWithCorrectHeader() {
        final String requestUrl = "/http-servlet-response";
        ResponseEntity<String> response = template.getForEntity(SINGLE_BASE_URL + requestUrl, String.class);
        HttpHeaders responseHeaders = response.getHeaders();

        assertThat(responseHeaders).isNotEmpty();
        assertThat(responseHeaders).containsEntry(SERVICE_SINGLE_RESPONSE_HEADER, Arrays.asList("Value-HttpServletResponse"));
    }

    @Test
    public void whenResponseEntityConstructorRequest_thenObtainResponseWithCorrectHeader() {
        final String requestUrl = "/response-entity-constructor";
        ResponseEntity<String> response = template.getForEntity(SINGLE_BASE_URL + requestUrl, String.class);
        HttpHeaders responseHeaders = response.getHeaders();

        assertThat(responseHeaders).isNotEmpty();
        assertThat(responseHeaders).containsEntry(SERVICE_SINGLE_RESPONSE_HEADER, Arrays.asList("Value-ResponseEntityContructor"));
    }

    @Test
    public void whenResponseEntityConstructorAndMultipleHeadersRequest_thenObtainResponseWithCorrectHeaders() {
        final String requestUrl = "/response-entity-contructor-multiple-headers";
        ResponseEntity<String> response = template.getForEntity(SINGLE_BASE_URL + requestUrl, String.class);
        HttpHeaders responseHeaders = response.getHeaders();

        assertThat(responseHeaders).isNotEmpty();
        assertThat(responseHeaders).containsEntry(SERVICE_SINGLE_RESPONSE_HEADER, Arrays.asList("Value-ResponseEntityConstructorAndHeaders"));
        assertThat(responseHeaders).containsEntry("Accept", Arrays.asList(MediaType.APPLICATION_JSON.toString()));
    }

    @Test
    public void whenResponseEntityBuilderRequest_thenObtainResponseWithCorrectHeader() {
        final String requestUrl = "/response-entity-builder";
        ResponseEntity<String> response = template.getForEntity(SINGLE_BASE_URL + requestUrl, String.class);
        HttpHeaders responseHeaders = response.getHeaders();

        assertThat(responseHeaders).isNotEmpty();
        assertThat(responseHeaders).containsEntry(SERVICE_SINGLE_RESPONSE_HEADER, Arrays.asList("Value-ResponseEntityBuilder"));
    }

    @Test
    public void whenResponseEntityBuilderAndHttpHeadersRequest_thenObtainResponseWithCorrectHeader() {
        final String requestUrl = "/response-entity-builder-with-http-headers";
        ResponseEntity<String> response = template.getForEntity(SINGLE_BASE_URL + requestUrl, String.class);
        HttpHeaders responseHeaders = response.getHeaders();

        assertThat(responseHeaders).isNotEmpty();
        assertThat(responseHeaders).containsEntry(SERVICE_SINGLE_RESPONSE_HEADER, Arrays.asList("Value-ResponseEntityBuilderWithHttpHeaders"));
    }
    
    @Test
    public void whenFilterWithNoExtraHeaderRequest_thenObtainResponseWithCorrectHeader() {
        final String requestUrl = "/no-extra-header";
        ResponseEntity<String> response = template.getForEntity(FILTER_BASE_URL + requestUrl, String.class);
        HttpHeaders responseHeaders = response.getHeaders();

        assertThat(responseHeaders).isNotEmpty();
        assertThat(responseHeaders).containsEntry(SERVICE_FILTER_RESPONSE_HEADER, Arrays.asList("Value-Filter"));
    }
    
    @Test
    public void whenFilterWithExtraHeaderRequest_thenObtainResponseWithCorrectHeaders() {
        final String requestUrl = "/extra-header";
        ResponseEntity<String> response = template.getForEntity(FILTER_BASE_URL + requestUrl, String.class);
        HttpHeaders responseHeaders = response.getHeaders();

        assertThat(responseHeaders).isNotEmpty();
        assertThat(responseHeaders).containsEntry(SERVICE_FILTER_RESPONSE_HEADER, Arrays.asList("Value-Filter"));
        assertThat(responseHeaders).containsEntry(SERVICE_SINGLE_RESPONSE_HEADER, Arrays.asList("Value-ExtraHeader"));
    }

}
