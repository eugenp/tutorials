package com.baeldung;

import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.baggage.propagation.W3CBaggagePropagator;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.metrics.export.MetricReader;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;

import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.LogRecordProcessor;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.LogRecordExporter;
import io.opentelemetry.exporter.otlp.http.metrics.OtlpHttpMetricExporter;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;

import io.opentelemetry.instrumentation.log4j.appender.v2_17.OpenTelemetryAppender;
import java.util.concurrent.TimeUnit;

public class TelemetryConfig {

    private static final String OTLP_TRACES_ENDPOINT = "http://telemetry-collector:4318/v1/traces";
    private static final String OTLP_METRICS_ENDPOINT = "http://telemetry-collector:4318/v1/metrics";
    private static final String OTLP_LOGS_ENDPOINT = "http://telemetry-collector:4317";
    
    private static TelemetryConfig telemetryConfig;
    private final OpenTelemetry openTelemetry;

    private TelemetryConfig() {
        Resource resource = Resource.getDefault()
            .toBuilder()
            .put(AttributeKey.stringKey("service.name"), "trivia-service")
            .put(AttributeKey.stringKey("service.version"), "1.0-SNAPSHOT")
            .build();

        // Tracing setup
        SpanExporter spanExporter = OtlpHttpSpanExporter.builder().setEndpoint(OTLP_TRACES_ENDPOINT).build();
        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
            .setResource(resource)
            .addSpanProcessor(SimpleSpanProcessor.create(spanExporter))
            .build();

        // Metrics setup
        MetricExporter metricExporter = OtlpHttpMetricExporter.builder().setEndpoint(OTLP_METRICS_ENDPOINT).build();
        MetricReader metricReader = PeriodicMetricReader.builder(metricExporter)
            .setInterval(30, TimeUnit.SECONDS)
            .build();
        SdkMeterProvider meterProvider = SdkMeterProvider.builder()
            .setResource(resource)
            .registerMetricReader(metricReader)
            .build();
        
        // Logging setup 
        LogRecordExporter logRecordExporter = OtlpGrpcLogRecordExporter.builder().setEndpoint(OTLP_LOGS_ENDPOINT).build();
        LogRecordProcessor logRecordProcessor = BatchLogRecordProcessor.builder(logRecordExporter).build();
        SdkLoggerProvider sdkLoggerProvider = SdkLoggerProvider.builder()
            .setResource(resource)
            .addLogRecordProcessor(logRecordProcessor).build();

        openTelemetry = OpenTelemetrySdk.builder()
            .setMeterProvider(meterProvider)
            .setTracerProvider(tracerProvider)
            .setLoggerProvider(sdkLoggerProvider)
            .setPropagators(ContextPropagators.create(TextMapPropagator.composite(W3CTraceContextPropagator.getInstance(), W3CBaggagePropagator.getInstance())))
            .buildAndRegisterGlobal();

        //OpenTelemetryAppender in log4j configuration and install
        OpenTelemetryAppender.install(openTelemetry);
    }

    public OpenTelemetry getOpenTelemetry() {
        return openTelemetry;
    }

    public static TelemetryConfig getInstance() {
        if (telemetryConfig == null) {
            telemetryConfig = new TelemetryConfig();
        }

        return telemetryConfig;
    }
}