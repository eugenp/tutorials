package com.baeldung.jackson.serialization.jsonpropertyorder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class JsonPropertyOrderUnitTest {

    @Test
    public void whenSerializingUsingJsonPropertyOrder_thenCorrect() throws JsonProcessingException {

        // arrange
        Author author = new Author("Alex", "Theedom");

        // act
        String result = new ObjectMapper().writeValueAsString(author);

        // assert
        assertThat(result, matchesJsonSchemaInClasspath("author-jsonpropertyorder-schema.json"));

        // NOTE: property order is not enforced by the JSON specification.

        /*
            {
              "items": [],
              "firstName": "Alex",
              "lastName": "Theedom",
              "id": "fd277638-9b6e-49f7-81c1-bc52f165245b"
            }
        */

    }
}
