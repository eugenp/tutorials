package org.baeldung.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.FormAuthConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class FooLiveTest {
    private static final String URL_PREFIX = "http://localhost:8080/spring-security-rest";
    private FormAuthConfig formConfig = new FormAuthConfig(URL_PREFIX + "/login", "username", "password");

    private RequestSpecification givenAuth() {
        return RestAssured.given().auth().form("user", "userPass", formConfig);
    }

    @Test
    public void whenMethodArgumentMismatch_thenBadRequest() {
        final Response response = givenAuth().get(URL_PREFIX + "/api/foos/ccc");
        final ApiError error = response.as(ApiError.class);
        assertEquals(HttpStatus.BAD_REQUEST, error.getStatus());
        assertEquals(1, error.getErrors().size());
        assertTrue(error.getErrors().get(0).contains("should be of type"));

    }

    @Test
    public void whenNoHandlerForHttpRequest_thenNotFound() {
        final Response response = givenAuth().delete(URL_PREFIX + "/api/xx");
        final ApiError error = response.as(ApiError.class);
        assertEquals(HttpStatus.NOT_FOUND, error.getStatus());
        assertEquals(1, error.getErrors().size());
        assertTrue(error.getErrors().get(0).contains("No handler found"));
    }

    @Test
    public void whenHttpRequestMethodNotSupported_thenMethodNotAllowed() {
        final Response response = givenAuth().delete(URL_PREFIX + "/api/foos/1");
        final ApiError error = response.as(ApiError.class);
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, error.getStatus());
        assertEquals(1, error.getErrors().size());
        assertTrue(error.getErrors().get(0).contains("Supported methods are"));
    }

    @Test
    public void whenSendInvalidHttpMediaType_thenUnsupportedMediaType() {
        final Response response = givenAuth().body("").post(URL_PREFIX + "/api/foos");
        final ApiError error = response.as(ApiError.class);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, error.getStatus());
        assertEquals(1, error.getErrors().size());
        assertTrue(error.getErrors().get(0).contains("media type is not supported"));
    }

}
