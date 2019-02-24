package com.baeldung.common.web;

import static com.baeldung.web.util.HTTPLinkHeaderUtil.extractURIByRel;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;

import com.google.common.net.HttpHeaders;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public abstract class AbstractBasicLiveTest<T extends Serializable> extends AbstractLiveTest<T> {

    public AbstractBasicLiveTest(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

    // find - all - paginated

    @Test
    public void whenResourcesAreRetrievedPaged_then200IsReceived() {
        create();
        
        final Response response = RestAssured.get(getURL() + "?page=0&size=10");

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public void whenPageOfResourcesAreRetrievedOutOfBounds_then404IsReceived() {
        final String url = getURL() + "?page=" + randomNumeric(5) + "&size=10";
        final Response response = RestAssured.get(url);

        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public void givenResourcesExist_whenFirstPageIsRetrieved_thenPageContainsResources() {
        create();

        final Response response = RestAssured.get(getURL() + "?page=0&size=10");

        assertFalse(response.body().as(List.class).isEmpty());
    }

    @Test
    public void whenFirstPageOfResourcesAreRetrieved_thenSecondPageIsNext() {
        create();
        create();
        create();
        
        final Response response = RestAssured.get(getURL() + "?page=0&size=2");

        final String uriToNextPage = extractURIByRel(response.getHeader(HttpHeaders.LINK), "next");
        assertEquals(getURL() + "?page=1&size=2", uriToNextPage);
    }

    @Test
    public void whenFirstPageOfResourcesAreRetrieved_thenNoPreviousPage() {
        final Response response = RestAssured.get(getURL() + "?page=0&size=2");

        final String uriToPrevPage = extractURIByRel(response.getHeader(HttpHeaders.LINK), "prev");
        assertNull(uriToPrevPage);
    }

    @Test
    public void whenSecondPageOfResourcesAreRetrieved_thenFirstPageIsPrevious() {
        create();
        create();

        final Response response = RestAssured.get(getURL() + "?page=1&size=2");

        final String uriToPrevPage = extractURIByRel(response.getHeader(HttpHeaders.LINK), "prev");
        assertEquals(getURL() + "?page=0&size=2", uriToPrevPage);
    }

    @Test
    public void whenLastPageOfResourcesIsRetrieved_thenNoNextPageIsDiscoverable() {
        create();
        create();
        create();
        
        final Response first = RestAssured.get(getURL() + "?page=0&size=2");
        final String uriToLastPage = extractURIByRel(first.getHeader(HttpHeaders.LINK), "last");

        final Response response = RestAssured.get(uriToLastPage);

        final String uriToNextPage = extractURIByRel(response.getHeader(HttpHeaders.LINK), "next");
        assertNull(uriToNextPage);
    }

    // count

}
