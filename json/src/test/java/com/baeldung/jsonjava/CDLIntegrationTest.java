package com.baeldung.jsonjava;

import static org.junit.Assert.assertEquals;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.junit.Test;

public class CDLIntegrationTest {
    @Test
    public void givenCommaDelimitedText_thenConvertToJSONArray() {
        JSONArray ja = CDL.rowToJSONArray(new JSONTokener("England, USA, Canada"));
        assertEquals("[\"England\",\"USA\",\"Canada\"]", ja.toString());
    }

    @Test
    public void givenJSONArray_thenConvertToCommaDelimitedText() {
        JSONArray ja = new JSONArray("[\"England\",\"USA\",\"Canada\"]");
        String cdt = CDL.rowToString(ja);
        assertEquals("England,USA,Canada", cdt.toString().trim());
    }

    @Test
    public void givenCommaDelimitedText_thenGetJSONArrayOfJSONObjects() {
        String string = 
          "name, city, age \n" +
          "john, chicago, 22 \n" +
          "gary, florida, 35 \n" +
          "sal, vegas, 18";
         
        JSONArray result = CDL.toJSONArray(string);
        assertEquals("[{\"name\":\"john\",\"city\":\"chicago\",\"age\":\"22\"},{\"name\":\"gary\",\"city\":\"florida\",\"age\":\"35\"},{\"name\":\"sal\",\"city\":\"vegas\",\"age\":\"18\"}]", result.toString());
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
        assertEquals("[{\"name\":\"john\",\"city\":\"chicago\",\"age\":\"22\"},{\"name\":\"gary\",\"city\":\"florida\",\"age\":\"35\"},{\"name\":\"sal\",\"city\":\"vegas\",\"age\":\"18\"}]", result.toString());
    }
    
}
