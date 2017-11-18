package com.baeldung.jacksonannotation.serialization.jsonanygetter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.util.Map;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class JsonAnyGetterUnitTest {

    @Test
    public void whenSerializingUsingJsonAnyGetter_thenCorrect() throws JsonProcessingException {

        // arrange
        Inventory inventory = new Inventory();
        Map<String, Float> countryDeliveryCost = inventory.getCountryDeliveryCost();
        inventory.setLocation("France");

        countryDeliveryCost.put("USA", 10.00f);
        countryDeliveryCost.put("UK", 15.00f);

        // act
        String result = new ObjectMapper().writeValueAsString(inventory);

        // assert
        assertThat(from(result).getString("location")).isEqualTo("France");
        assertThat(from(result).getFloat("USA")).isEqualTo(10.00f);
        assertThat(from(result).getFloat("UK")).isEqualTo(15.00f);

        /*
            {
              "location": "France",
              "USA": 10,
              "UK": 15
            }
        */

    }

}
