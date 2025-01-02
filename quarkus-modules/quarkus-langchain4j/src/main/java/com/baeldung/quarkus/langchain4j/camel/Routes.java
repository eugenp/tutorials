package com.baeldung.quarkus.langchain4j.camel;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class Routes extends RouteBuilder {

    @Inject
    StructurizingService structurizingService;

    @Override
    public void configure() {
        from("platform-http:/structurize?produces=application/json")
          .log("A document has been received by the camel-quarkus-http extension: ${body}")
          .setHeader("expectedDateFormat", constant("YYYY-MM-DD"))
          .bean(structurizingService)
          .transform()
          .body();
    }
}
