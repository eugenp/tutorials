package com.baeldung.apachecamellogging;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCopierCamelRoute extends RouteBuilder {

    private static Logger logger = LoggerFactory.getLogger(FileCopierCamelRoute.class);

    public void configure() {
        from("file:data/inbox?noop=true").log("We got an incoming file ${file:name} containing: ${body}")
            .to("log:com.baeldung.apachecamellogging?level=INFO")
            .process(new Processor() {
                @Override
                public void process(Exchange exchange) throws Exception {
                    logger.info("We are passing the message to a FileProcesor to Capitalize the message body");
                }
            })
            .bean(FileProcessor.class)
            .to("file:data/outbox")
            .log("Successlly transfer file: ${file:name}");
    }
}