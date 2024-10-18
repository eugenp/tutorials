package com.baeldung.metrics.prometheus;

import java.io.IOException;
import java.util.Random;

import io.prometheus.metrics.core.metrics.Counter;
import io.prometheus.metrics.core.metrics.Gauge;
import io.prometheus.metrics.core.metrics.Histogram;
import io.prometheus.metrics.core.metrics.Info;
import io.prometheus.metrics.core.metrics.StateSet;
import io.prometheus.metrics.core.metrics.Summary;
import io.prometheus.metrics.exporter.httpserver.HTTPServer;
import io.prometheus.metrics.instrumentation.jvm.JvmMetrics;

public class PrometheusApp {

    public static void main(String[] args) throws InterruptedException, IOException {

        JvmMetrics.builder().register();

        HTTPServer server = HTTPServer.builder()
          .port(9400)
          .buildAndStart();

        Counter requestCounter = Counter.builder()
          .name("http_requests_total")
          .help("Total number of HTTP requests")
          .labelNames("method", "status")
          .register();

        requestCounter.labelValues("GET", "200").inc();

        Gauge memoryUsage = Gauge.builder()
          .name("memory_usage_bytes")
          .help("Current memory usage in bytes")
          .register();

        memoryUsage.set(5000000);

        Histogram requestLatency = Histogram.builder()
          .name("http_request_latency_seconds")
          .help("Tracks HTTP request latency in seconds")
          .labelNames("method")
          .register();

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            double latency = 0.1 + (3 * random.nextDouble());
            requestLatency.labelValues("GET").observe(latency);
        }

        Summary requestDuration = Summary.builder()
          .name("http_request_duration_seconds")
          .help("Tracks the duration of HTTP requests in seconds")
          .quantile(0.5, 0.05)
          .quantile(0.9, 0.01)
          .register();

        for (int i = 0; i < 100; i++) {
            double duration = 0.05 + (2 * random.nextDouble());
            requestDuration.observe(duration);
        }

        Info appInfo = Info.builder()
          .name("app_info")
          .help("Application version information")
          .labelNames("version", "build")
          .register();

        appInfo.addLabelValues("1.0.0", "12345");

        StateSet stateSet = StateSet.builder()
          .name("feature_flags")
          .help("Feature flags")
          .labelNames("env")
          .states("feature1")
          .register();

        stateSet.labelValues("dev").setFalse("feature1");

        System.out.println("HTTPServer listening on http://localhost:" + server.getPort() + "/metrics");

        Thread.currentThread().join();
    }
}
