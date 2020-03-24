package com.baeldung.jacksonannotation.inclusion.jsonignore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class JsonIgnoreUnitTest {

    @Test
    public void whenSerializingUsingJsonIgnore_thenCorrect() throws JsonProcessingException {

        // arrange
        Author author = new Author("Alex", "Theedom");

        // act
        String result = new ObjectMapper().writeValueAsString(author);

        // assert
        assertThat(from(result).getString("firstName")).isEqualTo("Alex");
        assertThat(from(result).getString("lastName")).isEqualTo("Theedom");
        assertThat(from(result).getString("id")).isNull();

        /*
            {
              "firstName": "Alex",
              "lastName": "Theedom",
              "items": []
            }
        */

    }
}