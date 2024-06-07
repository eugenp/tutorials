package com.baeldung.logbookconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.CompositeSink;
import org.zalando.logbook.core.Conditions;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.json.JsonHttpLogFormatter;
import org.zalando.logbook.logstash.LogstashLogbackSink;

@Configuration
public class LogBookConfig {

    @Bean
    public Logbook logbook() {
        Logbook logbook = Logbook.builder()
            .condition(Conditions.exclude(Conditions.requestTo("/api/welcome"), Conditions.contentType(
                "Application/octet-stream"), Conditions.header("X-Secret", "true")))
            .sink(new DefaultSink(new DefaultHttpLogFormatter(), new DefaultHttpLogWriter()))
            .build();
        return logbook;
    }

    public Logbook compositesink() {
        CompositeSink comsink = new CompositeSink(null);
        Logbook logbook = Logbook.builder()
            .sink(comsink)
            .build();
        return logbook;
    }

    public Logbook logstashsink() {
        HttpLogFormatter formatter = new JsonHttpLogFormatter();

        LogstashLogbackSink logstashsink = new LogstashLogbackSink(formatter);
        //LogstashLogbackSink logstashsink = new LogstashLogbackSink(formatter, Level.INFO);

        Logbook logbook = Logbook.builder()
            .sink(logstashsink)
            .build();
        return logbook;
    }
}
