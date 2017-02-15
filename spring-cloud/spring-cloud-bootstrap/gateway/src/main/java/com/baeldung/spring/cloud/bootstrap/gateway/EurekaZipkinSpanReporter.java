package com.baeldung.spring.cloud.bootstrap.gateway;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.metric.SpanMetricReporter;
import org.springframework.cloud.sleuth.zipkin.HttpZipkinSpanReporter;
import org.springframework.cloud.sleuth.zipkin.ZipkinProperties;
import org.springframework.cloud.sleuth.zipkin.ZipkinSpanReporter;
import org.springframework.context.annotation.Configuration;
import zipkin.Span;

@Configuration
public class EurekaZipkinSpanReporter implements ZipkinSpanReporter {

    @Autowired
    private EurekaClient eurekaClient;
    @Autowired
    private SpanMetricReporter spanMetricReporter;
    @Autowired
    private ZipkinProperties zipkinProperties;
    @Value("${spring.sleuth.web.skipPattern}")
    private String skipPattern;

    @Override
    public void report(Span span) {
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("zipkin", false);
        HttpZipkinSpanReporter reporter = new HttpZipkinSpanReporter(instance.getHomePageUrl(), zipkinProperties.getFlushInterval(), zipkinProperties.getCompression().isEnabled(), spanMetricReporter);
        if (!span.name.matches(skipPattern)) reporter.report(span);
    }
}
