package org.baeldung.common.web;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.net.HttpHeaders;

import io.restassured.RestAssured;
import io.restassured.response.Response;

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
}
