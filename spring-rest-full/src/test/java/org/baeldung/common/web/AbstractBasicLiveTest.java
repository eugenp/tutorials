package org.baeldung.common.web;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.baeldung.web.util.HTTPLinkHeaderUtil.extractURIByRel;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.Serializable;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.net.HttpHeaders;

public abstract class AbstractBasicLiveTest<T extends Serializable> extends AbstractLiveTest<T> {

    public AbstractBasicLiveTest(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

    // tests

    @Test
    public void givenResourceExists_whenRetrievingResource_thenEtagIsAlsoReturned() {
        // Given
        final String uriOfResource = createAsUri();

        // When
        final Response findOneResponse = RestAssured.given()
            .header("Accept", "application/json")
            .get(uriOfResource);

        // Then
        assertNotNull(findOneResponse.getHeader(HttpHeaders.ETAG));
    }

    @Test
    public void givenResourceWasRetrieved_whenRetrievingAgainWithEtag_thenNotModifiedReturned() {
        // Given
        final String uriOfResource = createAsUri();
        final Response findOneResponse = RestAssured.given()
            .header("Accept", "application/json")
            .get(uriOfResource);
        final String etagValue = findOneResponse.getHeader(HttpHeaders.ETAG);

        // When
        final Response secondFindOneResponse = RestAssured.given()
            .header("Accept", "application/json")
            .headers("If-None-Match", etagValue)
            .get(uriOfResource);

        // Then
        assertTrue(secondFindOneResponse.getStatusCode() == 304);
    }

    @Test
    @Ignore("No Update operation yet")
    public void givenResourceWasRetrievedThenModified_whenRetrievingAgainWithEtag_thenResourceIsReturned() {
        // Given
        final String uriOfResource = createAsUri();
        final Response findOneResponse = RestAssured.given()
            .header("Accept", "application/json")
            .get(uriOfResource);
        final String etagValue = findOneResponse.getHeader(HttpHeaders.ETAG);

        // existingResource.setName(randomAlphabetic(6));
        // getApi().update(existingResource.setName("randomString"));

        // When
        final Response secondFindOneResponse = RestAssured.given()
            .header("Accept", "application/json")
            .headers("If-None-Match", etagValue)
            .get(uriOfResource);

        // Then
        assertTrue(secondFindOneResponse.getStatusCode() == 200);
    }

    @Test
    @Ignore("Not Yet Implemented By Spring - https://jira.springsource.org/browse/SPR-10164")
    public void givenResourceExists_whenRetrievedWithIfMatchIncorrectEtag_then412IsReceived() {
        // Given
        final String uriOfResource = createAsUri();

        // When
        final Response findOneResponse = RestAssured.given()
            .header("Accept", "application/json")
            .headers("If-Match", randomAlphabetic(8))
            .get(uriOfResource);

        // Then
        assertTrue(findOneResponse.getStatusCode() == 412);
    }

    // find - one

    // find - all

    // find - all - paginated

    @Test
    public void whenResourcesAreRetrievedPaged_then200IsReceived() {
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
        final Response first = RestAssured.get(getURL() + "?page=0&size=2");
        final String uriToLastPage = extractURIByRel(first.getHeader(HttpHeaders.LINK), "last");

        final Response response = RestAssured.get(uriToLastPage);

        final String uriToNextPage = extractURIByRel(response.getHeader(HttpHeaders.LINK), "next");
        assertNull(uriToNextPage);
    }

    // count

}
