package com.baeldung.uribuilder;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class SpringUriBuilderIntegrationTest {

    @Test
    public void constructUri() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host("www.baeldung.com").path("/junit-5").build();

        assertEquals("http://www.baeldung.com/junit-5", uriComponents.toUriString());
    }

    @Test
    public void constructUriEncoded() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host("www.baeldung.com").path("/junit 5").build().encode();

        assertEquals("http://www.baeldung.com/junit%205", uriComponents.toUriString());
    }

    @Test
    public void constructUriFromTemplate() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host("www.baeldung.com").path("/{article-name}").buildAndExpand("junit-5");

        assertEquals("http://www.baeldung.com/junit-5", uriComponents.toUriString());
    }

    @Test
    public void constructUriWithQueryParameter() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("http").host("www.google.com").path("/").query("q={keyword}").buildAndExpand("baeldung");

        assertEquals("http://www.google.com/?q=baeldung", uriComponents.toUriString());
    }

    @Test
    public void expandWithRegexVar() {
        String template = "/myurl/{name:[a-z]{1,5}}/show";
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(template).build();
        uriComponents = uriComponents.expand(Collections.singletonMap("name", "test"));

        assertEquals("/myurl/test/show", uriComponents.getPath());
    }
}
