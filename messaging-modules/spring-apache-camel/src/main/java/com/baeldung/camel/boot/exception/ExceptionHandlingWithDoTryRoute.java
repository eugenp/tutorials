package com.baeldung.camel.boot.exception;

import java.io.IOException;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandlingWithDoTryRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        from("direct:start-handling-exception")
            .routeId("exception-handling-route")
            .doTry()
                .process(new IllegalArgumentExceptionThrowingProcessor())
                .to("mock:received")
            .doCatch(IOException.class, IllegalArgumentException.class)
                .to("mock:caught")
            .doFinally()
                .to("mock:finally")
            .end();    
                    
    }
}