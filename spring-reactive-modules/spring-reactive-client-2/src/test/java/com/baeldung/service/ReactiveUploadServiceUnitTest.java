package com.baeldung.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

class ReactiveUploadServiceUnitTest {

    private static final String BASE_URL = "http://localhost:8080/external/upload";

    final WebClient webClientMock = WebClient.builder().baseUrl(BASE_URL)
            .exchangeFunction(clientRequest -> Mono.just(ClientResponse.create(HttpStatus.OK)
                .header("content-type", "application/json")
                .build()))
            .build();

    private final ReactiveUploadService tested = new ReactiveUploadService(webClientMock);

    @Test
    void givenAPdf_whenUploadingWithWebClient_thenOK() {
        final Resource file = mock(Resource.class);

        final Mono<HttpStatusCode> result = tested.uploadPdf(file);
        final HttpStatusCode status = result.block();

        assertThat(status).isEqualTo(HttpStatus.OK);
    }

    @Test
    void givenAMultipartPdf_whenUploadingWithWebClient_thenOK() {
        final Resource file = mock(Resource.class);
        final MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getResource()).thenReturn(file);

        final Mono<HttpStatusCode> result = tested.uploadMultipart(multipartFile);
        final HttpStatusCode status = result.block();

        assertThat(status).isEqualTo(HttpStatus.OK);
    }
}