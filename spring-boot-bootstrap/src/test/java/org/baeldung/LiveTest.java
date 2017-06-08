package org.baeldung;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.Test;
import org.springframework.http.HttpStatus;

public class LiveTest {
    private static final String API_ROOT = "http://localhost:8081//api/books";

    @Test
    public void whenTry_thenOK() {
        final Response response = RestAssured.get(API_ROOT);
        assertEquals(200, response.statusCode());
        System.out.println(response.asString());

    }

    @Test
    public void whenMethodArgumentMismatch_thenBadRequest() {
        final Response response = RestAssured.get(API_ROOT + "/ccc");
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode());
        assertTrue(response.asString()
            .contains("should be of type"));
    }

    @Test
    public void whenNoHandlerForHttpRequest_thenNotFound() {
        final Response response = RestAssured.delete(API_ROOT + "/xx");
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode());

        assertTrue(response.asString()
            .contains("No handler found"));
    }

    @Test
    public void whenHttpRequestMethodNotSupported_thenMethodNotAllowed() {
        final Response response = RestAssured.delete(API_ROOT + "/1");
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.statusCode());
        assertTrue(response.asString()
            .contains("Supported methods are"));

    }

    @Test
    public void whenSendInvalidHttpMediaType_thenUnsupportedMediaType() {
        final Response response = RestAssured.given()
            .body("")
            .post(API_ROOT);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.statusCode());
        assertTrue(response.asString()
            .contains("media type is not supported"));

    }

}
