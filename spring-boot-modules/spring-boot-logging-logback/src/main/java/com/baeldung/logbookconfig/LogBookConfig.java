package com.baeldung.logbookconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.Conditions;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;

@Configuration
public class LogBookConfig {

    @Bean
    public Logbook logbook() {
        Logbook logbook = Logbook.builder()
            .condition(Conditions.exclude(Conditions.requestTo("/health"), Conditions.requestTo("/admin/**"), Conditions.contentType(
                "pplication/octet-stream"), Conditions.header("X-Secret", "true")))
            .sink(new DefaultSink(new DefaultHttpLogFormatter(), new DefaultHttpLogWriter()))
            .build();
        return logbook;
    }
}
