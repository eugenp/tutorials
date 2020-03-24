package com.baeldung.jsonjava;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class JSONArrayGetValueByKeyUnitTest {

    private static final JSONArrayGetValueByKey obj = new JSONArrayGetValueByKey();

    @Test
    public void givenJSONArrayAndAKey_thenReturnAllValuesForGivenKey() {
        String jsonStr = "[" + " {" + " \"name\": \"John\"," + " \"city\": \"chicago\"," + " \"age\": \"22\" " + "}," + " { " + "\"name\": \"Gary\"," + " \"city\": \"florida\"," + " \"age\": \"35\" " + "}," + " { " + "\"name\": \"Selena\","
            + " \"city\": \"vegas\"," + " \"age\": \"18\" " + "} " + "]";

        List<String> actualValues = obj.getValuesByKeyInJSONArray(jsonStr, "name");

        assertThat(actualValues, equalTo(Arrays.asList("John", "Gary", "Selena")));
    }

    @Test
    public void givenJSONArrayAndAKey_whenUsingJava8Syntax_thenReturnAllValuesForGivenKey() {
        String jsonStr = "[" + " {" + " \"name\": \"John\"," + " \"city\": \"chicago\"," + " \"age\": \"22\" " + "}," + " { " + "\"name\": \"Gary\"," + " \"city\": \"florida\"," + " \"age\": \"35\" " + "}," + " { " + "\"name\": \"Selena\","
            + " \"city\": \"vegas\"," + " \"age\": \"18\" " + "} " + "]";

        List<String> actualValues = obj.getValuesByKeyInJSONArrayUsingJava8(jsonStr, "name");

        assertThat(actualValues, equalTo(Arrays.asList("John", "Gary", "Selena")));
    }

}
