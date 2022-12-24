package com.baeldung.samples.domain;

import io.micrometer.tracing.test.simple.SimpleTracer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.micrometer.tracing.test.simple.TracerAssert.assertThat;

@ExtendWith(SpringExtension.class)
@ComponentScan(basePackageClasses = GreetingService.class)
@EnableAutoConfiguration
@EnableTestObservation
class GreetingServiceTracingIntegrationTest {

    @Autowired
    GreetingService service;
    @Value("${management.tracing.enabled:true}")
    boolean tracingEnabled;
    @Autowired
    SimpleTracer tracer;

    @Test
    void testEnabledTracing() {
        Assertions.assertThat(tracingEnabled).isTrue();
    }

    @Test
    void testTracingForGreeting() {
        service.sayHello();
        assertThat(tracer)
          .onlySpan()
          .hasNameEqualTo("greeting-service#say-hello")
          .isEnded();
    }

}
