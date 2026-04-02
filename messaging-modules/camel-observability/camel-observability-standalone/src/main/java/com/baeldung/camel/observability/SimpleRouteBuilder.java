package com.baeldung.camel.observability;

import org.apache.camel.BeanInject;
import org.apache.camel.builder.RouteBuilder;

public class SimpleRouteBuilder extends RouteBuilder {

    @BeanInject("SimpleProcessor")
    SimpleProcessor simpleProcessor;

    public void configure() {
        from("file:src/data?noop=true")
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
