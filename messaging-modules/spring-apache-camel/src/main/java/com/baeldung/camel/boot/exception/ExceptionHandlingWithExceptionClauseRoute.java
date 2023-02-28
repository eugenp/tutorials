package com.baeldung.camel.boot.exception;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandlingWithExceptionClauseRoute extends RouteBuilder {
    
    @Autowired
    private ExceptionLoggingProcessor exceptionLogger;
    
    @Override
    public void configure() throws Exception {
        onException(IllegalArgumentException.class).process(exceptionLogger)
            .handled(true)
            .to("mock:handled");
        
        from("direct:start-exception-clause")
            .routeId("exception-clause-route")
            .process(new IllegalArgumentExceptionThrowingProcessor())
            .to("mock:received");
    }
}
