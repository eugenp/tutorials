package com.baeldung.jacksonannotation.serialization.jsonvalue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class JsonValueUnitTest {

    @Test
    public void whenSerializingUsingJsonValue_thenCorrect() throws JsonProcessingException {

        // act
        String result = new ObjectMapper().writeValueAsString(Course.Level.ADVANCED);

        // assert
        assertThat(result).isEqualTo("\"Advanced\"");

        /*

        "Advanced"

         */

    }

    @Test
    public void whenSerializingAuthorUsingJsonValue_thenFirstNameAndLastNameAreConcatenated() throws JsonProcessingException {

        // arrange
        Author me = new Author("Alex", "Theedom");

        // act
        String result = new ObjectMapper().writeValueAsString(me);

        // assert
        assertThat(result).contains("Alex Theedom");

        /*
            {
              "name": "Alex Theedom"
            }

         */

    }
}