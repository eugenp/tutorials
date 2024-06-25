package com.baeldung.athena.service;

import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.baeldung.athena.exception.QueryExecutionFailureException;

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

    public JSONArray execute(@NonNull final String sqlQuery) {
        String queryExecutionId;
        try {
            queryExecutionId = athenaClient.startQueryExecution(query -> 
                query.queryString(sqlQuery)
                  .queryExecutionContext(queryExecutionContext)
                  .resultConfiguration(resultConfiguration)
            ).queryExecutionId();
        } catch (final InvalidRequestException exception) {
            log.error("Invalid SQL syntax detected in query {}", sqlQuery, exception);
            throw new QueryExecutionFailureException();
        }

        waitForQueryToComplete(queryExecutionId);

        final var queryResult = athenaClient.getQueryResults(request -> 
            request.queryExecutionId(queryExecutionId));
        return processQueryResult(queryResult);
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
    private JSONArray processQueryResult(@NonNull final GetQueryResultsResponse queryResultsResponse) {
        final var response = new JSONArray();
        final var rows = queryResultsResponse.resultSet().rows();
        if (rows.isEmpty()) {
            return response;
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
                response.put(element);
            });
        return response;
    }
    
}