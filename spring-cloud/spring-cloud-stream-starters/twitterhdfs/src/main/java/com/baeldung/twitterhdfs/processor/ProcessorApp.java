package com.baeldung.twitterhdfs.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;

@SpringBootApplication
@EnableBinding(Processor.class)
public class ProcessorApp {
    Logger log = LoggerFactory.getLogger(ProcessorApp.class);

    @Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public String processMessage(String payload) {
        log.info("Payload received!");
        return payload;
    }
}