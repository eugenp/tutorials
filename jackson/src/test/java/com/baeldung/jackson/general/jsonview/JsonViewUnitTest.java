package com.baeldung.jackson.general.jsonview;

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
public class JsonViewUnitTest {

    @Test
    public void whenSerializingUsingJsonView_andInternalView_thenCorrect() throws JsonProcessingException {

        // arrange
        Order order = new Order(120);

        // act
        String result = new ObjectMapper().writerWithView(Views.Internal.class)
            .writeValueAsString(order);

        // assert
        assertThat(from(result).getUUID("id")).isNotNull();
        assertThat(from(result).getObject("type", Order.Type.class)).isNotNull();
        assertThat(from(result).getInt("internalAudit")).isEqualTo(120);

        /*
            {
              "id": "33806388-795b-4812-b90a-60292111bc5c",
              "type": {
                "id": 20,
                "name": "Order"
              },
              "internalAudit": 120
            }
        */

    }

    @Test
    public void whenSerializingUsingJsonView_andPublicView_thenCorrect() throws JsonProcessingException {

        // arrange
        Order order = new Order(120);

        // act
        String result = new ObjectMapper().writerWithView(Views.Public.class)
            .writeValueAsString(order);

        // assert
        assertThat(from(result).getUUID("id")).isNotNull();
        assertThat(from(result).getObject("type", Order.Type.class)).isNotNull();
        assertThat(result).doesNotContain("internalAudit");

        /*
            {
              "id": "5184d5fc-e359-4cdf-93fa-4054025bef4e",
              "type": {
                "id": 20,
                "name": "Order"
              }
            }
        */

    }

}