package org.baeldung.monitoring;

import com.codahale.metrics.MetricRegistry;

public final class MetricRegistrySingleton {

    public static final MetricRegistry metrics = new MetricRegistry();

    private MetricRegistrySingleton() {
        throw new AssertionError();
    }

}
