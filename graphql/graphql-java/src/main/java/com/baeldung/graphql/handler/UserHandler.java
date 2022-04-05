package com.baeldung.graphql.handler;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetchingEnvironment;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.baeldung.graphql.entity.User;
import com.baeldung.graphql.schema.UserSchema;
import com.baeldung.graphql.utils.SchemaUtils;

import static ratpack.jackson.Jackson.json;

public class UserHandler implements Handler {
    private static final Logger LOGGER = Logger.getLogger(UserHandler.class.getSimpleName());
    private GraphQL graphql;
    private static List<User> users = new ArrayList<>();

    public UserHandler() throws Exception {
        graphql = new GraphQL(new UserSchema().getSchema());
    }

    @Override
    public void handle(Context context) throws Exception {
        context.parse(Map.class)
            .then(payload -> {
                Map<String, Object> parameters = (Map<String, Object>) payload.get("parameters");
                ExecutionResult executionResult = graphql.execute(payload.get(SchemaUtils.QUERY)
                    .toString(), null, this, parameters);
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

    public static List<User> getUsers() {
        return users;
    }

    public static List<User> getUsers(DataFetchingEnvironment env) {
        return ((UserHandler) env.getSource()).getUsers();
    }
}
