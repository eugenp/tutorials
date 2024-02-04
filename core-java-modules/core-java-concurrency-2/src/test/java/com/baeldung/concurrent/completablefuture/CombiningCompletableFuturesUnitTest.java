package com.baeldung.concurrent.completablefuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CombiningCompletableFuturesUnitTest {

    @Test
    public void givenMicroserviceClient_whenCreateResource_thenReturnSuccess() throws ExecutionException, InterruptedException {
        MicroserviceClient mockMicroserviceA = mock(MicroserviceClient.class);
        when(mockMicroserviceA.createResource(any())).thenReturn(CompletableFuture.completedFuture(123L));
        CompletableFuture<Long> resultFuture = mockMicroserviceA.createResource("My Resource");
        assertEquals(123L, resultFuture.get());
    }

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
    public void givenMicroserviceClient_whenMultipleCreateResource_thenCombineResults(List<String> inputs, int expectedSuccess, int expectedFailure) throws ExecutionException, InterruptedException {
        MicroserviceClient mockMicroservice = mock(MicroserviceClient.class);
        when(mockMicroservice.createResource("Good Resource")).thenReturn(CompletableFuture.completedFuture(123L));
        when(mockMicroservice.createResource("Bad Resource")).thenReturn(CompletableFuture.failedFuture(new IllegalArgumentException("Bad Resource")));

        List<CompletableFuture<Long>> clientCalls = new ArrayList<>();
        for (String resource : inputs) {
            clientCalls.add(mockMicroservice.createResource(resource));
        }
        CompletableFuture<?>[] clientCallsAsArray = clientCalls.toArray(new CompletableFuture[inputs.size()]);
        CompletableFuture.allOf(clientCallsAsArray)
            .exceptionally(ex -> null)
            .join();
        Map<Boolean, List<CompletableFuture<Long>>> resultsByFailure = clientCalls.stream().collect(Collectors.partitioningBy(CompletableFuture::isCompletedExceptionally));
        assertThat(resultsByFailure.getOrDefault(false, Collections.emptyList()).size()).isEqualTo(expectedSuccess);
        assertThat(resultsByFailure.getOrDefault(true, Collections.emptyList()).size()).isEqualTo(expectedFailure);
    }

    interface MicroserviceClient {
        CompletableFuture<Long> createResource(String resourceName);
    }
}
