package com.baeldung.concurrent.completablefuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CombiningCompletableFuturesUnitTest {

    private final Logger logger = mock(Logger.class);

    private static Stream<Arguments> clientData() {
        return Stream.of(
            Arguments.of(List.of("Good Resource"), 1, 0),
            Arguments.of(List.of("Bad Resource"), 0, 1),
            Arguments.of(List.of("Good Resource", "Bad Resource"), 1, 1),
            Arguments.of(List.of("Good Resource", "Bad Resource", "Good Resource", "Bad Resource", "Good Resource"), 3, 2)
        );
    }

    @ParameterizedTest
    @MethodSource("clientData")
    public void givenMicroserviceClient_whenMultipleCreateResource_thenCombineResults(List<String> inputs, int successCount, int errorCount) {
        MicroserviceClient mockMicroservice = mock(MicroserviceClient.class);
        // Return an identifier of 123 on "Good Resource"
        when(mockMicroservice.createResource("Good Resource"))
            .thenReturn(CompletableFuture.completedFuture(123L));
        // Throw an exception on "Bad Resource"
        when(mockMicroservice.createResource("Bad Resource"))
            .thenReturn(CompletableFuture.failedFuture(new IllegalArgumentException("Bad Resource")));

        // Given a list of CompletableFutures from our microservice calls...
        List<CompletableFuture<Long>> clientCalls = new ArrayList<>();
        for (String resource : inputs) {
            clientCalls.add(mockMicroservice.createResource(resource));
        }

        // When all CompletableFutures are completed (exceptionally or otherwise)...
        Map<Boolean, List<Long>> resultsByValidity = clientCalls.stream()
            .map(this::handleFuture)
            .collect(Collectors.partitioningBy(resourceId -> resourceId != -1L));

        // Then the returned resource identifiers should match what is expected...
        List<Long> validResults = resultsByValidity.getOrDefault(true, List.of());
        assertThat(validResults.size()).isEqualTo(successCount);

        // And the logger mock should be called once for each exception with the expected error message
        List<Long> invalidResults = resultsByValidity.getOrDefault(false, List.of());
        assertThat(invalidResults.size()).isEqualTo(errorCount);
        verify(logger, times(errorCount))
            .error(eq("Encountered error: java.lang.IllegalArgumentException: Bad Resource"));
    }

    /**
     * Completes the given CompletableFuture, handling any exceptions that are thrown.
     * @param future the CompletableFuture to complete.
     * @return the resource identifier (-1 if the request failed).
     */
    private Long handleFuture(CompletableFuture<Long> future) {
        return future
            .exceptionally(this::handleError)
            .join();
    }

    private Long handleError(Throwable throwable) {
        logger.error("Encountered error: " + throwable);
        return -1L;
    }

    interface MicroserviceClient {
        CompletableFuture<Long> createResource(String resourceName);
    }

    interface Logger {
        void error(String message);
    }
}
