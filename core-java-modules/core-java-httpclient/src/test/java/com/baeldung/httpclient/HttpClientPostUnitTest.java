package com.baeldung.httpclient;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class HttpClientPostUnitTest extends PostRequestMockServer {

    @Test
    void givenSyncPostRequest_whenServerIsAvailable_thenOkStatusIsReceived() throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClientPost.sendSynchronousPost(serviceUrl);
        assertAll(
          () -> assertThat(response.statusCode()).isEqualTo(200),
          () -> assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}")
        );
    }

    @Test
    void givenAsyncPostRequest_whenServerIsAvailable_thenOkStatusIsReceived() {
        CompletableFuture<HttpResponse<String>> futureResponse = HttpClientPost.sendAsynchronousPost(serviceUrl);
        futureResponse.thenAccept(
          response -> assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(200),
                () -> assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}")
              )
        );
    }

    @Test
    void givenConcurrentPostRequests_whenServerIsAvailable_thenOkStatusIsReceived() throws ExecutionException, InterruptedException {
        List<CompletableFuture<HttpResponse<String>>> completableFutures = HttpClientPost
          .sendConcurrentPost(List.of(serviceUrl, serviceUrl));

        CompletableFuture<List<HttpResponse<String>>> combinedFutures = CompletableFuture
          .allOf(completableFutures.toArray(new CompletableFuture[0]))
          .thenApply(future ->
            completableFutures.stream()
              .map(CompletableFuture::join)
              .collect(Collectors.toList()));

        List<HttpResponse<String>> responses = combinedFutures.get();
        responses.forEach((response) -> {
            assertAll(
              () -> assertThat(response.statusCode()).isEqualTo(200),
              () -> assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}")
            );
        });
    }

    @Test
    void givenPostRequestWithAuthClient_whenServerIsAvailable_thenOkStatusIsReceived() throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClientPost.sendPostWithAuthClient(serviceUrl);
        assertAll(
          () -> assertThat(response.statusCode()).isEqualTo(200),
          () -> assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}")
        );
    }

    @Test
    void givenPostRequestWithAuthHeader_whenServerIsAvailable_thenOkStatusIsReceived() throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClientPost.sendPostWithAuthHeader(serviceUrl);
        assertAll(
          () -> assertThat(response.statusCode()).isEqualTo(200),
          () -> assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}")
        );
    }

    @Test
    void givenPostRequestWithJsonBody_whenServerIsAvailable_thenOkStatusIsReceived() throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClientPost.sendPostWithJsonBody(serviceUrl);
        assertAll(
          () -> assertThat(response.statusCode()).isEqualTo(200),
          () -> assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}")
        );
    }

    @Test
    void givenPostRequestWithFormData_whenServerIsAvailable_thenOkStatusIsReceived() throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClientPost.sendPostWithFormData(serviceUrl);
        assertAll(
          () -> assertThat(response.statusCode()).isEqualTo(200),
          () -> assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}")
        );
    }

}
