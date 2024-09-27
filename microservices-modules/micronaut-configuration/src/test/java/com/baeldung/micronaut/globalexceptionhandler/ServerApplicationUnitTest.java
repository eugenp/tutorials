package com.baeldung.micronaut.globalexceptionhandler;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.specification.RequestSpecification;

@MicronautTest
class ServerApplicationUnitTest {

    private static final String ERRONEOUS_ENDPOINTS_PATH = "micronaut-configuration-tutorials/erroneous-endpoint";
    private static final String PROBES_ENDPOINTS_PATH = "micronaut-configuration-tutorials/probe";

    @Test
    public void givenNotFoundStatusHandler_whenRequestTheHandlerEndpoint_thenResponseIsNotFound(RequestSpecification spec) {
        spec.given()
            .basePath("micronaut-configuration-tutorials")
            .header("some-header", "some-value")
            .when()
            .get("/notfound")
            .then()
            .statusCode(404)
            .body(Matchers.containsString("\"message\":\"Page Not Found\",\"_links\":"));
    }

    @Test
    public void givenNotFoundStatusHandler_whenRequestThatThrows404_thenResponseIsHandled(RequestSpecification spec) {
        spec.given()
            .basePath(ERRONEOUS_ENDPOINTS_PATH)
            .when()
            .get("/not-found-error")
            .then()
            .statusCode(404)
            .body(Matchers.containsString("\"message\":\"Page Not Found\",\"_links\":"));
    }

    @Test
    public void givenCustomExceptionHandler_whenRequestThatThrowsDifferentError_thenResponseIsNotHandled(RequestSpecification spec) {
        spec.given()
            .basePath(ERRONEOUS_ENDPOINTS_PATH)
            .when()
            .get("/internal-server-error")
            .then()
            .statusCode(500)
            .body(containsString("\"message\":\"Internal Server Error\""));
    }

    @Test
    public void givenCustomExceptionHandler_whenRequestThatThrowsDifferentErrorIsSkipped_thenResponseIsNotHandled(RequestSpecification spec) {
        spec.given()
            .basePath(ERRONEOUS_ENDPOINTS_PATH)
            .header("skip-error", true)
            .when()
            .get("/internal-server-error")
            .then()
            .statusCode(200)
            .body(is("Endpoint 2"));
    }

    @Test
    public void givenCustomExceptionHandler_whenRequestThatThrowsCustomException_thenResponseIsHandled(RequestSpecification spec) {
        spec.given()
            .basePath(ERRONEOUS_ENDPOINTS_PATH)
            .when()
            .get("/custom-error")
            .then()
            .statusCode(200)
            .body(is("Custom Exception was handled"));
    }

    @Test
    public void givenCustomExceptionHandler_whenRequestThatThrowsCustomExceptionIsSkipped_thenResponseIsNotHandled(RequestSpecification spec) {
        spec.given()
            .basePath(ERRONEOUS_ENDPOINTS_PATH)
            .header("skip-error", true)
            .when()
            .get("/custom-error")
            .then()
            .statusCode(200)
            .body(is("Endpoint 3"));
    }

    @Test
    public void givenCustomExceptionHandler_whenRequestThatThrowsCustomChildException_thenResponseIsHandled(RequestSpecification spec) {
        spec.given()
            .basePath(ERRONEOUS_ENDPOINTS_PATH)
            .when()
            .get("/custom-child-error")
            .then()
            .statusCode(200)
            .body(is("Custom Exception was handled"));
    }

    @Test
    public void givenCustomExceptionHandler_whenRequestThatIsOK_thenResponseIsNotHandled(RequestSpecification spec) {
        spec.given()
            .basePath(PROBES_ENDPOINTS_PATH)
            .when()
            .get("/liveness")
            .then()
            .statusCode(200)
            .body(emptyOrNullString());
    }

    @Test
    public void givenLocalUnsupportedOperationExceptionHandler_whenRequestThatThrowsThisException_thenResponseIsHandled(RequestSpecification spec) {
        spec.given()
            .basePath(ERRONEOUS_ENDPOINTS_PATH)
            .when()
            .get("/unsupported-operation")
            .then()
            .statusCode(404)
            .body(containsString("\"message\":\"Unsupported Operation\""));
    }

    @Test
    public void givenLocalUnsupportedOperationExceptionHandler_whenRequestThatThrowsThisExceptionInOtherController_thenResponseIsNotHandled(RequestSpecification spec) {
        spec.given()
            .basePath(PROBES_ENDPOINTS_PATH)
            .when()
            .get("/readiness")
            .then()
            .statusCode(500)
            .body(containsString("\"message\":\"Internal Server Error\""));
    }
}
