package com.baeldung.apachecamel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.openai.OpenAIConstants;
import org.apache.camel.model.dataformat.JsonLibrary;

public class BookInfoRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        onException(Exception.class).handled(true)
            .log(org.apache.camel.LoggingLevel.INFO, "OpenAI Error: ${exception.message}")
            .setBody(simple("OpenAI request failed."));

        from("direct:startBookChat").setHeader(OpenAIConstants.SYSTEM_MESSAGE, constant("You are a helpful, brief book advisor."))
            .setHeader(OpenAIConstants.MODEL, constant("gpt-4o-mini"))
            .setHeader(OpenAIConstants.TEMPERATURE, constant(0.1))
            .to("openai:chat-completion?outputClass=com.baeldung.apachecamel.BookInfo")
            .unmarshal()
            .json(JsonLibrary.Jackson, BookInfo.class)
            .log("AI Response: ${body}");
    }
}
