package com.baeldung.resttemplate.postjson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.json.JSONException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.baeldung.resttemplate.RestTemplateApplication;
import com.baeldung.resttemplate.web.dto.Person;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestTemplateApplication.class)
public class RestTemplatePostRequestEncodingLiveTest {
    private static String createPersonUrl;
    private static RestTemplate restTemplate;

    @BeforeClass
    public static void runBeforeAllTestMethods() throws JSONException {
        createPersonUrl = "http://localhost:8080/spring-rest/createPerson";
        restTemplate = new RestTemplate();
    }

    @Test
    public void givenJapaneseNameInDataWithoutHeaderEncoding_whenDataIsPostedByPostForObject_thenSaveIncorrectly() {
        Person japanese = new Person(100, "関連当");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> request = new HttpEntity<>(japanese, headers);

        Person person = restTemplate.postForObject(createPersonUrl, request, Person.class);
        assertNotNull(person);
        assertNotEquals("関連当", person.getName());
    }

    @Test
    public void givenJapaneseNameInDataWithHeaderEncoding_whenDataIsPostedByPostForObject_thenSaveCorrectly() {
        Person japanese = new Person(100, "関連当");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/json;charset=UTF-8");
        HttpEntity<Person> request = new HttpEntity<>(japanese, headers);

        Person person = restTemplate.postForObject(createPersonUrl, request, Person.class);
        assertNotNull(person);
        assertEquals("関連当", person.getName());
    }
}