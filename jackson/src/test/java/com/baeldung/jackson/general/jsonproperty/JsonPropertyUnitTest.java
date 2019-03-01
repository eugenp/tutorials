package com.baeldung.jackson.general.jsonproperty;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class JsonPropertyUnitTest {

    @Test
    public void whenSerializingUsingJsonProperty_thenCorrect() throws JsonProcessingException {

        // arrange
        Book book = new Book("Design Patterns: Elements of Reusable Object-oriented Software", new Author("The", "GoF"));
        book.configureBinding("Hardback");

        // act
        String result = new ObjectMapper().writeValueAsString(book);

        // assert
        assertThat(from(result).getString("binding")).isEqualTo("Hardback");

        /*
            {
              "id": "cd941587-d1ae-4c2a-9a36-29533bf50411",
              "title": "Design Patterns: Elements of Reusable Object-oriented Software",
              "authors": [
                {
                  "id": "c8e26318-2f5b-4fa2-9fdc-6e99be021fca",
                  "firstName": "The",
                  "lastName": "GoF",
                  "items": []
                }
              ],
              "price": 0,
              "published": null,
              "pages": null,
              "isbn": null,
              "binding": "Hardback"
            }
        */

    }

    @Test
    public void whenDeserializingUsingJsonProperty_thenCorrect() throws IOException {

        // arrange
        String result = "{\"id\":\"cd941587-d1ae-4c2a-9a36-29533bf50411\",\"title\":\"Design Patterns: Elements of Reusable Object-oriented Software\",\"authors\":[{\"id\":\"c8e26318-2f5b-4fa2-9fdc-6e99be021fca\",\"firstName\":\"The\",\"lastName\":\"GoF\"}],\"binding\":\"Hardback\"}";

        // act
        Book book = new ObjectMapper().readerFor(Book.class)
            .readValue(result);

        // assert
        assertThat(book.coverBinding()).isEqualTo("Hardback");

    }

}