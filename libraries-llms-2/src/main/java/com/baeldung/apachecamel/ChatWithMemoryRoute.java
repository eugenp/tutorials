package com.baeldung.apachecamel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.openai.OpenAIConstants;

public class ChatWithMemoryRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(Exception.class).handled(true)
            .log(org.apache.camel.LoggingLevel.ERROR, "OpenAI Error: ${exception.message}")
            .setBody(constant("OpenAI request failed."));

        from("direct:startChatWithMemory").setHeader(OpenAIConstants.SYSTEM_MESSAGE, constant("You are a helpful, brief IT support assistant."))
            .setHeader(OpenAIConstants.MODEL, constant("gpt-4o-mini"))
            .setHeader(OpenAIConstants.TEMPERATURE, constant(0.1))
            .to("openai:chat-completion?conversationMemory=true")
            .log("Current Turn Response: ${body}");
    }
}
