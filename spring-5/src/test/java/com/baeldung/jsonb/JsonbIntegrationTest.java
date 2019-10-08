package com.baeldung.jsonb;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class JsonbIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void givenId_whenUriIsPerson_thenGetPerson() {
        ResponseEntity<Person> response = template
            .getForEntity("/person/1", Person.class);
        Person person = response.getBody();
        assertTrue(person.equals(new Person(2, "Jhon", "jhon1@test.com", 0, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1500.0))));
    }

    @Test
    public void whenSendPostAPerson_thenGetOkStatus() {
        ResponseEntity<Boolean> response = template.withBasicAuth("user","password").
            postForEntity("/person", "{\"birthDate\":\"07-09-2017\",\"email\":\"jhon1@test.com\",\"person-name\":\"Jhon\",\"id\":10}", Boolean.class);
        assertTrue(response.getBody());
    }

}
