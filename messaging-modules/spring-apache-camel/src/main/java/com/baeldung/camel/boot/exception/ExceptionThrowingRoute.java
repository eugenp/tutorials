package com.baeldung.camel.boot.exception;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExceptionThrowingRoute extends RouteBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionThrowingRoute.class);

    @Override
    public void configure() throws Exception {
        
        from("direct:start-exception")
            .routeId("exception-throwing-route")
            .process(new Processor() {
                
                @Override
                public void process(Exchange exchange) throws Exception {
                    LOGGER.error("Exception Thrown");
                    throw new IllegalArgumentException("An exception happened on purpose");
                    
                }
            }).to("mock:received");
    }
}