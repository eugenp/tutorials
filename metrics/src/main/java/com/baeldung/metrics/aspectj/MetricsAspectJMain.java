package com.baeldung.metrics.aspectj;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

public class MetricsAspectJMain {
    static final MetricRegistry registry = new MetricRegistry();

    public static void main(String args[]) throws InterruptedException {
        startReport();

        ObjectRunner runner = new ObjectRunner();

        for (int i = 0; i < 5; i++) {
            runner.run();
        }

        Thread.sleep(3000L);
    }

    static void startReport() {
        SharedMetricRegistries.add(ObjectRunner.REGISTRY_NAME, registry);

        ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .outputTo(new PrintStream(System.out))
                .build();
        reporter.start(3, TimeUnit.SECONDS);
    }
}
