package org.baeldung.common.web;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.Serializable;

import org.baeldung.persistence.model.Foo;
import org.baeldung.web.util.HTTPLinkHeaderUtil;
import org.hamcrest.core.AnyOf;
import org.junit.Test;
import org.springframework.http.MediaType;

import com.google.common.net.HttpHeaders;
import com.jayway.restassured.response.Response;

public abstract class AbstractDiscoverabilityLiveTest<T extends Serializable> extends AbstractLiveTest<T> {

    public AbstractDiscoverabilityLiveTest(final Class<T> clazzToSet) {
        super(clazzToSet);
    }

    // tests

    // discoverability

    @Test
    public void whenInvalidPOSTIsSentToValidURIOfResource_thenAllowHeaderListsTheAllowedActions() {
        // Given
        final String uriOfExistingResource = createAsUri();

        // When
        final Response res = givenAuth().post(uriOfExistingResource);

        // Then
        final String allowHeader = res.getHeader(HttpHeaders.ALLOW);
        assertThat(allowHeader, AnyOf.<String> anyOf(containsString("GET"), containsString("PUT"), containsString("DELETE")));
    }

    @Test
    public void whenResourceIsCreated_thenUriOfTheNewlyCreatedResourceIsDiscoverable() {
        // When
        final Foo newResource = new Foo(randomAlphabetic(6));
        final Response createResp = givenAuth().contentType(MediaType.APPLICATION_JSON_VALUE).body(newResource).post(getURL());
        final String uriOfNewResource = createResp.getHeader(HttpHeaders.LOCATION);

        // Then
        final Response response = givenAuth().header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).get(uriOfNewResource);

        final Foo resourceFromServer = response.body().as(Foo.class);
        assertThat(newResource, equalTo(resourceFromServer));
    }

    @Test
    public void whenResourceIsRetrieved_thenUriToGetAllResourcesIsDiscoverable() {
        // Given
        final String uriOfExistingResource = createAsUri();

        // When
        final Response getResponse = givenAuth().get(uriOfExistingResource);

        // Then
        final String uriToAllResources = HTTPLinkHeaderUtil.extractURIByRel(getResponse.getHeader("Link"), "collection");

        final Response getAllResponse = givenAuth().get(uriToAllResources);
        assertThat(getAllResponse.getStatusCode(), is(403));
    }

    // template method

}
