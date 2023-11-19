package com.baeldung.urlquerymanipulation;

import static junit.framework.TestCase.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.client.utils.URIBuilder;

import org.junit.Test;
import org.springframework.web.util.UriComponentsBuilder;

public class UrlQueryManipulationUnitTest {

    @Test
    public void whenUsingApacheUriBuilder_thenParametersAreCorrectlyAdded() throws URISyntaxException {
        String url = "baeldung.com";
        String key = "article";
        String value = "alpha";
        URI uri = new URIBuilder(url).addParameter(key, value)
          .build();

        assertEquals("baeldung.com?article=alpha", uri.toString());
    }

    @Test
    public void whenUsingJavaUriBuilder_thenParametersAreCorrectlyAdded() {
        String url = "baeldung.com";
        String key = "article";
        String value = "beta";
        URI uri = UriBuilder.fromUri(url)
          .queryParam(key, value)
          .build();

        assertEquals("baeldung.com?article=beta", uri.toString());
    }

    @Test
    public void whenUsingSpringUriComponentsBuilder_thenParametersAreCorrectlyAdded() {
        String url = "baeldung.com";
        String key = "article";
        String value = "charlie";
        URI uri = UriComponentsBuilder.fromUriString(url)
          .queryParam(key, value)
          .build()
          .toUri();

        assertEquals("baeldung.com?article=charlie", uri.toString());
    }

}
