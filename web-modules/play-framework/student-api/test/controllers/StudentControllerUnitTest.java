package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class StudentControllerUnitTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void givenStudentPostData_whenCreatingStudent_ThenShouldReturnCreatedStudent() {
        final ObjectNode jsonNode = Json.newObject();
        jsonNode.put("firstName", "John");
        jsonNode.put("lastName", "Baeldung");
        jsonNode.put("age", 25);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(POST)
                .bodyJson(jsonNode)
                .uri("/");

        Result result = route(app, request);
        assertEquals(CREATED, result.status());
        assertTrue(result.contentType().isPresent());
        assertEquals("application/json", result.contentType().get());
    }

    @Test
    public void givenUrlToListStudents_whenListingStudents_ThenShouldReturnStudentList() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
        assertTrue(result.contentType().isPresent());
        assertEquals("application/json", result.contentType().get());
    }

    @Test
    public void givenUrlToRetrieveSingleStudent_whenRetrievingStudent_ThenShouldReturn404NotFound() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/1");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
        assertTrue(result.contentType().isPresent());
        assertEquals("application/json", result.contentType().get());
    }

    @Test
    public void givenUrlToUpdateStudent_whenaUpdatingStudent_ThenShouldFail() {
        final ObjectNode jsonNode = Json.newObject();
        jsonNode.put("firstName", "John");
        jsonNode.put("lastName", "Baeldung");
        jsonNode.put("age", 25);

        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(PUT)
                .bodyJson(jsonNode)
                .uri("/");

        Result result = route(app, request);
        assertEquals(INTERNAL_SERVER_ERROR, result.status());
        assertTrue(result.contentType().isPresent());
        assertEquals("application/json", result.contentType().get());
    }

    @Test
    public void givenIdToDeleteStudent_whenDeletingStudent_ThenShouldFail() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(DELETE)
                .uri("/1");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
        assertTrue(result.contentType().isPresent());
        assertEquals("application/json", result.contentType().get());
    }
}
