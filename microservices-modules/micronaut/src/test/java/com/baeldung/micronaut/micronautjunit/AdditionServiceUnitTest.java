package com.baeldung.micronaut.micronautjunit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

@MicronautTest
class AdditionServiceUnitTest {

    @Inject
    AdditionService additionService;

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void givenAdditionService_whenAddingTwoIntegers_thenReturnSum() {
        assertEquals(4, additionService.sum(2, 2));
    }

    @Test
    void givenSumUrl_whenPassingTwoIntegersAsQuery_thenReturnSum() {
        HttpRequest<?> request = HttpRequest.GET("/sum?firstNumber=5&secondNumber=6");
        Integer body = client.toBlocking()
            .retrieve(request, Integer.class);
        assertNotNull(body);
        assertEquals(11, body);
    }
}