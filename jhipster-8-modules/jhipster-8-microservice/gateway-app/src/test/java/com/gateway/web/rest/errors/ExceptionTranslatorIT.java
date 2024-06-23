package com.gateway.web.rest.errors;

import com.gateway.IntegrationTest;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests {@link ExceptionTranslator} controller advice.
 */
@WithMockUser
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_TIMEOUT)
@IntegrationTest
class ExceptionTranslatorIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testConcurrencyFailure() {
        webTestClient
            .get()
            .uri("/api/exception-translator-test/concurrency-failure")
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.CONFLICT)
            .expectHeader()
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .expectBody()
            .jsonPath("$.message")
            .isEqualTo(ErrorConstants.ERR_CONCURRENCY_FAILURE);
    }

    @Test
    void testMethodArgumentNotValid() {
        webTestClient
            .post()
            .uri("/api/exception-translator-test/method-argument")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue("{}")
            .exchange()
            .expectHeader()
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .expectBody()
            .jsonPath("$.message")
            .isEqualTo(ErrorConstants.ERR_VALIDATION)
            .jsonPath("$.fieldErrors.[0].objectName")
            .isEqualTo("test")
            .jsonPath("$.fieldErrors.[0].field")
            .isEqualTo("test")
            .jsonPath("$.fieldErrors.[0].message")
            .isEqualTo("must not be null");
    }

    @Test
    void testMissingRequestPart() {
        webTestClient
            .get()
            .uri("/api/exception-translator-test/missing-servlet-request-part")
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectHeader()
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .expectBody()
            .jsonPath("$.message")
            .isEqualTo("error.http.400");
    }

    @Test
    void testMissingRequestParameter() {
        webTestClient
            .get()
            .uri("/api/exception-translator-test/missing-servlet-request-parameter")
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectHeader()
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .expectBody()
            .jsonPath("$.message")
            .isEqualTo("error.http.400");
    }

    @Test
    void testAccessDenied() {
        webTestClient
            .get()
            .uri("/api/exception-translator-test/access-denied")
            .exchange()
            .expectStatus()
            .isForbidden()
            .expectHeader()
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .expectBody()
            .jsonPath("$.message")
            .isEqualTo("error.http.403")
            .jsonPath("$.detail")
            .isEqualTo("test access denied!");
    }

    @Test
    void testUnauthorized() {
        webTestClient
            .get()
            .uri("/api/exception-translator-test/unauthorized")
            .exchange()
            .expectStatus()
            .isUnauthorized()
            .expectHeader()
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .expectBody()
            .jsonPath("$.message")
            .isEqualTo("error.http.401")
            .jsonPath("$.path")
            .isEqualTo("/api/exception-translator-test/unauthorized")
            .jsonPath("$.detail")
            .value(AnyOf.anyOf(IsEqual.equalTo("test authentication failed!"), IsEqual.equalTo("Invalid credentials")));
    }

    @Test
    void testMethodNotSupported() {
        webTestClient
            .post()
            .uri("/api/exception-translator-test/access-denied")
            .exchange()
            .expectStatus()
            .isEqualTo(HttpStatus.METHOD_NOT_ALLOWED)
            .expectHeader()
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .expectBody()
            .jsonPath("$.message")
            .isEqualTo("error.http.405")
            .jsonPath("$.detail")
            .isEqualTo("405 METHOD_NOT_ALLOWED \"Request method 'POST' is not supported.\"");
    }

    @Test
    void testExceptionWithResponseStatus() {
        webTestClient
            .get()
            .uri("/api/exception-translator-test/response-status")
            .exchange()
            .expectStatus()
            .isBadRequest()
            .expectHeader()
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .expectBody()
            .jsonPath("$.message")
            .isEqualTo("error.http.400")
            .jsonPath("$.title")
            .isEqualTo("test response status");
    }

    @Test
    void testInternalServerError() {
        webTestClient
            .get()
            .uri("/api/exception-translator-test/internal-server-error")
            .exchange()
            .expectHeader()
            .contentType(MediaType.APPLICATION_PROBLEM_JSON)
            .expectBody()
            .jsonPath("$.message")
            .isEqualTo("error.http.500")
            .jsonPath("$.title")
            .isEqualTo("Internal Server Error");
    }
}
