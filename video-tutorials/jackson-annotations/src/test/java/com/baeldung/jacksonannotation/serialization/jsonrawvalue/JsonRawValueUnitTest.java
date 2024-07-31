package com.baeldung.jacksonannotation.serialization.jsonrawvalue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class JsonRawValueUnitTest {

    @Test
    public void whenSerializingUsingJsonRawValue_thenCorrect() throws JsonProcessingException {

        // arrange
        String customerConfig = "{\"colour\":\"red\",\"device\":\"mobile\",\"orientation\":\"landscape\"}";
        Customer customer = new Customer("Alex", "Theedom");
        customer.setConfiguration("{\"colour\":\"red\",\"device\":\"mobile\",\"orientation\":\"landscape\"}");

        // act
        String result = new ObjectMapper().writeValueAsString(customer);

        // assert
        assertThat(result.contains(customerConfig));


        /*
            {
              "id": "7674fbec-527f-4008-a619-f9967cd0cbe0",
              "firstName": "Alex",
              "lastName": "Theedom",
              "configuration": {
                "colour": "red",
                "device": "mobile",
                "orientation": "landscape"
              }
            }
        */

    }
}
