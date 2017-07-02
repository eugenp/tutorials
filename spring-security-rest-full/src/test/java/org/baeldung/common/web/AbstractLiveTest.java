package org.baeldung.common.web;

import static org.baeldung.Consts.APPLICATION_PORT;

import java.io.Serializable;

import org.baeldung.test.IMarshaller;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.google.common.net.HttpHeaders;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class AbstractLiveTest<T extends Serializable> {

    private final Class<T> clazz;

    @Autowired
    private IMarshaller marshaller;

    AbstractLiveTest(final Class<T> clazzToSet) {
        super();

        Preconditions.checkNotNull(clazzToSet);
        clazz = clazzToSet;
    }

    // template method

    protected abstract void create();

    protected abstract String createAsUri();

    protected final void create(final T resource) {
        createAsUri(resource);
    }

    protected final String createAsUri(final T resource) {
        final Response response = createAsResponse(resource);
        Preconditions.checkState(response.getStatusCode() == 201, "create operation: " + response.getStatusCode());

        final String locationOfCreatedResource = response.getHeader(HttpHeaders.LOCATION);
        Preconditions.checkNotNull(locationOfCreatedResource);
        return locationOfCreatedResource;
    }

    private Response createAsResponse(final T resource) {
        Preconditions.checkNotNull(resource);
        final RequestSpecification givenAuthenticated = givenAuth();

        final String resourceAsString = marshaller.encode(resource);
        return givenAuthenticated.contentType(marshaller.getMime()).body(resourceAsString).post(getURL());
    }

    //

    String getURL() {
        return "http://localhost:" + APPLICATION_PORT + "/spring-security-rest-full/auth/foos";
    }

    protected final RequestSpecification givenAuth() {
        return RestAssured.given().auth().preemptive().basic("user1", "user1Pass");
    }

}
