package com.baeldung.logbookconfig;

import java.util.Arrays;

import org.slf4j.event.Level;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.HttpLogFormatter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.ChunkingSink;
import org.zalando.logbook.core.CommonsLogFormatSink;
import org.zalando.logbook.core.CompositeSink;
import org.zalando.logbook.core.Conditions;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.core.ExtendedLogFormatSink;
import org.zalando.logbook.json.JsonHttpLogFormatter;
import org.zalando.logbook.logstash.LogstashLogbackSink;

@Configuration
public class LogBookConfig {

    @Bean
    public Logbook logbook() {
        Logbook logbook = Logbook.builder()
            .condition(Conditions.exclude(Conditions.requestTo("/api/welcome"), Conditions.contentType(
                "application/octet-stream"), Conditions.header("X-Secret", "true")))
            .sink(new DefaultSink(new DefaultHttpLogFormatter(), new DefaultHttpLogWriter()))
            .build();
        return logbook;
    }

    //@Bean
    public Logbook chunkSink() {
        Logbook logbook = Logbook.builder()
            .sink(new ChunkingSink(new CommonsLogFormatSink(new DefaultHttpLogWriter()), 1000))
            .build();
        return logbook;
    }

    //@Bean
    public Logbook compositesink() {
        CompositeSink comsink = new CompositeSink(Arrays.asList(new CommonsLogFormatSink(new DefaultHttpLogWriter()), new ExtendedLogFormatSink(
            new DefaultHttpLogWriter())));
        Logbook logbook = Logbook.builder()
            .sink(comsink)
            .build();
        return logbook;
    }

    // to run logstash example set spring.profiles.active=logbooklogstash in application.properties and enable following bean disabling all others
    //@Bean
    public Logbook logstashsink() {
        HttpLogFormatter formatter = new JsonHttpLogFormatter();
        LogstashLogbackSink logstashsink = new LogstashLogbackSink(formatter, Level.INFO);

        Logbook logbook = Logbook.builder()
            .sink(logstashsink)
            .build();
        return logbook;
    }

    //@Bean
    public Logbook commonLogFormat() {
        Logbook logbook = Logbook.builder()
            .sink(new CommonsLogFormatSink(new DefaultHttpLogWriter()))
            .build();
        return logbook;
    }

    //@Bean
    public Logbook extendedLogFormat() {
        Logbook logbook = Logbook.builder()
            .sink(new ExtendedLogFormatSink(new DefaultHttpLogWriter()))
            .build();
        return logbook;
    }
}
