package com.baeldung.springai.mcp.oauth2;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("mcp")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class McpServerOAuth2LiveTest {

    private static final Logger log = LoggerFactory.getLogger(McpServerOAuth2LiveTest.class);

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @BeforeEach
    void setup() {
        webClient = WebClient.create("http://localhost:" + port);
    }

    @Test
    void givenSecuredMcpServer_whenCallingTheEndpointsWithValidAuthorizationHeader_thenExpectedResponseShouldBeObtained() {
        Flux<String> eventStream = webClient.get()
          .uri("/sse")
          .header("Authorization", obtainAccessToken())
          .accept(MediaType.TEXT_EVENT_STREAM)
          .retrieve()
          .bodyToFlux(String.class);

        eventStream.subscribe(
            data -> {
                log.info("Response received: {}", data);
                if(!isRequestMessage(data)) {
                    assertThat(data).containsSequence("AAPL", "$150");
                }
            },
            error -> log.error(error.getMessage()),
            () -> log.info("Stream completed"));

        Flux<String> sendMessage = webClient.post()
          .uri("/mcp/message")
          .header("Authorization", obtainAccessToken())
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.TEXT_EVENT_STREAM)
          .bodyValue("""
             {
                 "jsonrpc": "2.0",
                 "id": "1",
                 "method": "tools/call",
                 "params": {
                     "name": "getStockPrice",
                     "arguments": {
                         "arg0": "AAPL"
                     }
                 }
             }
             """)
          .retrieve()
          .bodyToFlux(String.class);

        sendMessage.blockLast();
        eventStream.blockLast();
    }

    private boolean isRequestMessage(String data) {
        return data.contains("/mcp/message");
    }

    public String obtainAccessToken() {
        String clientId = "mcp-client";
        String clientSecret = "secret";
        String basicToken = Base64.getEncoder()
          .encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8));

        return "Bearer " + webClient.post()
          .uri("/oauth2/token")
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
          .header(HttpHeaders.AUTHORIZATION, "Basic " + basicToken)
          .body(BodyInserters
                  .fromFormData("grant_type", "client_credentials")
          )
          .retrieve()
          .bodyToMono(JsonNode.class)
          .map(node -> node.get("access_token").asText())
          .block(Duration.ofSeconds(5));
    }
}
