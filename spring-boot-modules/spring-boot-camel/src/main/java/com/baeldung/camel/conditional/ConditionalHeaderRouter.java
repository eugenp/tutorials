package com.baeldung.camel.conditional;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ConditionalHeaderRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        from("direct:start-conditional-header")
            .routeId("conditional-header-route")
            .choice()
                .when(header("fruit").isEqualTo("Apple"))
                    .setHeader("favourite", simple("Apples"))
                    .to("mock:result")
                .otherwise()
                    .setHeader("favourite", header("fruit"))
                    .to("mock:result")
            .end();        
    }
}
