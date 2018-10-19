package com.baeldung.jackson.serialization.jsonserialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class JsonSerializeUnitTest {

    @Test
    public void whenSerializingUsingJsonSerialize_thenCorrect() throws JsonProcessingException, ParseException {

        // arrange
        Author joshuaBloch = new Author("Joshua", "Bloch");
        Book book = new Book("Effective Java", joshuaBloch);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String toParse = "25-12-2017 13:30:25";
        book.setPublished(df.parse(toParse));

        // act
        String result = new ObjectMapper().writeValueAsString(book);

        // assert
        assertThat(from(result).getString("published")).isEqualTo(toParse);

        /*
            {
              "id": "957c43f2-fa2e-42f9-bf75-6e3d5bb6960a",
              "title": "Effective Java",
              "authors": [
                {
                  "id": "9bcd817d-0141-42e6-8f04-e5aaab0980b6",
                  "firstName": "Joshua",
                  "lastName": "Bloch",
                  "items": []
                }
              ],
              "price": 0,
              "published": "25-12-2017 13:30:25",
              "pages": null,
              "isbn": null
            }
        */

    }
}
