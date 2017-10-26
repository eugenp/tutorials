package com.baeldung.jackson.serialization.jsonvalue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class JsonValueUnitTest {

    @Test
    public void whenSerializingUsingJsonValue_thenCorrect() throws JsonProcessingException {

        // act
        String result = new ObjectMapper().writeValueAsString(Course.Level.ADVANCED);

        // assert
        assertThat(result).isEqualTo("\"Advanced\"");

    }
}