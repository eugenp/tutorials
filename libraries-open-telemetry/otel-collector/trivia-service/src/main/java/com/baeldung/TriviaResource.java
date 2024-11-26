package com.baeldung;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.time.Instant;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.Context;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;

@Path("/trivia")
public class TriviaResource {
    Logger logger = LogManager.getLogger(TriviaResource.class);

    private final Tracer tracer;
    private final Meter meter;
    private TriviaService triviaService;
    private final LongCounter httpRequestCounter;
    
    static final String OTEL_SERVICE_NAME = "trivia-service";
    static final String WORD_SERVICE_URL = "http://127.0.0.1:8081/api/words/random";

    public TriviaResource() {
        TelemetryConfig telemetryConfig = TelemetryConfig.getInstance();
        this.tracer = telemetryConfig.getOpenTelemetry().getTracer(OTEL_SERVICE_NAME, "0.0.1-SNAPSHOT");
        this.meter = telemetryConfig.getOpenTelemetry().getMeter(OTEL_SERVICE_NAME);
        var textMapPropagator = telemetryConfig.getOpenTelemetry().getPropagators().getTextMapPropagator();

        this.triviaService = new TriviaService(new OkHttpClient(), textMapPropagator);

        this.httpRequestCounter = meter.counterBuilder("http.request.count")
            .setDescription("Counts the number of HTTP requests")
            .setUnit("1")
            .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response retrieveCard() {
        httpRequestCounter.add(1, Attributes.builder().put("endpoint", "/trivia").build());

        Span span = tracer.spanBuilder("retreive_card")
            .setAttribute("http.method", "GET")
            .setAttribute("http.url", WORD_SERVICE_URL)
            .setSpanKind(SpanKind.CLIENT).startSpan();


        try (Scope scope = span.makeCurrent()) {
            Instant start = Instant.now();
            span.addEvent("http.request.word-api", start);
            
            WordResponse wordResponse = triviaService.requestWordFromSource(Context.current().with(span), WORD_SERVICE_URL);

            span.setAttribute("http.status_code", wordResponse.httpResponseCode());

            logger.info("word-api response payload: {}", wordResponse.wordWithDefinition());

            return Response.ok(wordResponse.wordWithDefinition()).build();
        } catch(IOException exception) {
            span.setStatus(StatusCode.ERROR, "Error retreiving info from dictionary service");
            span.recordException(exception);
            logger.error("Error while calling dictionary service", exception);
            return Response.noContent().build();
        } finally {
            span.end();
        }
    }
}