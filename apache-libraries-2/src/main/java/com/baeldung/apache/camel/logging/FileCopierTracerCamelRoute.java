package com.baeldung.apache.camel.logging;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCopierTracerCamelRoute extends RouteBuilder {

    Logger logger = LoggerFactory.getLogger(FileCopierTracerCamelRoute.class);

    public void configure() {
        getContext().setTracing(true);
        from("file:data/json?noop=true").to("log:input?level=INFO")
            .unmarshal()
            .json(JsonLibrary.Jackson)
            .bean(FileProcessor.class, "transform")
            .marshal()
            .json(JsonLibrary.Jackson)
            .to("file:data/output");
    }
}