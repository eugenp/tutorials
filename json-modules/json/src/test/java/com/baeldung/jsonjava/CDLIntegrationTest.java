package com.baeldung.jsonjava;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.junit.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

public class CDLIntegrationTest {

    @Test
    public void givenCommaDelimitedText_thenConvertToJSONArray() {
        JSONArray ja = CDL.rowToJSONArray(new JSONTokener("England, USA, Canada"));

        assertThatJson(ja)
          .isEqualTo("[\"England\",\"USA\",\"Canada\"]");
    }

    @Test
    public void givenJSONArray_thenConvertToCommaDelimitedText() {
        JSONArray ja = new JSONArray("[\"England\",\"USA\",\"Canada\"]");

        String cdt = CDL.rowToString(ja);

        assertThat(cdt.trim()).isEqualTo("England,USA,Canada");
    }

    @Test
    public void givenCommaDelimitedText_thenGetJSONArrayOfJSONObjects() {
        String string =
          "name, city, age \n" +
          "john, chicago, 22 \n" +
          "gary, florida, 35 \n" +
          "sal, vegas, 18";

        JSONArray result = CDL.toJSONArray(string);

        assertThatJson(result)
          .isEqualTo("[{\"name\":\"john\",\"city\":\"chicago\",\"age\":\"22\"},{\"name\":\"gary\",\"city\":\"florida\",\"age\":\"35\"},{\"name\":\"sal\",\"city\":\"vegas\",\"age\":\"18\"}]");
    }

    @Test
    public void givenCommaDelimitedText_thenGetJSONArrayOfJSONObjects2() {
        JSONArray ja = new JSONArray();
        ja.put("name");
        ja.put("city");
        ja.put("age");

        String string =
          "john, chicago, 22 \n" +
          "gary, florida, 35 \n" +
          "sal, vegas, 18";

        JSONArray result = CDL.toJSONArray(ja, string);

        assertThatJson(result)
          .isEqualTo("[{\"name\":\"john\",\"city\":\"chicago\",\"age\":\"22\"},{\"name\":\"gary\",\"city\":\"florida\",\"age\":\"35\"},{\"name\":\"sal\",\"city\":\"vegas\",\"age\":\"18\"}]");
    }

}
