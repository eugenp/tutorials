package com.baeldung.camel.producertemplate;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("direct:start").log("Received: ${body}")
            .transform(simple("Hello ${body}"));
        from("direct:fileRoute").to("file://output?fileName=output.txt&fileExist=Append");
        from("direct:beanRoute").bean(ProcessingBean.class, "process");

    }

    @Bean
    public ProcessingBean processingBean() {
        return new ProcessingBean();
    }

}
