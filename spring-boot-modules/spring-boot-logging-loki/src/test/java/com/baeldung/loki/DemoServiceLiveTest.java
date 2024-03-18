package com.baeldung.loki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = DemoService.class)
public class DemoServiceLiveTest {

    @Test
    public void givenLokiContainerRunning_whenDemoServiceInvoked_thenLokiAppenderCollectLogs() throws JsonProcessingException, InterruptedException {
        DemoService service = new DemoService();
        service.log();
        Thread.sleep(2000);
        String baseUrl = "http://localhost:3100/loki/api/v1/query_range";

        // Set up query parameters
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String query = "{level=\"INFO\"} |= `DemoService.log invoked`";

        // Get current time in UTC
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
        String current_time_est = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));

        LocalDateTime  tenMinsAgo = currentDateTime.minusMinutes(10);
        String start_time_est = tenMinsAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));

        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
            .queryParam("query", query)
            .queryParam("start", start_time_est)
            .queryParam("end", current_time_est)
            .build()
            .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            new HttpEntity<>(headers),
            String.class
        );

        List<String> messages = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode result = jsonNode.get("data").get("result").get(0).get("values");
            result.iterator().forEachRemaining(e-> {
                Iterator<JsonNode> elements = e.elements();
                elements.forEachRemaining(f ->
                    messages.add(f.toString())
                );
            });
        } else {
            System.out.println("Error: " + response.getStatusCodeValue());
        }

        String expected = "DemoService.log invoked";
        assertTrue(messages.stream().anyMatch(e -> e.contains(expected)));
    }
}
