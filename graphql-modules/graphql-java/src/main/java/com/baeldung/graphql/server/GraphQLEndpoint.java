package com.baeldung.graphql.server;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

import com.baeldung.graphql.data.BookRepository;

import graphql.kickstart.servlet.GraphQLConfiguration;
import graphql.kickstart.servlet.GraphQLHttpServlet;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "HelloServlet", urlPatterns = {"/graphql"})
public class GraphQLEndpoint extends GraphQLHttpServlet {

    @Override
    protected GraphQLConfiguration getConfiguration() {
        return GraphQLConfiguration.with(createSchema()).build();
    }

    private GraphQLSchema createSchema() {
        String schema = "type Query{hello: String}";

        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

        RuntimeWiring runtimeWiring = newRuntimeWiring()
                .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
                .build();

        SchemaGenerator schemaGenerator = new SchemaGenerator();
        /*GraphQLSchema schema = SchemaParser.newParser()
                .resolvers(new GraphQLQuery(new BookRepository()))
                .file("schema.graphqls")
                .build()
                .makeExecutableSchema();*/
        return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
    }
}
