package controllers;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

public class HomeControllerUnitTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void givenRequest_whenRootPath_ThenStatusOkay() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void givenRequest_whenHtmlPath_ThenStatusOkay() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/baeldung/html");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void givenRequest_whenBadRequest_ThenStatusBadRequest() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/baeldung/bad-req");

        Result result = route(app, request);
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void givenRequest_whenNotFound_ThenStatusNotFound() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/baeldung/not-found");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

    @Test
    public void givenRequest_whenCustomContentTypePath_ThenContextTypeTextHtml() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/baeldung/custom-content-type");

        Result result = route(app, request);
        assertTrue(result.contentType().isPresent());
        assertEquals("text/html", result.contentType().get());
    }

    @Test
    public void givenRequest_whenAsyncPath_ThenStatusOkay() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/baeldung/async");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void givenRequest_whenHeadersPath_ThenCustomHeaderFieldSet() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/baeldung/headers");

        Result result = route(app, request);
        final Optional<String> headerOptional = result.header("Header-Key");
        assertTrue(headerOptional.isPresent());
        assertEquals("Some value", headerOptional.get());
    }
}
