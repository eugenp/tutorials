package com.baeldung.jackson.miscellaneous.custom;

import com.baeldung.jackson.domain.Author;
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
public class CustomUnitTest {

    @Test
    public void whenSerializingUsingCustom_thenCorrect() throws JsonProcessingException {

        // arrange
        Course course = new Course("Spring Security", new Author("Eugen", "Paraschiv"));
        course.setMedium(Course.Medium.ONLINE);

        // act
        String result = new ObjectMapper().writeValueAsString(course);

        // assert
        assertThat(from(result).getString("title")).isEqualTo("Spring Security");

        /*
            {
              "title": "Spring Security",
              "price": 0,
              "id": "7dfd4db9-1175-432f-a53b-687423f7bb9b",
              "duration": 0,
              "authors": [
                {
                  "id": "da0738f6-033c-4974-8d87-92820e5ccf27",
                  "firstName": "Eugen",
                  "lastName": "Paraschiv",
                  "items": []
                }
              ],
              "medium": "ONLINE"
            }
        */

    }
}