package com.baeldung.web.test;

import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import com.baeldung.web.dto.Foo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.base.Charsets;

public class RestTemplateBasicLiveTest {

    private RestTemplate restTemplate;
    private static final String fooResourceUrl = "http://localhost:8082/spring-rest/foos";

    @Before
    public void beforeTest() {
        restTemplate = new RestTemplate();
        // restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
    }

    // GET

    @Test
    public void givenResourceUrl_whenSendGetForRequestEntity_thenStatusOk() throws IOException {
        final ResponseEntity<Foo> response = restTemplate.getForEntity(fooResourceUrl + "/1", Foo.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void givenResourceUrl_whenSendGetForRequestEntity_thenBodyCorrect() throws IOException {
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String> response = template.getForEntity(fooResourceUrl + "/1", String.class);

        final ObjectMapper mapper = new XmlMapper();
        final JsonNode root = mapper.readTree(response.getBody());
        final JsonNode name = root.path("name");
        assertThat(name.asText(), notNullValue());
    }

    @Test
    public void givenResourceUrl_whenRetrievingResource_thenCorrect() throws IOException {
        final Foo foo = restTemplate.getForObject(fooResourceUrl + "/1", Foo.class);

        assertThat(foo.getName(), notNullValue());
        assertThat(foo.getId(), is(1L));
    }

    // HEAD, OPTIONS

    @Test
    public void givenFooService_whenCallHeadForHeaders_thenReceiveAllHeadersForThatResource() {
        final HttpHeaders httpHeaders = restTemplate.headForHeaders(fooResourceUrl);
        assertTrue(httpHeaders.getContentType()
            .includes(MediaType.APPLICATION_JSON));
    }

    // POST

    @Test
    public void givenFooService_whenPostForObject_thenCreatedObjectIsReturned() {
        final HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"));
        final Foo foo = restTemplate.postForObject(fooResourceUrl, request, Foo.class);
        assertThat(foo, notNullValue());
        assertThat(foo.getName(), is("bar"));
    }

    @Test
    public void givenFooService_whenPostForLocation_thenCreatedLocationIsReturned() {
        final HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"));
        final URI location = restTemplate.postForLocation(fooResourceUrl, request);
        assertThat(location, notNullValue());
    }

    @Test
    public void givenFooService_whenPostResource_thenResourceIsCreated() {
        final Foo foo = new Foo("bar");
        final ResponseEntity<Foo> response = restTemplate.postForEntity(fooResourceUrl, foo, Foo.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        final Foo fooResponse = response.getBody();
        assertThat(fooResponse, notNullValue());
        assertThat(fooResponse.getName(), is("bar"));
    }

    @Test
    public void givenFooService_whenCallOptionsForAllow_thenReceiveValueOfAllowHeader() {
        final Set<HttpMethod> optionsForAllow = restTemplate.optionsForAllow(fooResourceUrl);
        final HttpMethod[] supportedMethods = { HttpMethod.GET, HttpMethod.POST, HttpMethod.HEAD };

        assertTrue(optionsForAllow.containsAll(Arrays.asList(supportedMethods)));
    }

    // PUT

    @Test
    public void givenFooService_whenPutExistingEntity_thenItIsUpdated() {
        final HttpHeaders headers = prepareBasicAuthHeaders();
        final HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"), headers);

        // Create Resource
        final ResponseEntity<Foo> createResponse = restTemplate.exchange(fooResourceUrl, HttpMethod.POST, request, Foo.class);

        // Update Resource
        final Foo updatedInstance = new Foo("newName");
        updatedInstance.setId(createResponse.getBody()
            .getId());
        final String resourceUrl = fooResourceUrl + '/' + createResponse.getBody()
            .getId();
        final HttpEntity<Foo> requestUpdate = new HttpEntity<>(updatedInstance, headers);
        restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Void.class);

        // Check that Resource was updated
        final ResponseEntity<Foo> updateResponse = restTemplate.exchange(resourceUrl, HttpMethod.GET, new HttpEntity<>(headers), Foo.class);
        final Foo foo = updateResponse.getBody();
        assertThat(foo.getName(), is(updatedInstance.getName()));
    }

    @Test
    public void givenFooService_whenPutExistingEntityWithCallback_thenItIsUpdated() {
        final HttpHeaders headers = prepareBasicAuthHeaders();
        final HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"), headers);

        // Create entity
        ResponseEntity<Foo> response = restTemplate.exchange(fooResourceUrl, HttpMethod.POST, request, Foo.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        // Update entity
        final Foo updatedInstance = new Foo("newName");
        updatedInstance.setId(response.getBody()
            .getId());
        final String resourceUrl = fooResourceUrl + '/' + response.getBody()
            .getId();
        restTemplate.execute(resourceUrl, HttpMethod.PUT, requestCallback(updatedInstance), clientHttpResponse -> null);

        // Check that entity was updated
        response = restTemplate.exchange(resourceUrl, HttpMethod.GET, new HttpEntity<>(headers), Foo.class);
        final Foo foo = response.getBody();
        assertThat(foo.getName(), is(updatedInstance.getName()));
    }

    // PATCH

    @Test
    public void givenFooService_whenPatchExistingEntity_thenItIsUpdated() {
        final HttpHeaders headers = prepareBasicAuthHeaders();
        final HttpEntity<Foo> request = new HttpEntity<>(new Foo("bar"), headers);

        // Create Resource
        final ResponseEntity<Foo> createResponse = restTemplate.exchange(fooResourceUrl, HttpMethod.POST, request, Foo.class);

        // Update Resource
        final Foo updatedResource = new Foo("newName");
        updatedResource.setId(createResponse.getBody()
            .getId());
        final String resourceUrl = fooResourceUrl + '/' + createResponse.getBody()
            .getId();
        final HttpEntity<Foo> requestUpdate = new HttpEntity<>(updatedResource, headers);
        final ClientHttpRequestFactory requestFactory = getSimpleClientHttpRequestFactory();
        final RestTemplate template = new RestTemplate(requestFactory);
        template.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
        template.patchForObject(resourceUrl, requestUpdate, Void.class);

        // Check that Resource was updated
        final ResponseEntity<Foo> updateResponse = restTemplate.exchange(resourceUrl, HttpMethod.GET, new HttpEntity<>(headers), Foo.class);
        final Foo foo = updateResponse.getBody();
        assertThat(foo.getName(), is(updatedResource.getName()));
    }

    // DELETE

    @Test
    public void givenFooService_whenCallDelete_thenEntityIsRemoved() {
        final Foo foo = new Foo("remove me");
        final ResponseEntity<Foo> response = restTemplate.postForEntity(fooResourceUrl, foo, Foo.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        final String entityUrl = fooResourceUrl + "/" + response.getBody()
            .getId();
        restTemplate.delete(entityUrl);
        try {
            restTemplate.getForEntity(entityUrl, Foo.class);
            fail();
        } catch (final HttpClientErrorException ex) {
            assertThat(ex.getStatusCode(), is(HttpStatus.NOT_FOUND));
        }
    }

    @Test
    public void givenFooService_whenFormSubmit_thenResourceIsCreated() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("id", "1");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity( fooResourceUrl+"/form", request , String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        final String fooResponse = response.getBody();
        assertThat(fooResponse, notNullValue());
        assertThat(fooResponse, is("1"));
    }

    private HttpHeaders prepareBasicAuthHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        final String encodedLogPass = getBase64EncodedLogPass();
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + encodedLogPass);
        return headers;
    }

    private String getBase64EncodedLogPass() {
        final String logPass = "user1:user1Pass";
        final byte[] authHeaderBytes = encodeBase64(logPass.getBytes(Charsets.US_ASCII));
        return new String(authHeaderBytes, Charsets.US_ASCII);
    }

    private RequestCallback requestCallback(final Foo updatedInstance) {
        return clientHttpRequest -> {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(clientHttpRequest.getBody(), updatedInstance);
            clientHttpRequest.getHeaders()
                .add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            clientHttpRequest.getHeaders()
                .add(HttpHeaders.AUTHORIZATION, "Basic " + getBase64EncodedLogPass());
        };
    }

    // Simply setting restTemplate timeout using ClientHttpRequestFactory

    ClientHttpRequestFactory getSimpleClientHttpRequestFactory() {
        final int timeout = 5;
        final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout * 1000);
        return clientHttpRequestFactory;
    }

}
