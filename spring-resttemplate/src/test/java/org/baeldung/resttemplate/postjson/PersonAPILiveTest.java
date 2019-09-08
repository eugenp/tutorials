package org.baeldung.resttemplate.postjson;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URI;

import org.baeldung.resttemplate.RestTemplateConfigurationApplication;
import org.baeldung.resttemplate.web.dto.Person;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestTemplateConfigurationApplication.class)
public class PersonAPILiveTest {

    private static String createPersonUrl;
    private static String updatePersonUrl;

    private static RestTemplate restTemplate;

    private static HttpHeaders headers;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static JSONObject personJsonObject;

    @BeforeClass
    public static void runBeforeAllTestMethods() throws JSONException {
        createPersonUrl = "http://localhost:8082/spring-rest/createPerson";
        updatePersonUrl = "http://localhost:8082/spring-rest/updatePerson";

        restTemplate = new RestTemplate();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        personJsonObject = new JSONObject();
        personJsonObject.put("id", 1);
        personJsonObject.put("name", "John");
    }

    @Test
    public void givenDataIsJson_whenDataIsPostedByPostForObject_thenResponseBodyIsNotNull() throws IOException {
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
        String personResultAsJsonStr = restTemplate.postForObject(createPersonUrl, request, String.class);
        JsonNode root = objectMapper.readTree(personResultAsJsonStr);

        Person person = restTemplate.postForObject(createPersonUrl, request, Person.class);

        assertNotNull(personResultAsJsonStr);
        assertNotNull(root);
        assertNotNull(root.path("name")
            .asText());

        assertNotNull(person);
        assertNotNull(person.getName());
    }

    @Test
    public void givenDataIsJson_whenDataIsPostedByPostForEntity_thenResponseBodyIsNotNull() throws IOException {
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(createPersonUrl, request, String.class);
        JsonNode root = objectMapper.readTree(responseEntityStr.getBody());

        ResponseEntity<Person> responseEntityPerson = restTemplate.postForEntity(createPersonUrl, request, Person.class);

        assertNotNull(responseEntityStr.getBody());
        assertNotNull(root.path("name")
            .asText());

        assertNotNull(responseEntityPerson.getBody());
        assertNotNull(responseEntityPerson.getBody()
            .getName());
    }

    @Test
    public void givenDataIsJson_whenDataIsPostedByPostForLocation_thenResponseBodyIsTheLocationHeader() throws JsonProcessingException {
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
        URI locationHeader = restTemplate.postForLocation(updatePersonUrl, request);

        assertNotNull(locationHeader);
    }
}