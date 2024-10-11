package com.baeldung.service;

import java.net.URI;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.baeldung.exception.ServiceException;

import reactor.core.publisher.Mono;

@Service
public class ReactiveUploadService {

    private final WebClient webClient;
    private static final String EXTERNAL_UPLOAD_URL = "http://localhost:8080/external/upload";

    public ReactiveUploadService(final WebClient webClient) {
        this.webClient = webClient;
    }


    public Mono<HttpStatusCode> uploadPdf(final Resource resource) {

        final URI url = UriComponentsBuilder.fromHttpUrl(EXTERNAL_UPLOAD_URL).build().toUri();
        Mono<HttpStatusCode> httpStatusMono = webClient.post()
          .uri(url)
          .contentType(MediaType.APPLICATION_PDF)
          .body(BodyInserters.fromResource(resource))
          .exchangeToMono(response -> {
              if (response.statusCode().equals(HttpStatus.OK)) {
                  return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
              } else {
                  throw new ServiceException("Error uploading file");
              }
          });
        return httpStatusMono;
    }


    public Mono<HttpStatusCode> uploadMultipart(final MultipartFile multipartFile) {
        final URI url = UriComponentsBuilder.fromHttpUrl(EXTERNAL_UPLOAD_URL).build().toUri();

        final MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", multipartFile.getResource());

        Mono<HttpStatusCode> httpStatusMono = webClient.post()
          .uri(url)
          .contentType(MediaType.MULTIPART_FORM_DATA)
          .body(BodyInserters.fromMultipartData(builder.build()))
          .exchangeToMono(response -> {
              if (response.statusCode().equals(HttpStatus.OK)) {
                  return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
              } else {
                  throw new ServiceException("Error uploading file");
              }
          });
        return httpStatusMono;
    }
}
