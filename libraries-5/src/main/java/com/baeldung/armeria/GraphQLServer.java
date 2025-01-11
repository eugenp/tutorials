package com.baeldung.armeria;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.graphql.GraphqlService;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import graphql.GraphQL;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.util.concurrent.CompletableFuture;

import static graphql.Scalars.GraphQLString;

public class GraphQLServer {
    public static void main(String[] args) {
        ServerBuilder sb = Server.builder();
        sb.http(8080);

        sb.tlsSelfSigned();
        sb.https(8443);

        sb.accessLogWriter(AccessLogWriter.common(), true);

        sb.service("/graphql", GraphqlService.builder().graphql(buildSchema()).build());

        Server server = sb.build();
        CompletableFuture<Void> future = server.start();
        future.join();
    }

    private static GraphQL buildSchema() {
        GraphQLSchema schema = GraphQLSchema.newSchema()
            .query(GraphQLObjectType.newObject()
                .name("query")
                .field(GraphQLFieldDefinition.newFieldDefinition()
                    .name("name")
                    .type(GraphQLString)
                    .staticValue("Armeria")
                    .build())
                .build())
            .build();

        return GraphQL.newGraphQL(schema).build();
    }
}
