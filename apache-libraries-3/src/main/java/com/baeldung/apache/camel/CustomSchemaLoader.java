package com.baeldung.apache.camel;

import java.io.InputStream;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

public class CustomSchemaLoader {
    private static final Logger logger = LoggerFactory.getLogger(CustomSchemaLoader.class);
    private final BookService bookService = new BookService();

    public GraphQLSchema loadSchema() {
        try (InputStream schemaStream = getClass().getClassLoader().getResourceAsStream("books.graphql")) {
            if (schemaStream == null) {
                throw new RuntimeException("GraphQL schema file 'books.graphql' not found in classpath");
            }

            TypeDefinitionRegistry registry = new SchemaParser()
                .parse(new InputStreamReader(schemaStream));

            RuntimeWiring wiring = buildRuntimeWiring();
            return new SchemaGenerator().makeExecutableSchema(registry, wiring);

        } catch (Exception e) {
            logger.error("Failed to load GraphQL schema", e);
            throw new RuntimeException("GraphQL schema initialization failed", e);
        }
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
            .type("Query", builder -> builder
                .dataFetcher("books", env -> bookService.getBooks())
                .dataFetcher("bookById", env -> bookService.getBookById(env.getArgument("id"))))
            .type("Mutation", builder -> builder
                .dataFetcher("addBook", env -> {
                    String id = env.getArgument("id");
                    String title = env.getArgument("title");
                    String author = env.getArgument("author");
                    if (title == null || title.isEmpty()) {
                        throw new IllegalArgumentException("Title cannot be empty");
                    }
                    return bookService.addBook(new Book(id, title, author));
                }))
            .build();
    }
}
