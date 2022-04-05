package com.baeldung.jacksonannotation.serialization.jsonpropertyorder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class JsonPropertyOrderUnitTest {

    @Test
    public void whenSerializingUsingJsonPropertyOrder_thenCorrect() throws JsonProcessingException {

        // arrange
        Author author = new Author("Alex", "Theedom");
        author.setzIndex("z123");
        author.setAlphaIndex("z123");

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
              "id": "31ca2af9-df0a-4d49-a74c-86c0a3f944a2",
              "alphaIndex": "z123",
              "zIndex": "z123"
            }
        */

    }
}
