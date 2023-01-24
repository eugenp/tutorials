package com.baeldung.jsonb;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyNamingStrategy;
import javax.json.bind.config.PropertyOrderStrategy;

import org.apache.commons.collections4.ListUtils;
import org.junit.Test;

import com.baeldung.adapter.PersonAdapter;
import com.baeldung.jsonb.Person;

public class JsonbUnitTest {

    @Test
    public void givenPersonList_whenSerializeWithJsonb_thenGetPersonJsonArray() {
        Jsonb jsonb = JsonbBuilder.create();
        // @formatter:off
        List<Person> personList = Arrays.asList(
            new Person(1, "Jhon", "jhon@test.com", 20, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1000)), 
            new Person(2, "Jhon", "jhon1@test.com", 20, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1500)), 
            new Person(3, "Jhon", null, 20, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1000)),  
            new Person(4, "Tom", "tom@test.com", 21, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1500)));            
        // @formatter:on
        String jsonArrayPerson = jsonb.toJson(personList);
        // @formatter:off
        String jsonExpected = "[" + 
          "{\"email\":\"jhon@test.com\"," + 
           "\"id\":1,\"person-name\":\"Jhon\"," + 
           "\"registeredDate\":\"09-09-2019\"," + 
           "\"salary\":\"1000.0\"}," +
          "{\"email\":\"jhon1@test.com\"," +
           "\"id\":2,\"person-name\":\"Jhon\"," +
           "\"registeredDate\":\"09-09-2019\"," +
           "\"salary\":\"1500.0\"},{\"email\":null," +
           "\"id\":3,\"person-name\":\"Jhon\"," +
           "\"registeredDate\":\"09-09-2019\"," +
           "\"salary\":\"1000.0\"}," +
          "{\"email\":\"tom@test.com\"," +
           "\"id\":4,\"person-name\":\"Tom\"," +
           "\"registeredDate\":\"09-09-2019\"," +
           "\"salary\":\"1500.0\"}"
           + "]";
        // @formatter:on
        assertTrue(jsonArrayPerson.equals(jsonExpected));
    }

    @Test
    public void givenPersonJsonArray_whenDeserializeWithJsonb_thenGetPersonList() {
        Jsonb jsonb = JsonbBuilder.create();
        // @formatter:off
        String personJsonArray =
            "[" + 
                "{\"email\":\"jhon@test.com\"," + 
                 "\"id\":1,\"person-name\":\"Jhon\"," + 
                 "\"registeredDate\":\"09-09-2019\"," + 
                 "\"salary\":\"1000.0\"}," +
                "{\"email\":\"jhon1@test.com\"," +
                 "\"id\":2,\"person-name\":\"Jhon\"," +
                 "\"registeredDate\":\"09-09-2019\"," +
                 "\"salary\":\"1500.0\"},{\"email\":null," +
                 "\"id\":3,\"person-name\":\"Jhon\"," +
                 "\"registeredDate\":\"09-09-2019\"," +
                 "\"salary\":\"1000.0\"}," +
                "{\"email\":\"tom@test.com\"," +
                 "\"id\":4,\"person-name\":\"Tom\"," +
                 "\"registeredDate\":\"09-09-2019\"," +
                 "\"salary\":\"1500.0\"}"
                 + "]";
        // @formatter:on        
        @SuppressWarnings("serial")
        List<Person> personList = jsonb.fromJson(personJsonArray, new ArrayList<Person>() {
        }.getClass()
            .getGenericSuperclass());
        // @formatter:off
        List<Person> personListExpected = Arrays.asList(
            new Person(1, "Jhon", "jhon@test.com", 20, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1000)), 
            new Person(2, "Jhon", "jhon1@test.com", 20, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1500)), 
            new Person(3, "Jhon", null, 20, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1000)),  
            new Person(4, "Tom", "tom@test.com", 21, LocalDate.of(2019, 9, 9), BigDecimal.valueOf(1500)));            
        // @formatter:on
        assertTrue(ListUtils.isEqualList(personList, personListExpected));
    }

    @Test
    public void givenPersonObject_whenNamingStrategy_thenGetCustomPersonJson() {
        JsonbConfig config = new JsonbConfig().withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES);
        Jsonb jsonb = JsonbBuilder.create(config);
        Person person = new Person(1, "Jhon", "jhon@test.com", 20, LocalDate.of(2019, 9, 7), BigDecimal.valueOf(1000));
        String jsonPerson = jsonb.toJson(person);
        // @formatter:off
        String jsonExpected = 
            "{\"email\":\"jhon@test.com\"," +
             "\"id\":1," +
             "\"person-name\":\"Jhon\"," +
             "\"registered_date\":\"07-09-2019\"," +
             "\"salary\":\"1000.0\"}";
        // @formatter:on
        assertTrue(jsonExpected.equals(jsonPerson));
    }

    @Test
    public void givenPersonObject_whenWithPropertyOrderStrategy_thenGetReversePersonJson() {
        JsonbConfig config = new JsonbConfig().withPropertyOrderStrategy(PropertyOrderStrategy.REVERSE);
        Jsonb jsonb = JsonbBuilder.create(config);
        Person person = new Person(1, "Jhon", "jhon@test.com", 20, LocalDate.of(2019, 9, 7), BigDecimal.valueOf(1000));
        String jsonPerson = jsonb.toJson(person);
        // @formatter:off
        String jsonExpected = 
            "{\"salary\":\"1000.0\","+
             "\"registeredDate\":\"07-09-2019\"," +
             "\"person-name\":\"Jhon\"," +
             "\"id\":1," +
             "\"email\":\"jhon@test.com\"}";
        // @formatter:on
        assertTrue(jsonExpected.equals(jsonPerson));
    }

    @Test
    public void givenPersonObject_whenSerializeWithJsonb_thenGetPersonJson() {
        Jsonb jsonb = JsonbBuilder.create();
        Person person = new Person(1, "Jhon", "jhon@test.com", 20, LocalDate.of(2019, 9, 7), BigDecimal.valueOf(1000));
        String jsonPerson = jsonb.toJson(person);
        // @formatter:off
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
        Jsonb jsonb = JsonbBuilder.create();
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

    @Test
    public void givenPersonObject_whenSerializeWithAdapter_thenGetPersonJson() {
        JsonbConfig config = new JsonbConfig().withAdapters(new PersonAdapter());
        Jsonb jsonb = JsonbBuilder.create(config);
        Person person = new Person(1, "Jhon", "jhon@test.com", 0, LocalDate.of(2019, 9, 7), BigDecimal.valueOf(1000.0));// new Person(1, "Jhon");
        String jsonPerson = jsonb.toJson(person);
        // @formatter:off
        String jsonExpected = 
            "{\"id\":1," +
             "\"name\":\"Jhon\"}";
        // @formatter:on
        assertTrue(jsonExpected.equals(jsonPerson));
    }

    @Test
    public void givenPersonJson_whenDeserializeWithAdapter_thenGetPersonObject() {
        JsonbConfig config = new JsonbConfig().withAdapters(new PersonAdapter());
        Jsonb jsonb = JsonbBuilder.create(config);
        Person person = new Person(1, "Jhon", "jhon@test.com", 0, LocalDate.of(2019, 9, 7), BigDecimal.valueOf(1000.0));// new Person(1, "Jhon");
        // @formatter:off
        String jsonPerson = 
            "{\"id\":1," +
             "\"name\":\"Jhon\"}";
        // @formatter:on
        assertTrue(jsonb.fromJson(jsonPerson, Person.class)
            .equals(person));
    }

}
