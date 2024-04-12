package com.baeldung.twitterhdfs.sink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.app.hdfs.sink.HdfsSinkConfiguration;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.ServiceActivator;

@SpringBootApplication
@EnableBinding(Sink.class)
@Import(HdfsSinkConfiguration.class)
public class SinkApp {
    Logger log = LoggerFactory.getLogger(SinkApp.class);

    @ServiceActivator(inputChannel= Sink.INPUT)
    public void loggerSink(Object payload) {
        log.info("Received: " + payload);
    }
}