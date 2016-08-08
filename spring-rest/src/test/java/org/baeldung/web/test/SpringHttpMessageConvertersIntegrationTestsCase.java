package org.baeldung.web.test;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.baeldung.config.converter.KryoHttpMessageConverter;
import org.baeldung.web.dto.Foo;
import org.baeldung.web.dto.FooProtos;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.client.RestTemplate;

/**
 * Integration Test class. Tests methods hits the server's rest services.
 */
public class SpringHttpMessageConvertersIntegrationTestsCase {

    private static String BASE_URI = "http://localhost:8080/spring-rest/";

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
        restTemplate.setMessageConverters(getMessageConverters());

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
        restTemplate.setMessageConverters(getMessageConverters());

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
        restTemplate.setMessageConverters(getMessageConverters());

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

    // UTIL

    private List<HttpMessageConverter<?>> getMessageConverters() {
        final List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();

        final MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
        final XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
        xmlConverter.setMarshaller(xstreamMarshaller);
        xmlConverter.setUnmarshaller(xstreamMarshaller);

        converters.add(xmlConverter);
        converters.add(new MappingJackson2HttpMessageConverter());

        return converters;
    }

}
