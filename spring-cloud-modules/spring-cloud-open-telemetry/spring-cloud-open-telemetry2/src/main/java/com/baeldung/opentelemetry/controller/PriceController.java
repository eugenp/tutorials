package com.baeldung.opentelemetry.controller;

import com.baeldung.opentelemetry.model.Price;
import com.baeldung.opentelemetry.repository.PriceRepository;
import io.micrometer.core.instrument.MeterRegistry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PriceController.class);

    private final PriceRepository priceRepository;

    private final MeterRegistry registry;

    private final Tracer tracer;

    @Autowired
    public PriceController(PriceRepository priceRepository, MeterRegistry registry, Tracer tracer) {
        this.priceRepository = priceRepository;
        this.registry = registry;
        this.tracer = tracer;
    }

    @GetMapping(path = "/price/{id}")
    public Price getPrice(@PathVariable("id") long productId) {

        Span parentSpan = tracer.spanBuilder("print-service-parent").setSpanKind(SpanKind.SERVER).startSpan();
        try {
            LOGGER.info("Getting Price details for Product Id {}", productId);
            registry.counter("items.total", "id", String.valueOf(productId)).increment();
            LOGGER.info("processing id {}", productId);
            return priceRepository.getPrice(productId);
        } finally {
            parentSpan.end();
        }

  }
}
