package com.baeldung.parametrizedtypereference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReactiveApiServiceUnitTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private ReactiveApiService reactiveApiService;

    @BeforeEach
    void setUp() {

        reactiveApiService = new ReactiveApiService(WebClient.builder()) {
            @Override
            public Mono<Map<String, List<User>>> fetchUsersByDepartment() {
                ParameterizedTypeReference<Map<String, List<User>>> typeRef =
                        new ParameterizedTypeReference<Map<String, List<User>>>() {};

                return webClient.get()
                        .uri("/users/by-department")
                        .retrieve()
                        .bodyToMono(typeRef);
            }

            @Override
            public Mono<ApiResponse<List<User>>> fetchUsersWithWrapper() {
                ParameterizedTypeReference<ApiResponse<List<User>>> typeRef =
                        new ParameterizedTypeReference<ApiResponse<List<User>>>() {};

                return webClient.get()
                        .uri("/users/wrapped")
                        .retrieve()
                        .bodyToMono(typeRef);
            }
        };
    }

    @Test
    void whenFetchingUsersByDepartment_thenReturnsCorrectMap() {
        // given
        Map<String, List<User>> expectedMap = new HashMap<>();
        expectedMap.put("Engineering", Arrays.asList(
                new User(1L, "John Doe", "john@example.com", "Engineering")
        ));

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/users/by-department")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(expectedMap));

        // when
        Mono<Map<String, List<User>>> result = reactiveApiService.fetchUsersByDepartment();

        // then
        StepVerifier.create(result)
                .assertNext(map -> {
                    assertTrue(map.containsKey("Engineering"));
                    assertEquals("John Doe", map.get("Engineering").get(0).getName());
                })
                .verifyComplete();
    }

    @Test
    void whenFetchingUsersWithWrapper_thenReturnsApiResponse() {
        // given
        List<User> users = Arrays.asList(
                new User(1L, "John Doe", "john@example.com", "Engineering"),
                new User(2L, "Jane Smith", "jane@example.com", "Marketing")
        );
        ApiResponse<List<User>> expectedResponse = new ApiResponse<>(true, "Success", users);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/users/wrapped")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(any(ParameterizedTypeReference.class)))
                .thenReturn(Mono.just(expectedResponse));

        // when
        Mono<ApiResponse<List<User>>> result = reactiveApiService.fetchUsersWithWrapper();

        // then
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertTrue(response.success());
                    assertEquals("Success", response.message());
                    assertEquals(2, response.data().size());
                    assertEquals("John Doe", response.data().get(0).getName());
                })
                .verifyComplete();
    }
}
