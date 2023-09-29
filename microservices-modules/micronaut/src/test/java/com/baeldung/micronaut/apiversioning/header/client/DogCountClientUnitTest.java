package com.baeldung.micronaut.apiversioning.header.client;

import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class DogCountClientUnitTest {

    @Inject
    private EmbeddedApplication<?> application;

    @Inject
    private DogCountClient dogCountClient;

    @Test
    void countV1() {
       Assertions.assertEquals(10, dogCountClient.countV1(null).blockingSingle().split(",").length);
       Assertions.assertEquals(4, dogCountClient.countV1(4).blockingSingle().split(",").length);
    }

    @Test
    void countV2() {
        Assertions.assertThrows(HttpClientResponseException.class,
                () -> dogCountClient.countV2(null).count().blockingGet());

        Assertions.assertEquals(6, dogCountClient.countV2(6).blockingSingle().split(",").length);
    }

    @Test
    void countDefault() {
        Assertions.assertThrows(HttpClientResponseException.class,
                () -> dogCountClient.countDefault(null).count().blockingGet());

        Assertions.assertEquals(6, dogCountClient.countDefault(6).blockingSingle().split(",").length);
    }
}