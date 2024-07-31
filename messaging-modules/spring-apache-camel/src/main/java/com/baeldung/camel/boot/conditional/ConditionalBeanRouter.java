package com.baeldung.camel.boot.conditional;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ConditionalBeanRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        from("direct:start-conditional-bean")
            .routeId("conditional-bean-route")
            .choice()
                .when(method(FruitBean.class, "isApple"))
                    .setHeader("favourite", simple("Apples"))
                    .to("mock:result")
                .otherwise()
                    .setHeader("favourite", header("fruit"))
                    .to("mock:result")
            .end();        
    }
}
