package com.baeldung.graphqlreturnmap;

import graphql.language.ScalarTypeDefinition;
import graphql.schema.Coercing;
import graphql.schema.GraphQLDirective;
import graphql.schema.GraphQLScalarType;

import java.util.List;

public class ExtendedGraphQLScalarType extends GraphQLScalarType {

    public ExtendedGraphQLScalarType(){
        super("","",null);

    }

    public ExtendedGraphQLScalarType(String name, String description, Coercing coercing) {
        super(name, description, coercing);
    }

    public ExtendedGraphQLScalarType(String name, String description, Coercing coercing, List<GraphQLDirective> directives, ScalarTypeDefinition definition) {
        super(name, description, coercing, directives, definition);
    }
}
