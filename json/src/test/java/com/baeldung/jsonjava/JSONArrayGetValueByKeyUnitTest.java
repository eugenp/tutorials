package com.baeldung.jsonjava;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JSONArrayGetValueByKeyUnitTest {

    private static final JSONArrayGetValueByKey obj = new JSONArrayGetValueByKey();

    @Test
    public void givenJSONArrayAndAKey_thenReturnAllValuesForGivenKey() {
        String jsonStr = "[" + " {" + " \"name\": \"John\"," + " \"city\": \"chicago\"," + " \"age\": \"22\" " + "}," + " { " + "\"name\": \"Gary\"," + " \"city\": \"florida\"," + " \"age\": \"35\" " + "}," + " { " + "\"name\": \"Selena\","
            + " \"city\": \"vegas\"," + " \"age\": \"18\" " + "} " + "]";

        List<String> actualValues = obj.getValuesByKeyInJSONArray(jsonStr, "name");

        assertThat(actualValues)
          .containsExactlyInAnyOrder("John", "Gary", "Selena");
    }

    @Test
    public void givenJSONArrayAndAKey_whenUsingJava8Syntax_thenReturnAllValuesForGivenKey() {
        String jsonStr = "[" + " {" + " \"name\": \"John\"," + " \"city\": \"chicago\"," + " \"age\": \"22\" " + "}," + " { " + "\"name\": \"Gary\"," + " \"city\": \"florida\"," + " \"age\": \"35\" " + "}," + " { " + "\"name\": \"Selena\","
            + " \"city\": \"vegas\"," + " \"age\": \"18\" " + "} " + "]";

        List<String> actualValues = obj.getValuesByKeyInJSONArrayUsingJava8(jsonStr, "name");

        assertThat(actualValues)
          .containsExactlyInAnyOrder("John", "Gary", "Selena");
    }

}
