package org.baeldung.client;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.baeldung.web.dto.Foo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateBasicLiveTest {

    private RestTemplate restTemplate;

    private static final String fooResourceUrl = String.format("http://localhost:%d/spring-rest/foos", 8082);

    @Before
    public void beforeTest() {
        restTemplate = new RestTemplate();
    }

    // GET

    @Test
    public void givenResourceUrl_whenSendGetForRequestEntity_thenStatusOk() throws IOException {
        final ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl + "/1", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void givenResourceUrl_whenRetrievingResource_thenCorrect() throws IOException {
        final Foo foo = restTemplate.getForObject(fooResourceUrl + "/1", Foo.class);

        assertThat(foo.getName(), notNullValue());
        assertThat(foo.getId(), is(1L));
    }

    // PUT

    @Test
    public void givenFooService_whenPutObject_thenUpdatedObjectIsReturned() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final Foo foo = new Foo(1, "newName");
        final String resourceUrl = fooResourceUrl + "/1";
        final HttpEntity<Foo> requestUpdate = new HttpEntity<>(foo, headers);
        final ResponseEntity<Foo> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Foo.class);

        assertThat(foo.getName(), is(response.getBody()
            .getName()));
    }

}
