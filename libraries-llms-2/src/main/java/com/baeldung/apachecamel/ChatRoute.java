package com.baeldung.apachecamel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.openai.OpenAIConstants;

public class ChatRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(Exception.class).handled(true)
            .log(org.apache.camel.LoggingLevel.INFO, "OpenAI Error: ${exception.message}")
            .setBody(simple("OpenAI request failed."));

        from("direct:startChat").setHeader(OpenAIConstants.SYSTEM_MESSAGE, constant("You are a helpful, brief IT support assistant."))
            .setHeader(OpenAIConstants.TEMPERATURE, constant(0.1))
            .setHeader(OpenAIConstants.MODEL, constant("gpt-4o-mini"))
            .to("openai:chat-completion")
            .log("AI Response: ${body}");
    }
}
