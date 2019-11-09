package com.baeldung.twitterhdfs.aggregate;

import com.baeldung.twitterhdfs.processor.ProcessorApp;
import com.baeldung.twitterhdfs.source.SourceApp;
import com.baeldung.twitterhdfs.sink.SinkApp;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.aggregate.AggregateApplicationBuilder;

@SpringBootApplication
public class AggregateApp {
    public static void main(String[] args) {
        new AggregateApplicationBuilder()
                .from(SourceApp.class).args("--fixedDelay=5000")
                .via(ProcessorApp.class)
                .to(SinkApp.class).args("--debug=true")
                .run(args);
    }
}