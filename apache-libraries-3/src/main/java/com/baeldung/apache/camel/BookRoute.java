package com.baeldung.apache.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.RestConfiguration;

public class BookRoute extends RouteBuilder {

    private final BookService bookService = new BookService();

    @Override
    public void configure() throws Exception {
        onException(Exception.class).handled(true)
            .setHeader("Content-Type", constant("application/json"))
            .setBody(simple("{\"error\": \"${exception.message}\"}"));

        restConfiguration().component("jetty")
            .host("localhost")
            .port(8080)
            .contextPath("/api")
            .bindingMode(RestBindingMode.json);

        rest("/books").get()
            .to("direct:getAllBooks")
            .get("/{id}")
            .to("direct:getBookById")
            .post()
            .type(Book.class)
            .to("direct:addBook");

        from("direct:getAllBooks").bean(bookService, "getBooks");
        from("direct:getBookById").bean(bookService, "getBookById(${header.id})");
        from("direct:addBook").bean(bookService, "addBook");

        from("jetty:http://0.0.0.0:8080/graphql?matchOnUriPrefix=true")
            .routeId("graphql-route")
            .to("graphql:classpath:books.graphql?schemaLoader=#graphqlSchemaLoader");
    }
}
