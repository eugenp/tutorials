package com.baeldung.jacksonannotation.deserialization.jsoncreator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class JsonCreatorUnitTest {

    @Test
    public void whenDeserializingUsingJsonCreator_thenCorrect() throws IOException {

        // arrange
        String authorJson =
          "{" +
            "    \"christianName\": \"Alex\"," +
            "    \"surname\": \"Theedom\"" +
            "}";

        // act
        final Author author = new ObjectMapper().readerFor(Author.class).readValue(authorJson);

        // assert
        assertThat(from(authorJson).getString("christianName")).isEqualTo(author.getFirstName());
        assertThat(from(authorJson).getString("surname")).isEqualTo(author.getLastName());

        /*
            {
              "christianName": "Alex",
              "surname": "Theedom"
            }
        */

    }
}