package com.baeldung.graphql.error.handling.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof VehicleNotFoundException) {
            return GraphqlErrorBuilder.newError()
              .errorType(ErrorType.NOT_FOUND)
              .message(ex.getMessage())
              .path(env.getExecutionStepInfo().getPath())
              .location(env.getField().getSourceLocation())
              .build();
        } else if (ex instanceof AbstractGraphQLException) {
            return GraphqlErrorBuilder.newError()
              .errorType(ErrorType.INTERNAL_ERROR)
              .message(ex.getMessage())
              .path(env.getExecutionStepInfo().getPath())
              .location(env.getField().getSourceLocation())
              .extensions(((AbstractGraphQLException) ex).getExtensions())
              .build();
        } else {
            return null;
        }
    }
}
