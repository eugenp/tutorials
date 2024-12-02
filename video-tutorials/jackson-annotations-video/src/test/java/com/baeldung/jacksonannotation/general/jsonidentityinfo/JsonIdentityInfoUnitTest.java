package com.baeldung.jacksonannotation.general.jsonidentityinfo;

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
public class JsonIdentityInfoUnitTest {

    @Test
    public void whenSerializingUsingJsonIdentityInfo_thenCorrect() throws JsonProcessingException {

        // arrange
        Author author = new Author("Alex", "Theedom");

        Course course = new Course("Java EE Introduction", author);
        author.setItems(Collections.singletonList(course));
        course.setAuthors(Collections.singletonList(author));

        // act
        String result = new ObjectMapper().writeValueAsString(author);

        // assert
        assertThat(from(result).getString("items[0].authors")).isNotNull();

        /*
        Authors are included.
            {
              "id": "1b408bf9-5946-4a14-a112-fde2953a7fe7",
              "firstName": "Alex",
              "lastName": "Theedom",
              "items": [
                {
                  "id": "5ed30530-f0a5-42eb-b786-be2c655da968",
                  "title": "Java EE Introduction",
                  "authors": [
                    "1b408bf9-5946-4a14-a112-fde2953a7fe7"
                  ],
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