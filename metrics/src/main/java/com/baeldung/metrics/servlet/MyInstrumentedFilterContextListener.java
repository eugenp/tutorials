package com.baeldung.metrics.servlet;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlet.InstrumentedFilterContextListener;

class MyInstrumentedFilterContextListener extends InstrumentedFilterContextListener {
    private static final MetricRegistry REGISTRY = new MetricRegistry();

    @Override
    protected MetricRegistry getMetricRegistry() {
        return REGISTRY;
    }
}
