package com.baeldung.jackson.miscellaneous.disable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
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
public class DisableUnitTest {

    @Test
    public void whenSerializingUsingDisable_thenCorrect() throws JsonProcessingException {

        // arrange
        Author author = new Author("Alex", "Theedom");

        // act
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(author);

        // assert
        assertThat(from(result).getList("items")).isNull();

        /*
            {
              "lastName": "Theedom",
              "firstName": "Alex",
              "id": "de4afbb4-b24d-45c8-bb00-fd6b9acb42f1"
            }
        */

        // act
        mapper = new ObjectMapper();
        mapper.disable(MapperFeature.USE_ANNOTATIONS);
        result = mapper.writeValueAsString(author);

        // assert
        assertThat(from(result).getList("items")).isNotNull();

        /*
            {
              "id": "81e6ed72-6b27-4fe9-a36f-e3171c5b55ef",
              "firstName": "Alex",
              "lastName": "Theedom",
              "items": []
            }
        */

    }
}