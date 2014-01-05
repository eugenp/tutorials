package org.baeldung.common.web;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.baeldung.web.util.HTTPLinkHeaderUtil.extractURIByRel;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.util.List;

import org.baeldung.test.IMarshaller;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public abstract class AbstractLiveTest<T extends Serializable> {

    protected final Class<T> clazz;

    @Autowired
    protected IMarshaller marshaller;

    public AbstractLiveTest(final Class<T> clazzToSet) {
        super();

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // tests

    // find - one

    // find - all

    // find - all - paginated

    @Test
    public void whenResourcesAreRetrievedPaged_then200IsReceived() {
        final Response response = givenAuth().get(getFooURL() + "?page=0&size=10");

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public void whenPageOfResourcesAreRetrievedOutOfBounds_then404IsReceived() {
        final String url = getFooURL() + "?page=" + randomNumeric(5) + "&size=10";
        final Response response = givenAuth().get(url);

        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    // @Ignore("create is not done yet")
    public void givenResourcesExist_whenFirstPageIsRetrieved_thenPageContainsResources() {
        create();

        final Response response = givenAuth().get(getFooURL() + "?page=0&size=10");

        assertFalse(response.body().as(List.class).isEmpty());
    }

    @Test
    public void whenFirstPageOfResourcesAreRetrieved_thenSecondPageIsNext() {
        final Response response = givenAuth().get(getFooURL() + "?page=0&size=2");

        final String uriToNextPage = extractURIByRel(response.getHeader(HttpHeaders.LINK), "next");
        assertEquals(getFooURL() + "?page=1&size=2", uriToNextPage);
    }

    @Test
    public void whenFirstPageOfResourcesAreRetrieved_thenNoPreviousPage() {
        final Response response = givenAuth().get(getFooURL() + "?page=0&size=2");

        final String uriToPrevPage = extractURIByRel(response.getHeader(HttpHeaders.LINK), "prev");
        assertNull(uriToPrevPage);
    }

    @Test
    public void whenSecondPageOfResourcesAreRetrieved_thenFirstPageIsPrevious() {
        create();
        create();

        final Response response = givenAuth().get(getFooURL() + "?page=1&size=2");

        final String uriToPrevPage = extractURIByRel(response.getHeader(HttpHeaders.LINK), "prev");
        assertEquals(getFooURL() + "?page=0&size=2", uriToPrevPage);
    }

    @Test
    public void whenLastPageOfResourcesIsRetrieved_thenNoNextPageIsDiscoverable() {
        final Response first = givenAuth().get(getFooURL() + "?page=0&size=2");
        final String uriToLastPage = extractURIByRel(first.getHeader(HttpHeaders.LINK), "last");

        final Response response = givenAuth().get(uriToLastPage);

        final String uriToNextPage = extractURIByRel(response.getHeader(HttpHeaders.LINK), "next");
        assertNull(uriToNextPage);
    }

    // count

    // template method

    public abstract void create();

    protected final void create(final T resource) {
        createAsUri(resource);
    }

    final String createAsUri(final T resource) {
        final Response response = createAsResponse(resource);
        Preconditions.checkState(response.getStatusCode() == 201, "create operation: " + response.getStatusCode());

        final String locationOfCreatedResource = response.getHeader(HttpHeaders.LOCATION);
        Preconditions.checkNotNull(locationOfCreatedResource);
        return locationOfCreatedResource;
    }

    final Response createAsResponse(final T resource) {
        Preconditions.checkNotNull(resource);
        final RequestSpecification givenAuthenticated = givenAuth();

        final String resourceAsString = marshaller.encode(resource);
        return givenAuthenticated.contentType(marshaller.getMime()).body(resourceAsString).post(getFooURL());
    }

    //

    private String getFooURL() {
        return "http://localhost:8080/spring-security-rest-full/foos";
    }

    protected final RequestSpecification givenAuth() {
        return RestAssured.given().auth().preemptive().basic("user1", "user1Pass");
    }

}
