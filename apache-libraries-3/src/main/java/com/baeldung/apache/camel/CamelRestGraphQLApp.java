package com.baeldung.apache.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;

public class CamelRestGraphQLApp {
    private static final Logger logger = LoggerFactory.getLogger(CamelRestGraphQLApp.class);
    public static void main(String[] args) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new BookRoute());
        context.start();
        logger.info("Server running at http://localhost:8088");
        Thread.sleep(Long.MAX_VALUE);
        context.stop();
    }
}
