package com.baeldung.graphqlanydata;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class MutationResolver {

    public DataFetcher<Object> updateProfile() {
        return environment -> {
            String name = environment.getArgument("name");
            String type = environment.getArgument("type");

            if ("message".equalsIgnoreCase(type)) {
                return new SimpleMessage("Profile updated for " + name);
            } else {
                return new UserProfile("u123", name, "ACTIVE");
            }
        };
    }
}
