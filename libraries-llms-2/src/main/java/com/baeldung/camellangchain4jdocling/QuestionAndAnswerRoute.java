package com.baeldung.camellangchain4jdocling;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

public class QuestionAndAnswerRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("undertow:http://0.0.0.0:8080/api/ask?httpMethodRestrict=POST").routeId("document-qa-api")
            .setProperty("question", bodyAs(String.class))
            .pollEnrich("file:output?fileName=policies.md&noop=true")
            .setProperty("markdown", bodyAs(String.class))
            .setBody(simple("""
                You are a helpful document assistant.
                Answer ONLY using the document below.
                DOCUMENT
                =========
                ${exchangeProperty.markdown}
                QUESTION
                =========
                ${exchangeProperty.question}
                """))
            .to("langchain4j-chat:analysis?chatModel=#chatModel")
            .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"));
    }
}
