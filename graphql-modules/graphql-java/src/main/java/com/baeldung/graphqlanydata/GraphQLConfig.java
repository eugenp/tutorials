package com.baeldung.graphqlanydata;

import java.io.InputStreamReader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

@Configuration
public class GraphQLConfig {

    @Bean
    public GraphQL graphQL() {
        SchemaParser schemaParser = new SchemaParser();
        SchemaGenerator schemaGenerator = new SchemaGenerator();

        TypeDefinitionRegistry typeRegistry = schemaParser.parse(
            new InputStreamReader(getClass().getResourceAsStream("/schema.graphqls"))
        );

        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
            .type("Mutation", builder ->
                builder.dataFetcher("updateProfile", new MutationResolver().updateProfile())
            )
            .type("AnyDataResponse", typeWiring ->
                typeWiring.typeResolver(env -> {
                    Object javaObject = env.getObject();
                    if (javaObject instanceof SimpleMessage) {
                        return env.getSchema().getObjectType("SimpleMessage");
                    } else if (javaObject instanceof UserProfile) {
                        return env.getSchema().getObjectType("UserProfile");
                    }
                    return null;
                })
            )
            .build();

        GraphQLSchema schema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
        return GraphQL.newGraphQL(schema).build();
    }
}
