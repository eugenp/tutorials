package com.baeldung.httpclient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

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

}
