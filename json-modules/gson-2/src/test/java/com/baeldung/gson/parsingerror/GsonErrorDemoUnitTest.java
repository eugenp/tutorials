package com.baeldung.gson.parsingerror;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Type;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import com.baeldung.gson.parsingerrors.Person;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

class GsonErrorDemoUnitTest {

    @Test
    void givenAJsonArray_WhenTellingGsonToExpectAnObject_ThenThrows() {
        assertThrows(JsonSyntaxException.class, () -> {
            Person person = new Gson().fromJson("[{\"name\":\"John\"},{\"name\":\"James\"}]", Person.class);
        });
    }

    @Test
    void givenAJsonArray_WhenParsingIntoAnArray_ThenOK() {
        Person[] personArray = new Gson().fromJson("[{\"name\":\"John\"},{\"name\":\"James\"}]", Person[].class);
        assertThat(personArray).extracting(Person::getName)
          .containsExactly("John", "James");
    }

    @Test
    void givenAJsonArray_WhenParsingIntoACollection_ThenOK() {
        Type collectionType = new TypeToken<Collection<Person>>() {
        }.getType();
        Collection<Person> personCollection = new Gson().fromJson("[{\"name\":\"John\"},{\"name\":\"James\"}]", collectionType);
        assertThat(personCollection).extracting(Person::getName)
          .containsExactly("John", "James");
    }

    @Test
    void givenAJsonObject_WhenTellingGsonToExpectAnArray_ThenThrows() {
        assertThrows(JsonSyntaxException.class, () -> {
            Person[] personArray = new Gson().fromJson("{\"name\":\"John\"}", Person[].class);
        });
    }

    @Test
    void givenAJsonObject_WhenParsingIntoAnObject_ThenOK() {
        Person person = new Gson().fromJson("{\"name\":\"John\"}", Person.class);
        assertEquals("John", person.getName());
    }

}
