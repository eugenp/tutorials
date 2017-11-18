package com.baeldung.jackson.serialization.jsonrawvalue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
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
              "firstName": "Alex",
              "lastName": "Theedom",
              "publications": []
            }
        */

    }
}
