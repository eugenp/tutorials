package com.baeldung.graphqlreturnmap;

import graphql.schema.GraphQLScalarType;

public class ExtendedGraphQLScalarType extends GraphQLScalarType {

    public ExtendedGraphQLScalarType(){
        super("","",null);

    }
}
