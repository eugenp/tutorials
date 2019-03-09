package com.baeldung.jackson.inclusion.jsoninclude;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class JsonIncludeUnitTest {

    @Test
    public void whenSerializingUsingJsonInclude_thenCorrect() throws JsonProcessingException {

        // arrange
        Author author = new Author("Alex", null);

        // act
        String result = new ObjectMapper().writeValueAsString(author);

        // assert
        assertThat(from(result).getString("firstName")).isEqualTo("Alex");
        assertThat(result).doesNotContain("lastName");

        /*
            {
              "id": "e8bb4802-6e0c-4fa5-9f68-c233272399cd",
              "firstName": "Alex",
              "items": []
            }
        */

    }
}