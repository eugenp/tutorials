package com.baeldung.apache.camel.logging;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCopierCamelRoute extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileCopierCamelRoute.class);

    public void configure() {
        from("file:data/inbox?noop=true").log("We got an incoming file ${file:name} containing: ${body}")
            .to("log:com.baeldung.apachecamellogging?level=INFO")
            .process(process -> {
                LOGGER.info("We are passing the message to a FileProcesor bean to capitalize the message body");
            })
            .bean(FileProcessor.class)
            .to("file:data/outbox")
            .to("log:com.baeldung.apachecamellogging?showBodyType=false&maxChars=20")
            .log(LoggingLevel.DEBUG, "Output Process", "The Process ${id}")
            .log("Successfully transfer file: ${file:name}");
    }
}