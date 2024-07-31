package com.baeldung.jacksonannotation.polymorphism;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class PolymorphismUnitTest {

    @Test
    public void whenSerializingUsingPolymorphism_thenCorrect() throws JsonProcessingException {

        // arrange
        Order.InternalType internalType = new Order.InternalType();
        internalType.id = 250;
        internalType.name = "staff";

        Order order = new Order(internalType);

        // act
        String result = new ObjectMapper().writeValueAsString(order);

        // assert
        assertThat(from(result).getString("type.ordertype")).isEqualTo("internal");

        /*
            {
              "id": "7fc898e3-b4e7-41b0-8ffa-664cf3663f2e",
              "type": {
                "ordertype": "internal",
                "id": 250,
                "name": "staff"
              }
            }
        */

    }

    @Test
    public void whenDeserializingPolymorphic_thenCorrect() throws IOException {

        // arrange
        String orderJson = "{\"type\":{\"ordertype\":\"internal\",\"id\":100,\"name\":\"directors\"}}";

        // act
        Order order = new ObjectMapper().readerFor(Order.class).readValue(orderJson);

         // assert
        assertThat(from(orderJson).getString("type.ordertype")).isEqualTo("internal");
        assertThat(((Order.InternalType) order.getType()).name).isEqualTo("directors");
        assertThat(((Order.InternalType) order.getType()).id).isEqualTo(100);
        assertThat(order.getType().getClass()).isEqualTo(Order.InternalType.class);
    }
}