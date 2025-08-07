package com.baeldung.parametrizedtypereference;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ReactiveApiService {

    private final WebClient webClient;

    public ReactiveApiService(String baseUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Mono<Map<String, List<User>>> fetchUsersByDepartment() {
        ParameterizedTypeReference<Map<String, List<User>>> typeRef = new ParameterizedTypeReference<Map<String, List<User>>>() {
        };

        return webClient.get()
            .uri("/users/by-department")
            .retrieve()
            .bodyToMono(typeRef);
    }

    public Mono<ApiResponse<List<User>>> fetchUsersWithWrapper() {
        ParameterizedTypeReference<ApiResponse<List<User>>> typeRef = new ParameterizedTypeReference<ApiResponse<List<User>>>() {
        };

        return webClient.get()
            .uri("/users/wrapped")
            .retrieve()
            .bodyToMono(typeRef);
    }

}