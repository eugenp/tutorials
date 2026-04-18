package com.baeldung.camel.observablity;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class SimpleRouteBuilder extends RouteBuilder {

    private final SimpleProcessor simpleProcessor;

    @Autowired
    public SimpleRouteBuilder(SimpleProcessor simpleProcessor) {
        this.simpleProcessor = simpleProcessor;
    }

    public void configure() {
        from("file://src/data?noop=true")
                .process(simpleProcessor)
                .choice()
                .when(xpath("/person/city = 'London'"))
                .log("UK message")
                .to("file:target/messages/uk")
                .log("UK message 2")
                .to("file:target/messages/general-sink")
                .otherwise()
                .log("Other message")
                .to("file:target/messages/others")
                .log("Other message 2")
                .to("file:target/messages/general-sink");

    }

}
