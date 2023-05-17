package com.baeldung.spring.reactive.customexception.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.spring.reactive.customexception.exception.GlobalExceptionHandler;
import com.baeldung.spring.reactive.customexception.model.ErrorDetails;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserControllerUnitTest {

    @Autowired
    private WebTestClient webClient;

    @InjectMocks
    private UserController userController;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setupTests() {
        // Given
        webClient = WebTestClient.bindToController(userController)
          .controllerAdvice(globalExceptionHandler)
          .configureClient()
          .build();
    }

    @Test
    void givenGetUserV1Endpoint_whenHitWithInvalidUser_thenResponseShouldContainProblemDetailWithCustomAttributes() throws Exception {
        // When
        ProblemDetail problemDetail = webClient.get()
          .uri("/v1/users/123")
          .exchange()
          .expectStatus()
          .isNotFound()
          .expectBody(ProblemDetail.class)
          .returnResult()
          .getResponseBody();
        // Then
        assertNotNull(problemDetail);
        assertNotNull(problemDetail.getProperties().get("errors"));
        List<LinkedHashMap> errors = (List<LinkedHashMap>) problemDetail.getProperties().get("errors");
        assertEquals(ErrorDetails.API_USER_NOT_FOUND.getErrorCode().toString(),
          errors.get(0).get("code"));
        assertEquals(ErrorDetails.API_USER_NOT_FOUND.getErrorMessage().toString(),
          errors.get(0).get("message"));
        assertEquals(ErrorDetails.API_USER_NOT_FOUND.getReferenceUrl().toString(),
          errors.get(0).get("reference"));
    }

    @Test
    void givenGetUserV2Endpoint_whenHitWithInvalidUser_thenResponseShouldContainCustomException() throws Exception {
        // When
        String regex = "^\\{\\s*\"traceId\":\\s*\"[^\"]*\",\\s*\"timestamp\":\\d+\\.\\d+,\\s*\"status\":\\s*\"[^\"]*\",\\s*\"errors\":\\s*\\[\\s*\\{\\s*\"code\":\\s*\"[^\"]*\",\\s*\"message\":\\s*\"[^\"]*\",\\s*\"reference\":\\s*\"[^\"]*\"\\s*\\}\\s*\\]\\s*\\}$";
        Pattern errorResponseSampleFormat = Pattern.compile(regex);
        String errorResponse = webClient.get()
          .uri("/v2/users/123")
          .exchange()
          .expectStatus()
          .isNotFound()
          .expectBody(String.class)
          .returnResult()
          .getResponseBody();
        // Then
        assertTrue(errorResponseSampleFormat.matcher(errorResponse).matches());
    }

}
