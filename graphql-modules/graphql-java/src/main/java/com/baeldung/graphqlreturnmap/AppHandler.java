package com.baeldung.graphqlreturnmap;

import com.baeldung.graphql.utils.SchemaUtils;
import com.baeldung.graphqlreturnmap.resolver.ProductResolver;
import com.baeldung.graphqlreturnmap.resolver.Query;
import com.coxautodev.graphql.tools.SchemaParser;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLSchema;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import static ratpack.jackson.Jackson.json;

public class AppHandler implements Handler {
    private static final Logger LOGGER = Logger.getLogger(AppHandler.class.getSimpleName());
    private GraphQL graphql;

    public AppHandler() throws Exception {
        GraphQLSchema schema = SchemaParser.newParser()
            .resolvers(new Query(), new ProductResolver())
            .scalars(ExtendedScalars.Json)
            .file("schema.graphqls")
            .build()
            .makeExecutableSchema();
        graphql = GraphQL.newGraphQL(schema).build();
    }

    @Override
    public void handle(Context context) throws Exception {
        context.parse(Map.class)
            .then(payload -> {
                ExecutionResult executionResult = graphql.execute(payload.get(SchemaUtils.QUERY)
                    .toString(), null, this, Collections.emptyMap());
                Map<String, Object> result = new LinkedHashMap<>();
                if (executionResult.getErrors()
                    .isEmpty()) {
                    result.put(SchemaUtils.DATA, executionResult.getData());
                } else {
                    result.put(SchemaUtils.ERRORS, executionResult.getErrors());
                    LOGGER.warning("Errors: " + executionResult.getErrors());
                }
                context.render(json(result));
            });
    }
}
