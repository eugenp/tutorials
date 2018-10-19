package com.baeldung.jackson.general.jsonfilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.Test;

import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class JsonFilterUnitTest {

    @Test
    public void whenSerializingUsingJsonFilter_thenCorrect() throws JsonProcessingException {

        // arrange
        Author author = new Author("Alex", "Theedom");
        FilterProvider filters = new SimpleFilterProvider().addFilter("authorFilter", SimpleBeanPropertyFilter.filterOutAllExcept("lastName"));

        // act
        String result = new ObjectMapper().writer(filters)
            .writeValueAsString(author);

        // assert
        assertThat(from(result).getList("items")).isNull();

        /*
            {
              "lastName": "Theedom"
            }
        */

    }
}