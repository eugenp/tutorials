package com.baeldung.jacksonannotation.general.jsonunwrapped;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class JsonUnwrappedUnitTest {

    @Test
    public void whenSerializingUsingJsonUnwrapped_thenCorrect() throws JsonProcessingException {

        // arrange
        Order.Type preorderType = new Order.Type();
        preorderType.id = 10;
        preorderType.name = "pre-order";

        Order order = new Order(preorderType);

        // act
        String result = new ObjectMapper().writeValueAsString(order);

        // assert
        assertThat(from(result).getInt("id")).isEqualTo(10);
        assertThat(from(result).getString("name")).isEqualTo("pre-order");

        /*
            {
              "id": 10,
              "name": "pre-order"
            }
        */

    }
}