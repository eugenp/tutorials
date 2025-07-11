package com.baeldung.parametrizedtypereference;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ReactiveApiService {

    private final WebClient webClient;

    public ReactiveApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://api.example.com").build();
    }

    public Mono<Map<String, List<User>>> fetchUsersByDepartment() {
        ParameterizedTypeReference<Map<String, List<User>>> typeRef =
                new ParameterizedTypeReference<Map<String, List<User>>>() {};

        return webClient.get()
                .uri("/users/by-department")
                .retrieve()
                .bodyToMono(typeRef);
    }

    public Mono<ApiResponse<List<User>>> fetchUsersWithWrapper() {
        ParameterizedTypeReference<ApiResponse<List<User>>> typeRef =
                new ParameterizedTypeReference<ApiResponse<List<User>>>() {};

        return webClient.get()
                .uri("/users/wrapped")
                .retrieve()
                .bodyToMono(typeRef);
    }

    public Mono<List<User>> fetchUsersReactive() {
        return webClient.get()
                .uri("/users")
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        Mono.error(new ApiException("API error")))
                .bodyToMono(TypeReferences.USER_LIST)
                .onErrorResume(WebClientException.class, ex ->
                        Mono.just(Collections.emptyList()));
    }
}
