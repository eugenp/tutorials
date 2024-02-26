package com.baeldung.returnnull;

import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;

public class GraphQLVoidScalar {

    public static final GraphQLScalarType Void = GraphQLScalarType.newScalar()
      .name("Void")
      .description("A custom scalar that represents the null value")
      .coercing(new Coercing() {

          @Override
          public Object serialize(Object dataFetcherResult) {
              return null;
          }

          @Override
          public Object parseValue(Object input) {
              return null;
          }

          @Override
          public Object parseLiteral(Object input) {
              return null;
          }
      })
      .build();

}
