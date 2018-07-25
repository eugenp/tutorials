package org.baeldung.client;

import okhttp3.Request;
import okhttp3.RequestBody;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.baeldung.client.Consts.APPLICATION_PORT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class TestRestTemplateBasicLiveTest {

    private RestTemplateBuilder restTemplate;
    private static final String FOO_RESOURCE_URL = "http://localhost:" + APPLICATION_PORT + "/spring-rest/foos";
    private static final String URL_SECURED_BY_AUTHENTICATION = "http://httpbin.org/basic-auth/user/passwd";
    private static final String BASE_URL = "http://localhost:" + APPLICATION_PORT + "/spring-rest";

    @Before
    public void beforeTest() {
        restTemplate = new RestTemplateBuilder();
    }

    // GET
    @Test
    public void givenTestRestTemplate_whenSendGetForEntity_thenStatusOk() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> response = testRestTemplate.getForEntity(FOO_RESOURCE_URL + "/1", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void givenRestTemplateWrapper_whenSendGetForEntity_thenStatusOk() {
        TestRestTemplate testRestTemplate = new TestRestTemplate(restTemplate);
        ResponseEntity<String> response = testRestTemplate.getForEntity(FOO_RESOURCE_URL + "/1", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void givenRestTemplateBuilderWrapper_whenSendGetForEntity_thenStatusOk() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        restTemplateBuilder.build();
        TestRestTemplate testRestTemplate = new TestRestTemplate(restTemplateBuilder);
        ResponseEntity<String> response = testRestTemplate.getForEntity(FOO_RESOURCE_URL + "/1", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void givenRestTemplateWrapperWithCredentials_whenSendGetForEntity_thenStatusOk() {
        TestRestTemplate testRestTemplate = new TestRestTemplate(restTemplate, "user", "passwd");
        ResponseEntity<String> response = testRestTemplate.getForEntity(URL_SECURED_BY_AUTHENTICATION,
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void givenTestRestTemplateWithCredentials_whenSendGetForEntity_thenStatusOk() {
        TestRestTemplate testRestTemplate = new TestRestTemplate("user", "passwd");
        ResponseEntity<String> response = testRestTemplate.getForEntity(URL_SECURED_BY_AUTHENTICATION,
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void givenTestRestTemplateWithBasicAuth_whenSendGetForEntity_thenStatusOk() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        ResponseEntity<String> response = testRestTemplate.withBasicAuth("user", "passwd").
                getForEntity(URL_SECURED_BY_AUTHENTICATION, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void givenTestRestTemplateWithCredentialsAndEnabledCookies_whenSendGetForEntity_thenStatusOk() {
        TestRestTemplate testRestTemplate = new TestRestTemplate("user", "passwd", TestRestTemplate.
                HttpClientOption.ENABLE_COOKIES);
        ResponseEntity<String> response = testRestTemplate.getForEntity(URL_SECURED_BY_AUTHENTICATION,
                String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    // HEAD
    @Test
    public void givenFooService_whenCallHeadForHeaders_thenReceiveAllHeaders() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        final HttpHeaders httpHeaders = testRestTemplate.headForHeaders(FOO_RESOURCE_URL);
        assertTrue(httpHeaders.getContentType().includes(MediaType.APPLICATION_JSON));
    }

    // POST
    @Test
    public void givenService_whenPostForObject_thenCreatedObjectIsReturned() {
        TestRestTemplate testRestTemplate = new TestRestTemplate("user", "passwd");
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("text/html; charset=utf-8"),
                "{\"id\":1,\"name\":\"Jim\"}");
        final Request request = new Request.Builder().url(BASE_URL + "/users/detail").post(body).build();
        testRestTemplate.postForObject(URL_SECURED_BY_AUTHENTICATION, request, String.class);
    }

    // PUT
    @Test
    public void givenService_whenPutForObject_thenCreatedObjectIsReturned() {
        TestRestTemplate testRestTemplate = new TestRestTemplate("user", "passwd");
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse("text/html; charset=utf-8"),
                "{\"id\":1,\"name\":\"Jim\"}");
        final Request request = new Request.Builder().url(BASE_URL + "/users/detail").post(body).build();
        testRestTemplate.put(URL_SECURED_BY_AUTHENTICATION, request, String.class);
    }

}
