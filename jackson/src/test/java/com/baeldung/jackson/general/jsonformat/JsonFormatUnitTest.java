package com.baeldung.jackson.general.jsonformat;

import com.baeldung.jackson.domain.Author;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class JsonFormatUnitTest {

    @Test
    public void whenSerializingUsingJsonFormat_thenCorrect() throws JsonProcessingException, ParseException {

        // arrange
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        String toParse = "20-12-2014 14:30:00";
        Date date = df.parse(toParse);

        Book book = new Book("Design Patterns: Elements of Reusable Object-oriented Software", new Author("The", "GoF"));
        book.setPublished(date);

        // act
        String result = new ObjectMapper().writeValueAsString(book);

        // assert
        assertThat(from(result).getString("published")).isEqualTo(toParse);

        /*
            {
              "id": "762b39be-fd5b-489e-8688-aeb3b9bbf019",
              "title": "Design Patterns: Elements of Reusable Object-oriented Software",
              "authors": [
                {
                  "id": "6941b780-0f54-4259-adcb-85523c8f25f4",
                  "firstName": "The",
                  "lastName": "GoF",
                  "items": []
                }
              ],
              "price": 0,
              "published": "20-12-2014 02:30:00",
              "pages": null,
              "isbn": null
            }
        */

    }
}