package com.baeldung.sleuth.traceid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import brave.Span;
import brave.Tracer;

@RestController
public class SleuthTraceIdController {

    private static final Logger logger = LoggerFactory.getLogger(SleuthTraceIdController.class);

    @Autowired
    private Tracer tracer;

    @GetMapping("/hello")
    public String hello() {
        logger.info("Hello with Sleuth");
        Span span = tracer.currentSpan();
        if (span != null) {
            logger.info("Span ID hex {}", span.context().spanIdString());
            logger.info("Span ID decimal {}", span.context().spanId());
            logger.info("Trace ID hex {}", span.context().traceIdString());
            logger.info("Trace ID decimal {}", span.context().traceId());
        }
        return "hello";
    }
}
