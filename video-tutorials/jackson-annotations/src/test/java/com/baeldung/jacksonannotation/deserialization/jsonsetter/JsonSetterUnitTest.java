package com.baeldung.jacksonannotation.deserialization.jsonsetter;

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
public class JsonSetterUnitTest {

    @Test
    public void whenDeserializingUsingJsonSetter_thenCorrect() throws IOException {

        // arrange
        String json = "{\"firstName\":\"Alex\",\"lastName\":\"Theedom\",\"publications\":[{\"title\":\"Professional Java EE Design Patterns\"}]}";

        // act
        Author author = new ObjectMapper().readerFor(Author.class).readValue(json);

        // assert
        assertThat(from(json).getList("publications").size()).isEqualTo(author.getItems().size());

    }
}