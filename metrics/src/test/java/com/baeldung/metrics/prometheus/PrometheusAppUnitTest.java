package com.baeldung.metrics.prometheus;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.prometheus.metrics.core.metrics.Counter;
import io.prometheus.metrics.core.metrics.Gauge;
import io.prometheus.metrics.core.metrics.Histogram;
import io.prometheus.metrics.core.metrics.Info;
import io.prometheus.metrics.core.metrics.StateSet;
import io.prometheus.metrics.core.metrics.Summary;
import io.prometheus.metrics.exporter.httpserver.HTTPServer;
import io.prometheus.metrics.instrumentation.jvm.JvmMetrics;
import io.prometheus.metrics.model.registry.PrometheusRegistry;

class PrometheusAppUnitTest {

    private Counter requestCounter;

    private Gauge memoryUsage;

    private Histogram requestLatency;

    private Summary requestDuration;

    private Info appInfo;

    private StateSet stateSet;

    private HTTPServer httpServer;

    @BeforeEach
    void setUp() throws IOException {

        // Initialize JVM Metrics
        JvmMetrics.builder().register();

        // Initialize HTTP Server
        httpServer = HTTPServer.builder().port(9400).buildAndStart();


        // Initialize all the metrics within the new registry
        requestCounter = Counter.builder()
          .name("http_requests_total")
          .help("Total number of HTTP requests")
          .labelNames("method", "status")
          .register();

        memoryUsage = Gauge.builder()
          .name("memory_usage_bytes")
          .help("Current memory usage in bytes")
          .register();

        requestLatency = Histogram.builder()
          .name("http_request_latency_seconds")
          .help("Tracks HTTP request latency in seconds")
          .labelNames("method")
          .register();

        requestDuration = Summary.builder()
          .name("http_request_duration_seconds")
          .help("Tracks the duration of HTTP requests in seconds")
          .quantile(0.5, 0.05)
          .quantile(0.9, 0.01)
          .register();

        appInfo = Info.builder()
          .name("app_info")
          .help("Application version information")
          .labelNames("version", "build")
          .register();

        stateSet = StateSet.builder()
          .name("feature_flags")
          .help("Feature flags")
          .labelNames("env")
          .states("feature1")
          .register();
    }


    @AfterEach
    void tearDown() {
        if (httpServer != null) {
            httpServer.stop();  // Stop the server after each test
        }
        PrometheusRegistry.defaultRegistry.unregister(requestCounter);
        PrometheusRegistry.defaultRegistry.unregister(memoryUsage);
        PrometheusRegistry.defaultRegistry.unregister(requestLatency);
        PrometheusRegistry.defaultRegistry.unregister(requestDuration);
        PrometheusRegistry.defaultRegistry.unregister(appInfo);
        PrometheusRegistry.defaultRegistry.unregister(stateSet);
    }

    @Test
    void givenCounter_whenIncremented_thenMetricExposedCorrectly() throws IOException {
        // given
        requestCounter.labelValues("GET", "200").inc();

        // when
        String metrics = fetchMetricsFromEndpoint();

        // then
        assertTrue(metrics.contains("http_requests_total{method=\"GET\",status=\"200\"}"));
    }

    @Test
    void givenGauge_whenSet_thenMetricExposedCorrectly() throws IOException {
        // given
        memoryUsage.set(5000000);

        // when
        String metrics = fetchMetricsFromEndpoint();

        // then
        assertTrue(metrics.contains("memory_usage_bytes 5000000"));
    }

    @Test
    void givenHistogram_whenObserved_thenMetricExposedCorrectly() throws IOException {
        // given
        requestLatency.labelValues("GET").observe(0.25);

        // when
        String metrics = fetchMetricsFromEndpoint();

        // then
        assertTrue(metrics.contains("http_request_latency_seconds"));
        assertTrue(metrics.contains("http_request_latency_seconds_sum"));
        assertTrue(metrics.contains("http_request_latency_seconds_count"));
    }

    @Test
    void givenSummary_whenObserved_thenMetricExposedCorrectly() throws IOException {
        // given
        requestDuration.observe(0.15);

        // when
        String metrics = fetchMetricsFromEndpoint();

        // then
        assertTrue(metrics.contains("http_request_duration_seconds"));
        assertTrue(metrics.contains("http_request_duration_seconds_sum"));
        assertTrue(metrics.contains("http_request_duration_seconds_count"));
    }

    @Test
    void givenInfo_whenSet_thenMetricExposedCorrectly() throws IOException {
        // given
        appInfo.addLabelValues("1.0.0", "12345");

        // when
        String metrics = fetchMetricsFromEndpoint();

        // then
        assertTrue(metrics.contains("app_info{build=\"12345\",version=\"1.0.0\"} 1"));
    }

    @Test
    void givenStateSet_whenSetFalse_thenMetricExposedCorrectly() throws IOException {
        // given
        stateSet.labelValues("dev").setFalse("feature1");

        // when
        String metrics = fetchMetricsFromEndpoint();

        // then
        assertTrue(metrics.contains("feature_flags{env=\"dev\",feature_flags=\"feature1\"} 0"));
    }

    private String fetchMetricsFromEndpoint() throws IOException {
        URL url = new URL("http://localhost:9400/metrics");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine).append("\n");
        }

        in.close();
        connection.disconnect();

        return content.toString();
    }
}