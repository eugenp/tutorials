package com.baeldung.web.test;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.baeldung.config.converter.KryoHttpMessageConverter;
import com.baeldung.web.dto.Foo;
import com.baeldung.web.dto.FooProtos;

/**
 * Integration Test class. Tests methods hits the server's rest services.
 */
public class SpringHttpMessageConvertersLiveTest {

    private static String BASE_URI = "http://localhost:8082/spring-rest/";

    /**
     * Without specifying Accept Header, uses the default response from the
     * server (in this case json)
     */
    @Test
    public void whenRetrievingAFoo_thenCorrect() {
        final String URI = BASE_URI + "foos/{id}";

        final RestTemplate restTemplate = new RestTemplate();
        final Foo resource = restTemplate.getForObject(URI, Foo.class, "1");

        assertThat(resource, notNullValue());
    }

    @Test
    public void givenConsumingXml_whenReadingTheFoo_thenCorrect() {
        final String URI = BASE_URI + "foos/{id}";

        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
        final HttpEntity<String> entity = new HttpEntity<String>(headers);

        final ResponseEntity<Foo> response = restTemplate.exchange(URI, HttpMethod.GET, entity, Foo.class, "1");
        final Foo resource = response.getBody();

        assertThat(resource, notNullValue());
    }

    @Test
    public void givenConsumingJson_whenReadingTheFoo_thenCorrect() {
        final String URI = BASE_URI + "foos/{id}";

        final RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        final HttpEntity<String> entity = new HttpEntity<String>(headers);

        final ResponseEntity<Foo> response = restTemplate.exchange(URI, HttpMethod.GET, entity, Foo.class, "1");
        final Foo resource = response.getBody();

        assertThat(resource, notNullValue());
    }

    @Test
    public void givenConsumingXml_whenWritingTheFoo_thenCorrect() {
        final String URI = BASE_URI + "foos/{id}";
        final RestTemplate restTemplate = new RestTemplate();

        final Foo resource = new Foo(4, "jason");
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType((MediaType.APPLICATION_XML));
        final HttpEntity<Foo> entity = new HttpEntity<Foo>(resource, headers);

        final ResponseEntity<Foo> response = restTemplate.exchange(URI, HttpMethod.PUT, entity, Foo.class, resource.getId());
        final Foo fooResponse = response.getBody();

        Assert.assertEquals(resource.getId(), fooResponse.getId());
    }

    @Test
    public void givenConsumingProtobuf_whenReadingTheFoo_thenCorrect() {
        final String URI = BASE_URI + "foos/{id}";

        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Arrays.asList(new ProtobufHttpMessageConverter()));
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(ProtobufHttpMessageConverter.PROTOBUF));
        final HttpEntity<String> entity = new HttpEntity<String>(headers);

        final ResponseEntity<FooProtos.Foo> response = restTemplate.exchange(URI, HttpMethod.GET, entity, FooProtos.Foo.class, "1");
        final FooProtos.Foo resource = response.getBody();

        assertThat(resource, notNullValue());
    }

    @Test
    public void givenConsumingKryo_whenReadingTheFoo_thenCorrect() {
        final String URI = BASE_URI + "foos/{id}";

        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Arrays.asList(new KryoHttpMessageConverter()));
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(KryoHttpMessageConverter.KRYO));
        final HttpEntity<String> entity = new HttpEntity<String>(headers);

        final ResponseEntity<Foo> response = restTemplate.exchange(URI, HttpMethod.GET, entity, Foo.class, "1");
        final Foo resource = response.getBody();

        assertThat(resource, notNullValue());
    }

}
