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
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleGauge;

@Path("/trivia")
public class TriviaResource {
    Logger logger = LogManager.getLogger(TriviaResource.class);

    private final Tracer tracer;
    private final Meter meter;
    private TriviaService triviaService;
    
    static final String OTEL_SERVICE_NAME = "trivia-service";
    static final String WORD_SERVICE_URL = "http://localhost:8081/api/words/random";

    public TriviaResource() {
        this.triviaService = new TriviaService(new OkHttpClient());

        TelemetryConfig telemetryConfig = TelemetryConfig.getInstance();
        this.tracer = telemetryConfig.getOpenTelemetry().getTracer(OTEL_SERVICE_NAME, "0.0.1-SNAPSHOT");
        this.meter = telemetryConfig.getOpenTelemetry().getMeter(OTEL_SERVICE_NAME);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response retrieveCard() {
        DoubleGauge requestDuration = meter.gaugeBuilder("http.request.duration")
            .setDescription("Duration of HTTP request")
            .setUnit("ms").build();

        Span span = tracer.spanBuilder("retrieveCard")
            .setAttribute("http.method", "GET")
            .setAttribute("http.url", WORD_SERVICE_URL)
            .setSpanKind(SpanKind.CLIENT).startSpan();


        long callDuration = 0;
        try (Scope scope = span.makeCurrent()) {
            Instant start = Instant.now();

            span.addEvent("http.request.word-api", start);
            
            WordResponse wordResponse = triviaService.requestWordFromSource(WORD_SERVICE_URL);
            Instant end = Instant.now();
            callDuration = end.toEpochMilli() - start.toEpochMilli();
            requestDuration.set(callDuration, Attributes.builder().put("request.duration", "word-wonder-api").build());
                            
            span.setAttribute("http.status_code", wordResponse.httpResponseCode());
            span.setAttribute("http.request_duration", callDuration);

            logger.info("word-api response payload: {}", wordResponse.wordWithDefinition());

            return Response.ok(wordResponse.wordWithDefinition()).build();
        } catch(IOException exception) {
            span.setStatus(StatusCode.ERROR, "Error while retreiving info from word wonder service");
            span.recordException(exception);
            logger.error("Error while calling word wonder service", exception);
            return Response.noContent().build();
        } finally {
            span.end();
        }
    }
}