package com.baeldung.athena.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.baeldung.athena.exception.QueryExecutionFailureException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.model.Datum;
import software.amazon.awssdk.services.athena.model.GetQueryResultsResponse;
import software.amazon.awssdk.services.athena.model.InvalidRequestException;
import software.amazon.awssdk.services.athena.model.QueryExecutionContext;
import software.amazon.awssdk.services.athena.model.QueryExecutionState;
import software.amazon.awssdk.services.athena.model.ResultConfiguration;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryService {
    
    private final AthenaClient athenaClient;
    private final ResultConfiguration resultConfiguration;
    private final QueryExecutionContext queryExecutionContext;

    private static final long WAIT_PERIOD = 30;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JsonOrgModule());

    public <T> List<T> execute(@NonNull final String sqlQuery, @NonNull final Class<T> targetClass) {
        return execute(sqlQuery, null, targetClass);
    }
    
    public <T> List<T> execute(@NonNull final String sqlQuery, @Nullable final List<String> parameters,
            @NonNull final Class<T> targetClass) {
        String queryExecutionId;
        try {
            queryExecutionId = athenaClient.startQueryExecution(query -> 
                query.queryString(sqlQuery)
                  .queryExecutionContext(queryExecutionContext)
                  .resultConfiguration(resultConfiguration)
                  .executionParameters(parameters)
            ).queryExecutionId();
        } catch (final InvalidRequestException exception) {
            log.error("Invalid SQL syntax detected in query {}", sqlQuery, exception);
            throw new QueryExecutionFailureException();
        }

        waitForQueryToComplete(queryExecutionId);

        final var queryResult = athenaClient.getQueryResults(request -> 
            request.queryExecutionId(queryExecutionId));
        return transformQueryResult(queryResult, targetClass);
    }

    @SneakyThrows
    private void waitForQueryToComplete(@NonNull final String queryExecutionId) {
        QueryExecutionState queryState;

        do {
            final var response = athenaClient.getQueryExecution(request -> 
                request.queryExecutionId(queryExecutionId));
            queryState = response.queryExecution().status().state();

            switch (queryState) {
                case FAILED:
                case CANCELLED:
                    final var error = response.queryExecution().status().athenaError().errorMessage();
                    log.error("Query execution failed: {}", error);
                    throw new QueryExecutionFailureException();
                case QUEUED:
                case RUNNING:
                    TimeUnit.MILLISECONDS.sleep(WAIT_PERIOD);
                    break;
                case SUCCEEDED:
                    queryState = QueryExecutionState.SUCCEEDED;
                    return;
                default:
                    throw new IllegalStateException("Invalid query state");
            }
        } while (queryState != QueryExecutionState.SUCCEEDED);
    }

    @SneakyThrows
    private <T> List<T> transformQueryResult(@NonNull final GetQueryResultsResponse queryResultsResponse,
        @NonNull final Class<T> targetClass) {
        final var response = new ArrayList<T>();
        final var rows = queryResultsResponse.resultSet().rows();
        if (rows.isEmpty()) {
            return Collections.emptyList();
        }
        final var headers = rows.get(0).data().stream()
          .map(Datum::varCharValue)
          .toList();

        rows.stream()
            .skip(1)
            .forEach(row -> {
                final var element = new JSONObject();
                final var data = row.data();
               
                for (int i = 0; i < headers.size(); i++) {
                    final var key = headers.get(i);
                    final var value = data.get(i).varCharValue();
                    element.put(key, value);
                }
                final var obj = OBJECT_MAPPER.convertValue(element, targetClass);
                response.add(obj);
            });
        return response;
    }
    
    public record User(Integer id, String name, Integer age, String city) {};
    
}