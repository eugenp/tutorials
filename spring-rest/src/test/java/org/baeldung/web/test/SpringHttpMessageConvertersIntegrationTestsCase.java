package org.baeldung.web.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.baeldung.web.dto.Foo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
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
    public void testGetFoo() {
        final String URI = BASE_URI + "foos/{id}";

        final RestTemplate restTemplate = new RestTemplate();
        final Foo resource = restTemplate.getForObject(URI, Foo.class, "1");

        Assert.assertEquals(1l, resource.getId());
    }

    /**
     * Specifying Accept Header with application/xml for getting the xml response from the server.
     */
    @Test
    public void testGetFooAcceptXML() {
        final String URI = BASE_URI + "foos/{id}";

        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getMessageConverters());

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
        final HttpEntity<String> entity = new HttpEntity<String>(headers);

        final ResponseEntity<Foo> response = restTemplate.exchange(URI, HttpMethod.GET, entity, Foo.class, "1");
        final Foo resource = response.getBody();

        Assert.assertEquals(1l, resource.getId());

    }

    /**
     * Specifying Accept Header with application/xml for getting the xml response from the server.
     */
    @Test
    public void testPUTFooXML() {
        final String URI = BASE_URI + "foos/{id}";
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getMessageConverters());

        final Foo resource = new Foo(4, "andres");
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
        headers.setContentType((MediaType.APPLICATION_XML));
        final HttpEntity<Foo> entity = new HttpEntity<Foo>(resource, headers);

        final ResponseEntity<Foo> response = restTemplate.exchange(URI, HttpMethod.PUT, entity, Foo.class, resource.getId());
        final Foo fooResponse = response.getBody();

        Assert.assertEquals(resource.getId(), fooResponse.getId());
    }

    // UTIL

    /**
     * Configures Message Converters.
     */
    private List<HttpMessageConverter<?>> getMessageConverters() {
        final List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        // adds XML converter using XStreamMarshaller
        final XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAnnotatedClasses(Foo.class);

        final MarshallingHttpMessageConverter marshallingConverter = new MarshallingHttpMessageConverter(marshaller);
        converters.add(marshallingConverter);

        return converters;
    }

}
