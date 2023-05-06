package com.baeldung.opentelemetry.configuration;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.MeterProvider;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.OpenTelemetrySdkBuilder;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author : Nam Thang
 * @since : 04/05/2023, Thu
 **/
@Configuration
public class OpenTelemetryConfiguration {

//    @Bean
//    public OpenTelemetrySdk getOpenTelemetry (){
//        Resource serviceNameResource =
//                Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, "price-service"));
//        OpenTelemetrySdkBuilder openTelemetrySdkBuilder = OpenTelemetrySdk.builder()
//                .setTracerProvider(
//                        SdkTracerProvider.builder()
//                                .setResource(Resource.getDefault().merge(serviceNameResource))
//                                .build())
//                .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()));
//        return openTelemetrySdkBuilder.buildAndRegisterGlobal();
//    }
//
//    @Bean
//    public Tracer getTracer(){
//        return getOpenTelemetry().getTracer("test");
//    }
}
