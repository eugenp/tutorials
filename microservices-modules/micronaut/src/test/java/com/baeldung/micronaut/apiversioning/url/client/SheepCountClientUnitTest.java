package com.baeldung.micronaut.apiversioning.url.client;

import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

@MicronautTest
class SheepCountClientUnitTest {

    @Inject
    private EmbeddedApplication<?> application;
    @Inject
    private SheepCountClient client;

    @Test
    void givenTheCountApi_whenUsingV1ViaUrlStrategy_shouldRouteToProperHandler() {
        Assertions.assertEquals(10, client.countV1(null).blockingSingle().split(",").length);
        Assertions.assertEquals(4, client.countV1(4).blockingSingle().split(",").length);
    }

    @Test
    void givenTheCountApi_whenUsingV2ViaUrlStrategy_shouldRouteToProperHandler() {
        Assertions.assertThrows(HttpClientResponseException.class,
                () -> client.countV2(null).count().blockingGet());

        final var actual = client.countV2(4).blockingSingle().split(",").length;
        Assertions.assertEquals(4, actual);
    }

    @Test
    void givenTheCountApi_whenUsingDefaultVersionViaUrlStrategy_shouldRouteToProperHandler() {
        Assertions.assertThrows(HttpClientResponseException.class,
                () -> client.countDefault(null).count().blockingGet());

        final var actual = client.countDefault(4).blockingSingle().split(",").length;
        Assertions.assertEquals(4, actual);
    }
}