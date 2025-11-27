package com.baeldung.micronaut.micronautjunit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

@MicronautTest
class AdditionServiceMockingUnitTest {

    @Inject
    AdditionService additionService;

    @MockBean(AdditionService.class)
    AdditionService additionService() {
        return mock(AdditionService.class);
    }

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void givenAdditionService_whenAddingTwoIntegers_thenReturnSum() {
        when(additionService.sum(2, 2)).thenReturn(4);
        assertEquals(4, additionService.sum(2, 2));
    }

    @Test
    void givenSumUrl_whenPassingTwoIntegersAsQuery_thenReturnSum() {

        when(additionService.sum(20, 25)).thenReturn(45);
        final Integer result = client.toBlocking()
            .retrieve(HttpRequest.GET("/sum?firstNumber=20&secondNumber=25"), Integer.class);

        assertEquals(45, result);
    }
}