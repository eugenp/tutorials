package org.baeldung.common.web;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.io.Serializable;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Preconditions;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public abstract class AbstractLiveTest<T extends Serializable> {

    protected final Class<T> clazz;

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
        final Response response = givenAuth().get(getFooURL() + "?page=1&size=10");

        assertThat(response.getStatusCode(), is(200));
    }

    @Test
    public void whenPageOfResourcesAreRetrievedOutOfBounds_then404IsReceived() {
        final Response response = givenAuth().get(getFooURL() + "?page=" + randomNumeric(5) + "&size=10");

        assertThat(response.getStatusCode(), is(404));
    }

    @Test
    public void givenResourcesExist_whenFirstPageIsRetrieved_thenPageContainsResources() {
        // restTemplate.createResource();

        final Response response = givenAuth().get(getFooURL() + "?page=1&size=10");

        assertFalse(response.body().as(List.class).isEmpty());
    }

    // count

    // template method

    private String getFooURL() {
        return "http://localhost:8080/spring-security-rest-full/foos";
    }

    protected final RequestSpecification givenAuth() {
        return RestAssured.given().auth().preemptive().basic("user1", "user1Pass");
    }

}
