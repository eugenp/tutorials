package com.baeldung.jackson.deserialization.jsonanysetter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class JsonAnySetterUnitTest {

    @Test
    public void whenDeserializingUsingJsonAnySetter_thenCorrect() throws IOException {

        // arrange
        String json = "{\"USA\":10.00,\"UK\":15.00,\"China\":23.00,\"Brazil\":12.00,\"France\":8.00,\"Russia\":18.00}";

        // act
        Inventory inventory = new ObjectMapper().readerFor(Inventory.class)
            .readValue(json);

        // assert
        assertThat(from(json).getMap(".")
            .get("USA")).isEqualTo(inventory.getCountryDeliveryCost()
                .get("USA"));
        assertThat(from(json).getMap(".")
            .get("UK")).isEqualTo(inventory.getCountryDeliveryCost()
                .get("UK"));
        assertThat(from(json).getMap(".")
            .get("China")).isEqualTo(inventory.getCountryDeliveryCost()
                .get("China"));
        assertThat(from(json).getMap(".")
            .get("Brazil")).isEqualTo(inventory.getCountryDeliveryCost()
                .get("Brazil"));
        assertThat(from(json).getMap(".")
            .get("France")).isEqualTo(inventory.getCountryDeliveryCost()
                .get("France"));
        assertThat(from(json).getMap(".")
            .get("Russia")).isEqualTo(inventory.getCountryDeliveryCost()
                .get("Russia"));

    }
}