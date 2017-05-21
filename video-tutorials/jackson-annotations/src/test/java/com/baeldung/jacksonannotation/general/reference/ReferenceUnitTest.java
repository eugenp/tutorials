package com.baeldung.jacksonannotation.general.reference;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Collections;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class ReferenceUnitTest {

    @Test
    public void whenSerializingUsingReference_thenCorrect() throws JsonProcessingException {

        // arrange
        Author author = new Author("Alex", "Theedom");

        Course course = new Course("Java EE Introduction", author);
        author.setItems(Collections.singletonList(course));
        course.setAuthors(Collections.singletonList(author));

        // act
        String result = new ObjectMapper().writeValueAsString(author);

        // assert
        assertThat(from(result).getString("items[0].authors")).isNull();

        /*
        Without references defined it throws StackOverflowError.
        Authors excluded.

            {
              "id": "9c45d9b3-4888-4c24-8b74-65ef35627cd7",
              "firstName": "Alex",
              "lastName": "Theedom",
              "items": [
                {
                  "id": "f8309629-d178-4d67-93a4-b513ec4a7f47",
                  "title": "Java EE Introduction",
                  "price": 0,
                  "duration": 0,
                  "medium": null,
                  "level": null,
                  "prerequisite": null
                }
              ]
            }
        */

    }
}