package com.baeldung.jsonb;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.Before;
import org.junit.Test;

public class JsonbTest {

    private Jsonb jsonb;

    @Before
    public void setup() {
        jsonb = JsonbBuilder.create();
    }

    @Test
    public void givenPersonObject_whenSerializeWithJsonb_thenGetPersonJson() {
        Person person = new Person(1, "Jhon", "jhon@test.com", 20, LocalDate.of(2019, 9, 7), BigDecimal.valueOf(1000));
        String jsonPerson = jsonb.toJson(person);
        //// @formatter:off
        String jsonExpected = 
            "{\"email\":\"jhon@test.com\"," +
             "\"id\":1," +
             "\"person-name\":\"Jhon\"," +
             "\"registeredDate\":\"07-09-2019\"," +
             "\"salary\":\"1000.0\"}";
        // @formatter:on
        assertTrue(jsonExpected.equals(jsonPerson));
    }

    @Test
    public void givenPersonJson_whenDeserializeWithJsonb_thenGetPersonObject() {
        Person person = new Person(1, "Jhon", "jhon@test.com", 0, LocalDate.of(2019, 9, 7), BigDecimal.valueOf(1000.0));
        // @formatter:off
        String jsonPerson = 
            "{\"email\":\"jhon@test.com\"," + 
             "\"id\":1," + 
             "\"person-name\":\"Jhon\"," + 
             "\"registeredDate\":\"07-09-2019\"," + 
             "\"salary\":\"1000.0\"}";
        // @formatter:on
        assertTrue(jsonb.fromJson(jsonPerson, Person.class)
            .equals(person));
    }

}
