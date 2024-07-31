package com.baeldung.camel.boot.conditional;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ConditionalBodyRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        from("direct:start-conditional")
            .routeId("conditional-body-route")
            .choice()
                .when(body().contains("Baeldung"))
                    .setBody(simple("Goodbye, Baeldung!"))
                    .to("mock:result-body")
                .otherwise()
                    .to("mock:result-body")
            .end();        

    }

}
