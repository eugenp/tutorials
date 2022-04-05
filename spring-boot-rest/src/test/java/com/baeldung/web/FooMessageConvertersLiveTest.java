package com.baeldung.web;

import static com.baeldung.Consts.APPLICATION_PORT;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.baeldung.common.web.AbstractLiveTest;
import com.baeldung.persistence.model.Foo;
import com.baeldung.spring.ConfigIntegrationTest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ConfigIntegrationTest.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class FooMessageConvertersLiveTest extends AbstractLiveTest<Foo> {

    private static final String BASE_URI = "http://localhost:" + APPLICATION_PORT + "/spring-boot-rest/";

    public FooMessageConvertersLiveTest() {
        super(Foo.class);
    }

    @Override
    public final void create() {
        create(new Foo(randomAlphabetic(6)));
    }

    @Override
    public final String createAsUri() {
        return createAsUri(new Foo(randomAlphabetic(6)));
    }

    @Before
    public void setup(){
        create();
    }

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
        restTemplate.setMessageConverters(getXmlMessageConverters());

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        final HttpEntity<String> entity = new HttpEntity<>(headers);

        final ResponseEntity<Foo>
          response = restTemplate.exchange(URI, HttpMethod.GET, entity, Foo.class, "1");
        final Foo resource = response.getBody();

        assertThat(resource, notNullValue());
    }

    private List<HttpMessageConverter<?>> getXmlMessageConverters() {
        final XStreamMarshaller marshaller = new XStreamMarshaller();
        marshaller.setAnnotatedClasses(Foo.class);
        final MarshallingHttpMessageConverter marshallingConverter = new MarshallingHttpMessageConverter(marshaller);

        final List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(marshallingConverter);
        return converters;
    }

    @Test
    public void givenConsumingJson_whenReadingTheFoo_thenCorrect() {
        final String URI = BASE_URI + "foos/{id}";

        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getJsonMessageConverters());

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        final HttpEntity<String> entity = new HttpEntity<String>(headers);

        final ResponseEntity<Foo> response = restTemplate.exchange(URI, HttpMethod.GET, entity, Foo.class, "1");
        final Foo resource = response.getBody();

        assertThat(resource, notNullValue());
    }

    private List<HttpMessageConverter<?>> getJsonMessageConverters() {
        final List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        return converters;
    }

    @Test
    public void givenConsumingXml_whenWritingTheFoo_thenCorrect() {
        final String URI = BASE_URI + "foos";
        final RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getJsonAndXmlMessageConverters());

        final Foo resource = new Foo("jason");
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType((MediaType.APPLICATION_XML));
        final HttpEntity<Foo> entity = new HttpEntity<>(resource, headers);

        final ResponseEntity<Foo> response = restTemplate.exchange(URI, HttpMethod.POST, entity, Foo.class);
        final Foo fooResponse = response.getBody();

        assertThat(fooResponse, notNullValue());
        assertEquals(resource.getName(), fooResponse.getName());
    }

    private List<HttpMessageConverter<?>> getJsonAndXmlMessageConverters() {
        final List<HttpMessageConverter<?>> converters = getJsonMessageConverters();
        converters.addAll(getXmlMessageConverters());
        return converters;
    }

}
