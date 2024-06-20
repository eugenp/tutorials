package com.baeldung.athena.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baeldung.athena.exception.QueryExecutionFailureException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.model.Datum;
import software.amazon.awssdk.services.athena.model.GetQueryResultsResponse;
import software.amazon.awssdk.services.athena.model.QueryExecutionContext;
import software.amazon.awssdk.services.athena.model.QueryExecutionState;
import software.amazon.awssdk.services.athena.model.ResultConfiguration;

@Service
@RequiredArgsConstructor
public class QueryService {
    
    private final AthenaClient athenaClient;
    private final ResultConfiguration resultConfiguration;
    private final QueryExecutionContext queryExecutionContext;

    private static final long WAIT_PERIOD = 30;

    public List<Map<String, String>> execute(@NonNull final String sqlQuery) {
        final var queryExecutionId = athenaClient.startQueryExecution(query -> 
            query.queryString(sqlQuery)
                .queryExecutionContext(queryExecutionContext)
                .resultConfiguration(resultConfiguration)
        ).queryExecutionId();

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
    private List<Map<String, String>> processQueryResult(@NonNull final GetQueryResultsResponse queryResultsResponse) {
        final var rows = queryResultsResponse.resultSet().rows();
        if (rows.isEmpty()) {
            return Collections.emptyList();
        }
        final var headers = rows.get(0).data().stream()
            .map(Datum::varCharValue)
            .toList();

        return rows.stream()
            .skip(1)
            .map(row -> {
                final var data = row.data();
                final var rowMap = new HashMap<String, String>();

                for (int i = 0; i < headers.size(); i++) {
                    rowMap.put(headers.get(i), data.get(i).varCharValue());
                }
                return rowMap;
            })
            .collect(Collectors.toList());
    }
    
}