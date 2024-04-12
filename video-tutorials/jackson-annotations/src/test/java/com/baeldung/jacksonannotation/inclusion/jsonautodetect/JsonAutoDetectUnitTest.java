package com.baeldung.jacksonannotation.inclusion.jsonautodetect;

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
public class JsonAutoDetectUnitTest {

    @Test
    public void whenSerializingUsingJsonAutoDetect_thenCorrect() throws JsonProcessingException {

        // arrange
        Order order = new Order(1234567890);

        // act
        String result = new ObjectMapper().writeValueAsString(order);

        // assert
        assertThat(from(result).getInt("internalAudit")).isEqualTo(1234567890);

        /*
        With @JsonAutoDetect
            {
              "id": "c94774d9-de8f-4244-85d5-624bd3a4567a",
              "type": {
                "id": 20,
                "name": "Order"
              },
              "internalAudit": 1234567890
            }

         Without @JsonAutoDetect
            {
              "id": "c94774d9-de8f-4244-85d5-624bd3a4567a",
              "type": {
                "id": 20,
                "name": "Order"
              }
            }
        */

    }
}