package com.baeldung.functional;

import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.reactive.function.BodyExtractors.toDataBuffers;
import static org.springframework.web.reactive.function.BodyExtractors.toFormData;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class FormHandler {

    Mono<ServerResponse> handleLogin(ServerRequest request) {
        return request
          .body(toFormData())
          .map(MultiValueMap::toSingleValueMap)
          .map(formData -> {
              System.out.println("form data: " + formData.toString());
              if ("baeldung".equals(formData.get("user")) && "you_know_what_to_do".equals(formData.get("token"))) {
                  return ok()
                    .body(Mono.just("welcome back!"), String.class)
                    .block();
              }
              return ServerResponse
                .badRequest()
                .build()
                .block();
          });
    }

    Mono<ServerResponse> handleUpload(ServerRequest request) {
        return request
          .body(toDataBuffers())
          .collectList()
          .map(dataBuffers -> {
              AtomicLong atomicLong = new AtomicLong(0);
              dataBuffers.forEach(d -> atomicLong.addAndGet(d
                .asByteBuffer()
                .array().length));
              System.out.println("data length:" + atomicLong.get());
              return ok()
                .body(fromObject(atomicLong.toString()))
                .block();
          });
    }
}
