package controllers;

import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import play.twirl.api.Html;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

public class HomeControllerTest extends WithApplication {

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testIndex() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void testApplyHtml() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/baeldung/html");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void testBadRequestPage() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/baeldung/bad-req");

        Result result = route(app, request);
        assertEquals(BAD_REQUEST, result.status());
    }

    @Test
    public void testNotFoundPage() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/baeldung/not-found");

        Result result = route(app, request);
        assertEquals(NOT_FOUND, result.status());
    }

    @Test
    public void testCustomContentType() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/baeldung/custom-content-type");

        Result result = route(app, request);
        assertTrue(result.contentType().isPresent());
        assertEquals("text/html", result.contentType().get());
    }

    @Test
    public void testAsyncOperation() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/baeldung/async");

        Result result = route(app, request);
        assertEquals(OK, result.status());
    }

    @Test
    public void testSetHeaders() {
        Http.RequestBuilder request = new Http.RequestBuilder()
                .method(GET)
                .uri("/baeldung/headers");

        Result result = route(app, request);
        final Optional<String> headerOptional = result.header("Header-Key");
        assertTrue(headerOptional.isPresent());
        assertEquals("Some value", headerOptional.get());
    }
}
