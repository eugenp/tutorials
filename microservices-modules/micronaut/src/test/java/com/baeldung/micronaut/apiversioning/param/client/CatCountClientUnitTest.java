package com.baeldung.micronaut.apiversioning.param.client;

import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class CatCountClientUnitTest {

    @Inject
    private EmbeddedApplication<?> application;

    @Inject
    private CatCountClient client;

    @Test
    void givenTheCountApi_whenUsingV1ViaParameterStrategy_shouldRouteToProperHandler() {
       Assertions.assertEquals(10, client.count(null, 1).blockingSingle().split(",").length);
       Assertions.assertEquals(5, client.count(5, 1).blockingSingle().split(",").length);
    }

    @Test
    void givenTheCountApi_whenUsingV2ViaParameterStrategy_shouldRouteToProperHandler() {
        Assertions.assertThrows(HttpClientResponseException.class,
                () -> client.count(null, 2).count().blockingGet());

        Assertions.assertEquals(6, client.count(6, 2).blockingSingle().split(",").length);
    }

    @Test
    void givenTheCountApi_whenUsingDefaultVersionViaParameterStrategy_shouldRouteToProperHandler() {
        Assertions.assertEquals(10, client.count(null, null).blockingSingle().split(",").length);
        Assertions.assertEquals(6, client.count(6, null).blockingSingle().split(",").length);
    }
}