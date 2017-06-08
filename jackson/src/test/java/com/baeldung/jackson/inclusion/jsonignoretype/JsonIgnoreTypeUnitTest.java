package com.baeldung.jackson.inclusion.jsonignoretype;

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
public class JsonIgnoreTypeUnitTest {

    @Test
    public void whenSerializingUsingJsonIgnoreType_thenCorrect() throws JsonProcessingException {

        // arrange
        Order.Type type = new Order.Type();
        type.id = 10;
        type.name = "Pre-order";

        Order order = new Order(type);

        // act
        String result = new ObjectMapper().writeValueAsString(order);

        // assert
        assertThat(from(result).getString("id")).isNotNull();
        assertThat(from(result).getString("type")).isNull();

        /*
            {"id":"ac2428da-523e-443c-a18a-4ea4d2791fea"}
        */

    }
}