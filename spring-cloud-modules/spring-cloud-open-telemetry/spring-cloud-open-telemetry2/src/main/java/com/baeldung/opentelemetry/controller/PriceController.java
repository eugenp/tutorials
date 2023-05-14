package com.baeldung.opentelemetry.controller;

import com.baeldung.opentelemetry.model.Price;
import com.baeldung.opentelemetry.repository.PriceRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class PriceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceController.class);

    private final PriceRepository priceRepository;

    private final MeterRegistry registry;

    private final Tracer tracer;

    private final OpenTelemetry openTelemetry;

    TextMapGetter<HttpHeaders> getter = new TextMapGetter<HttpHeaders>() {
        @Override
        public String get(HttpHeaders headers, String s) {
            return headers.getFirst(s);
        }

        @Override
        public Iterable<String> keys(HttpHeaders headers) {
            List<String> keys = new ArrayList<>();
            Map<String, String> requestHeaders = headers.toSingleValueMap();
            requestHeaders.forEach((k, v) -> keys.add(k));
            return keys;
        }
    };

    @Autowired
    public PriceController(PriceRepository priceRepository, MeterRegistry registry, Tracer tracer, OpenTelemetry openTelemetry) {
        this.priceRepository = priceRepository;
        this.registry = registry;
        this.tracer = tracer;
        this.openTelemetry = openTelemetry;
    }

    @GetMapping(path = "/price/{id}")
    public Price getPrice(@RequestHeader HttpHeaders headers, @PathVariable("id") long productId) {
        Context extractedContext = openTelemetry.getPropagators().getTextMapPropagator().extract(Context.current(), headers, getter);
        try (Scope scope = extractedContext.makeCurrent()) {
            Span parentSpan = tracer.spanBuilder("price-service-parent").setParent(Context.current()).setSpanKind(SpanKind.SERVER).startSpan();
            try (Scope ignored = parentSpan.makeCurrent()) {
                LOGGER.info("hello Getting Price details for Product Id {}", productId);
                registry.counter("items.total", "id", String.valueOf(productId)).increment();
                LOGGER.info("processing id {}", productId);
                return priceRepository.getPrice(productId);
            } finally {
                parentSpan.end();
            }
        }
    }
}
