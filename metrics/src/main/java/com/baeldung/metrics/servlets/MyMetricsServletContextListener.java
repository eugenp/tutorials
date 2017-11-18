package com.baeldung.metrics.servlets;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

public class MyMetricsServletContextListener extends MetricsServlet.ContextListener {
    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();

    static {
        Counter counter = METRIC_REGISTRY.counter("m01-counter");
        counter.inc();

        Histogram histogram = METRIC_REGISTRY.histogram("m02-histogram");
        histogram.update(5);
        histogram.update(20);
        histogram.update(100);
    }

    @Override
    protected MetricRegistry getMetricRegistry() {
        return METRIC_REGISTRY;
    }
}
