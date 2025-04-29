package com.baeldung.apache.camel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.stream.InputStreamCache;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.RestConfiguration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;

public class BookRoute extends RouteBuilder {

    private final BookService bookService = new BookService();

    @Override
    public void configure() throws Exception {
        JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Map.class);
        jsonDataFormat.setUnmarshalType(Object.class);

        onException(Exception.class).handled(true)
            .setHeader("Content-Type", constant("application/json"))
            .setBody(simple("{\"error\": \"${exception.message}\"}"));

        onException(IllegalArgumentException.class)
            .handled(true)
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
            .setBody(simple("{\"error\": \"${exception.message}\"}"));

        onException(JsonParseException.class)
            .handled(true)
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
            .setBody(simple("{\"error\": \"Invalid JSON: ${exception.message}\"}"));

        restConfiguration().component("jetty")
            .host("localhost")
            .port(8088)
            .contextPath("/api")
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true");

        rest("/books")
            .get().to("direct:getAllBooks")
            .get("/{id}").to("direct:getBookById")
            .post()
                .consumes("application/json")
                .type(Book.class)
                .to("direct:addBook");

        from("direct:getAllBooks").bean(bookService, "getBooks");
        from("direct:getBookById").bean(bookService, "getBookById(${header.id})");
        from("direct:addBook").bean(bookService, "addBook");

        GraphQLSchema schema = new CustomSchemaLoader().loadSchema();
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();

        from("jetty:http://localhost:8088/graphql?matchOnUriPrefix=true")
            .log("Received GraphQL request: ${body}")
            .convertBodyTo(String.class)
            .process(exchange -> {
                String body = exchange.getIn().getBody(String.class);
                try {
                    Map<String, Object> payload = new ObjectMapper().readValue(body, Map.class);
                    String query = (String) payload.get("query");
                    if (query == null || query.trim().isEmpty()) {
                        throw new IllegalArgumentException("Missing 'query' field in request body");
                    }
                    ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                        .query(query)
                        .build();
                    ExecutionResult result = graphQL.execute(executionInput);
                    Map<String, Object> response = result.toSpecification();
                    exchange.getIn().setBody(response);
                } catch (Exception e) {
                    throw new RuntimeException("GraphQL processing error", e);
                }
            })
            .marshal().json(JsonLibrary.Jackson)
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
    }
}
