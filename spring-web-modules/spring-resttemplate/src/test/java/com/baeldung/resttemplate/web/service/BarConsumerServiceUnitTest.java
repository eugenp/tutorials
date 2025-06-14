package com.baeldung.resttemplate.web.service;

import com.baeldung.resttemplate.web.exception.UnauthorizedException;
import com.baeldung.resttemplate.web.model.Bar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BarConsumerServiceUnitTest {

    @Mock
    private RestTemplate restTemplate;

    private BarConsumerService barConsumerService;

    @BeforeEach
    public void setup() {
        barConsumerService = new BarConsumerService(restTemplate);
    }

    @Test
    public void givenValidId_whenFetchingBar_thenReturnsBar() {
        Bar expectedBar = new Bar();
        expectedBar.setId("123");
        expectedBar.setName("Test Bar");

        when(restTemplate.getForObject(anyString(), eq(Bar.class)))
                .thenReturn(expectedBar);

        Bar result = barConsumerService.fetchBarById("123");
        assertEquals(expectedBar, result);
    }

    @Test
    public void givenUnauthorizedResponse_whenFetchingBar_thenThrowsUnauthorizedException() {
        // Arrange
        String errorBody = "{\"error\": \"Invalid token\"}";
        HttpClientErrorException exception = HttpClientErrorException.create(
                HttpStatus.UNAUTHORIZED,
                "Unauthorized",
                null,
                errorBody.getBytes(),
                null
        );

        when(restTemplate.getForObject(anyString(), eq(Bar.class)))
                .thenThrow(exception);

        UnauthorizedException thrown = assertThrows(
                UnauthorizedException.class,
                () -> barConsumerService.fetchBarById("123")
        );

        assertTrue(thrown.getMessage().contains(errorBody));
    }
}