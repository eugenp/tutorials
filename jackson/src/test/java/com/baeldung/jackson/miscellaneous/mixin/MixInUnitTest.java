package com.baeldung.jackson.miscellaneous.mixin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.List;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class MixInUnitTest {

    @Test
    public void whenSerializingUsingMixIn_thenCorrect() throws JsonProcessingException {

        // arrange
        Author author = new Author("Alex", "Theedom");

        // act
        String result = new ObjectMapper().writeValueAsString(author);

        // assert
        assertThat(from(result).getList("items")).isNotNull();

        /*
            {
              "id": "f848b076-00a4-444a-a50b-328595dd9bf5",
              "firstName": "Alex",
              "lastName": "Theedom",
              "items": []
            }
        */

        ObjectMapper mapper = new ObjectMapper();
        mapper.addMixIn(List.class, IgnoreListMixIn.class);

        result = mapper.writeValueAsString(author);

        // assert
        assertThat(from(result).getList("items")).isNull();

        /*
            {
              "id": "9ffefb7d-e56f-447c-9009-e92e142f8347",
              "firstName": "Alex",
              "lastName": "Theedom"
            }
        */

    }
}