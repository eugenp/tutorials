package com.baeldung.httpclient;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HttpClientPostUnitTest extends PostRequestMockServer {

    @Test
    void givenSyncPostRequest_whenServerIsAvailable_thenOkStatusIsReceived() throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClientPost.sendSynchronousPost(serviceUrl);
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}");
    }

    @Test
    void givenAsyncPostRequest_whenServerIsAvailable_thenOkStatusIsReceived() throws ExecutionException, InterruptedException {
        CompletableFuture<HttpResponse<String>> futureResponse = HttpClientPost.sendAsynchronousPost(serviceUrl);
        HttpResponse<String> response = futureResponse.get();

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}");
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
            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}");
        });
    }

    @Test
    void givenPostRequestWithAuthClient_whenServerIsAvailable_thenOkStatusIsReceived() throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClientPost.sendPostWithAuthClient(serviceUrl);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}");
    }

    @Test
    void givenPostRequestWithAuthHeader_whenServerIsAvailable_thenOkStatusIsReceived() throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClientPost.sendPostWithAuthHeader(serviceUrl);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}");
    }

    @Test
    void givenPostRequestWithJsonBody_whenServerIsAvailable_thenOkStatusIsReceived() throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClientPost.sendPostWithJsonBody(serviceUrl);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}");
    }

    @Test
    void givenPostRequestWithFormData_whenServerIsAvailable_thenOkStatusIsReceived() throws IOException, InterruptedException {
        HttpResponse<String> response = HttpClientPost.sendPostWithFormData(serviceUrl);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}");
    }

    @Test
    void givenPostRequestWithFileData_whenServerIsAvailable_thenOkStatusIsReceived(@TempDir Path tempDir) throws IOException, InterruptedException {
        Path file = tempDir.resolve("temp.txt");
        List<String> lines = Arrays.asList("1", "2", "3");
        Files.write(file, lines);

        HttpResponse<String> response = HttpClientPost.sendPostWithFileData(serviceUrl, file);

        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("{\"message\":\"ok\"}");
    }

}
