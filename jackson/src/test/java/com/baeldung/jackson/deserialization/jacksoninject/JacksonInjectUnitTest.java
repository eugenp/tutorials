package com.baeldung.jackson.deserialization.jacksoninject;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class JacksonInjectUnitTest {

    @Test
    public void whenDeserializingUsingJacksonInject_thenCorrect() throws IOException {

        UUID id = UUID.fromString("9616dc8c-bad3-11e6-a4a6-cec0c932ce01");

        // arrange
        String authorJson = "{\"firstName\": \"Alex\", \"lastName\": \"Theedom\"}";

        // act
        InjectableValues inject = new InjectableValues.Std().addValue(UUID.class, id);
        Author author = new ObjectMapper().reader(inject)
            .forType(Author.class)
            .readValue(authorJson);

        // assert
        assertThat(author.getId()).isEqualTo(id);

        /*
            {
              "firstName": "Alex",
              "lastName": "Theedom",
              "publications": []
            }
        */

    }
}