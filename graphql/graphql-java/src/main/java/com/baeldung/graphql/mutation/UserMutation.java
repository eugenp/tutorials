package com.baeldung.graphql.mutation;

import graphql.annotations.GraphQLField;
import graphql.annotations.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

import javax.validation.constraints.NotNull;

import com.baeldung.graphql.entity.User;
import com.baeldung.graphql.utils.SchemaUtils;

import java.util.List;
import java.util.Optional;

import static com.baeldung.graphql.handler.UserHandler.getUsers;

@GraphQLName(SchemaUtils.MUTATION)
public class UserMutation {
    @GraphQLField
    public static User createUser(final DataFetchingEnvironment env, @NotNull @GraphQLName(SchemaUtils.NAME) final String name, @NotNull @GraphQLName(SchemaUtils.EMAIL) final String email, @NotNull @GraphQLName(SchemaUtils.AGE) final String age) {
        List<User> users = getUsers(env);
        final User user = new User(name, email, Integer.valueOf(age));
        users.add(user);
        return user;
    }

    @GraphQLField
    public static User updateUser(final DataFetchingEnvironment env, @NotNull @GraphQLName(SchemaUtils.ID) final String id, @NotNull @GraphQLName(SchemaUtils.NAME) final String name, @NotNull @GraphQLName(SchemaUtils.EMAIL) final String email,
        @NotNull @GraphQLName(SchemaUtils.AGE) final String age) {
        final Optional<User> user = getUsers(env).stream()
            .filter(c -> c.getId() == Long.parseLong(id))
            .findFirst();
        if (!user.isPresent()) {
            return null;
        }
        user.get()
            .setName(name);
        user.get()
            .setEmail(email);
        user.get()
            .setAge(Integer.valueOf(age));
        return user.get();
    }

    @GraphQLField
    public static User deleteUser(final DataFetchingEnvironment env, @NotNull @GraphQLName(SchemaUtils.ID) final String id) {
        final List<User> users = getUsers(env);
        final Optional<User> user = users.stream()
            .filter(c -> c.getId() == Long.parseLong(id))
            .findFirst();
        if (!user.isPresent()) {
            return null;
        }
        users.removeIf(c -> c.getId() == Long.parseLong(id));
        return user.get();
    }
}
