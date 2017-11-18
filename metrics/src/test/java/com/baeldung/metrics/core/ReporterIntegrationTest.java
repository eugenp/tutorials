package com.baeldung.metrics.core;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.codahale.metrics.*;

public class ReporterIntegrationTest {
    @Test
    public void whenConsoleReporter_thenOutputReport() {
        MetricRegistry metricRegistry = new MetricRegistry();

        Meter meter = metricRegistry.meter("meter");
        meter.mark();
        meter.mark(200);
        Histogram histogram = metricRegistry.histogram("histogram");
        histogram.update(12);
        histogram.update(17);
        histogram.update(20);
        Counter counter = metricRegistry.counter("counter");
        counter.inc();
        counter.dec();
        counter.inc();
        counter.inc();
        counter.inc();
        counter.inc();

        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry).build();
        reporter.start(5, TimeUnit.MICROSECONDS);
        reporter.report();
    }
}
