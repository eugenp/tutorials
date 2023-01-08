package com.baeldung.jackson.jsonignorevstransient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

class PersonUnitTest {

    @Test
    void givenPerson_whenSerializing_thenIdFieldIgnored() throws JsonProcessingException {

        Person person = new Person(1L, "My First Name", "My Last Name");
        String result = new ObjectMapper().writeValueAsString(person);

        assertThat(result, containsString("firstName"));
        assertThat(result, containsString("lastName"));
        assertThat(result, not(containsString("id")));
    }
}