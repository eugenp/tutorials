package com.baeldung.reactive.cors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import com.baeldung.reactive.cors.annotated.CorsOnAnnotatedElementsApplication;

@SpringBootTest(classes = CorsOnAnnotatedElementsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CorsOnAnnotatedElementsLiveTest {

    private static final String BASE_URL = "http://localhost:8081";
    private static final String BASE_CORS_ON_METHODS_URL = "/cors-on-methods";
    private static final String BASE_CORS_ON_CONTROLLER_URL = "/cors-on-controller";
    private static final String CONTROLLER_CORS_ALLOWED_ORIGIN = "http://allowed-origin.com";
    private static final String CORS_DEFAULT_ORIGIN = "http://default-origin.com";

    private static WebTestClient client;

    @BeforeAll
    public static void setup() {
        client = WebTestClient.bindToServer()
            .baseUrl(BASE_URL)
            .defaultHeader("Origin", CORS_DEFAULT_ORIGIN)
            .build();
    }

    @Test
    public void whenRequestingMethodCorsEnabledEndpoint_thenObtainResponseWithCorsHeaders() {
        ResponseSpec response = client.put()
            .uri(BASE_CORS_ON_METHODS_URL + "/cors-enabled-endpoint")
            .exchange();

        response.expectHeader()
            .valueEquals("Access-Control-Allow-Origin", "*");
    }

    @Test
    public void whenPreflightMethodCorsEnabled_thenObtainResponseWithCorsHeaders() {
        ResponseSpec response = client.options()
            .uri(BASE_CORS_ON_METHODS_URL + "/cors-enabled-endpoint")
            .header("Access-Control-Request-Method", "PUT")
            .exchange();

        response.expectHeader()
            .valueEquals("Access-Control-Allow-Origin", "*");
        response.expectHeader()
            .valueEquals("Access-Control-Allow-Methods", "PUT");
        response.expectHeader()
            .exists("Access-Control-Max-Age");
    }

    @Test
    public void whenRequestingMethodCorsDisabledEndpoint_thenObtainResponseWithoutCorsHeaders() {
        ResponseSpec response = client.put()
            .uri(BASE_CORS_ON_METHODS_URL + "/cors-disabled-put-endpoint")
            .exchange();

        response.expectHeader()
            .doesNotExist("Access-Control-Allow-Origin");
    }

    @Test
    public void whenRequestingMethodCorsRestrictiveOrigin_thenObtainForbiddenResponse() {
        ResponseSpec response = client.put()
            .uri(BASE_CORS_ON_METHODS_URL + "/cors-enabled-origin-restrictive-endpoint")
            .exchange();

        response.expectStatus()
            .isForbidden();
    }

    @Test
    public void whenPreflightMethodCorsRestrictiveOrigin_thenObtainForbiddenResponse() {
        ResponseSpec response = client.options()
            .uri(BASE_CORS_ON_METHODS_URL + "/cors-enabled-origin-restrictive-endpoint")
            .header("Access-Control-Request-Method", "PUT")
            .exchange();

        response.expectStatus()
            .isForbidden();
    }

    @Test
    public void whenPreflightMethodCorsRestrictiveHeader_thenObtainResponseWithAllowedHeaders() {
        ResponseSpec response = client.options()
            .uri(BASE_CORS_ON_METHODS_URL + "/cors-enabled-header-restrictive-endpoint")
            .header("Access-Control-Request-Method", "PUT")
            .header("Access-Control-Request-Headers", "Baeldung-Not-Allowed, Baeldung-Allowed")
            .exchange();

        response.expectHeader()
            .valueEquals("Access-Control-Allow-Headers", "Baeldung-Allowed");
    }

    @Test
    public void whenPreflightControllerCorsRegularEndpoint_thenObtainResponseWithCorsHeaders() {
        ResponseSpec response = client.options()
            .uri(BASE_CORS_ON_CONTROLLER_URL + "/regular-endpoint")
            .header("Origin", CONTROLLER_CORS_ALLOWED_ORIGIN)
            .header("Access-Control-Request-Method", "PUT")
            .exchange();

        response.expectHeader()
            .valueEquals("Access-Control-Allow-Origin", CONTROLLER_CORS_ALLOWED_ORIGIN);
    }

    @Test
    public void whenPreflightControllerCorsRestrictiveOrigin_thenObtainResponseWithCorsHeaders() {
        ResponseSpec response = client.options()
            .uri(BASE_CORS_ON_CONTROLLER_URL + "/cors-enabled-origin-restrictive-endpoint")
            .header("Origin", CONTROLLER_CORS_ALLOWED_ORIGIN)
            .header("Access-Control-Request-Method", "PUT")
            .exchange();

        response.expectHeader()
            .valueEquals("Access-Control-Allow-Origin", CONTROLLER_CORS_ALLOWED_ORIGIN);
    }

    @Test
    public void whenPreflightControllerCorsRestrictiveOriginWithAnotherAllowedOrigin_thenObtainResponseWithCorsHeaders() {
        final String anotherAllowedOrigin = "http://another-allowed-origin.com";
        ResponseSpec response = client.options()
            .uri(BASE_CORS_ON_CONTROLLER_URL + "/cors-enabled-origin-restrictive-endpoint")
            .header("Origin", anotherAllowedOrigin)
            .header("Access-Control-Request-Method", "PUT")
            .exchange();

        response.expectHeader()
            .valueEquals("Access-Control-Allow-Origin", anotherAllowedOrigin);
    }

    @Test
    public void whenPreflightControllerCorsExposingHeaders_thenObtainResponseWithCorsHeaders() {
        ResponseSpec response = client.options()
            .uri(BASE_CORS_ON_CONTROLLER_URL + "/cors-enabled-exposed-header-endpoint")
            .header("Origin", CONTROLLER_CORS_ALLOWED_ORIGIN)
            .header("Access-Control-Request-Method", "PUT")
            .exchange();

        response.expectHeader()
            .valueEquals("Access-Control-Expose-Headers", "Baeldung-Exposed");
    }
}
