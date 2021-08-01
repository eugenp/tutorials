package com.baeldung.reactive.service;


import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class ReactiveUploadService {

    private final WebClient webClient;
    private static final String EXTERNAL_UPLOAD_URL = "http://localhost:8080/external/upload";

    public ReactiveUploadService() {
        this.webClient = WebClient.create();
    }


    public Mono<HttpStatus> uploadPdf(final Resource resource){

        final URI url = UriComponentsBuilder.fromHttpUrl(EXTERNAL_UPLOAD_URL).build().toUri();
        Mono<HttpStatus> httpStatusMono = webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_PDF)
                .body(BodyInserters.fromResource(resource))
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
                    } else {
                        System.out.println("Failed to upload pdf. " + response.statusCode());
                    }
                    return null;
                });
        return httpStatusMono;
    }


    public Mono<HttpStatus> uploadMultipart(final MultipartFile multipartFile){
        final URI url = UriComponentsBuilder.fromHttpUrl(EXTERNAL_UPLOAD_URL).build().toUri();

        final MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", multipartFile.getResource());

        Mono<HttpStatus> httpStatusMono = webClient.post()
                .uri(url)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
                    } else {
                        System.out.println("Failed to upload pdf. " + response.statusCode());
                    }
                    return null;
                });
        return httpStatusMono;
    }


}
