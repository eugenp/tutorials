package com.baeldung.xss;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerUnitTest {

    @LocalServerPort
    int randomServerPort;

    private static String createPersonUrl;
    private static RestTemplate restTemplate;
    private static HttpHeaders headers;
    private static UriComponentsBuilder builder;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static JSONObject personJsonObject;

    @Test
    public void givenRequestIsSuspicious_whenRequestIsPost_thenResponseIsClean()
        throws IOException, JSONException {
        // given
        createPersonUrl = "http://localhost:" + randomServerPort + "/personService/person";
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        personJsonObject = new JSONObject();

        // when
        personJsonObject.put("id", 1);
        personJsonObject.put("firstName", "baeldung <script>alert('XSS')</script>");
        personJsonObject.put("lastName", "baeldung <b onmouseover=alert('XSS')>click me!</b>");

        builder = UriComponentsBuilder.fromHttpUrl(createPersonUrl)
            .queryParam("param", "<script>");

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("header_1", "<body onload=alert('XSS')>");
        headers.add("header_2", "<span onmousemove='doBadXss()'>");
        HttpEntity<String> request = new HttpEntity<>(personJsonObject.toString(), headers);

        ResponseEntity<String> personResultAsJsonStr = restTemplate.exchange(builder.toUriString(),
            HttpMethod.POST, request, String.class);
        JsonNode root = objectMapper.readTree(personResultAsJsonStr.getBody());

        // then
        assertEquals(root.get("lastName")
            .textValue(), "baeldung click me!");
        assertEquals(root.get("param")
            .textValue(), "");
        assertEquals(root.get("header_1")
            .textValue(), "");
        assertEquals(root.get("header_2")
            .textValue(), "");
    }
}
