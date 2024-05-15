package com.baeldung.bufferedreadertojsonobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

public class JavaBufferedReaderToJSONObjectUnitTest {

    @Test
    public void givenValidJson_whenUsingBufferedReader_thenJSONTokenerConverts() {
        byte[] b = "{ \"name\" : \"John\", \"age\" : 18 }".getBytes(StandardCharsets.UTF_8);
        InputStream is = new ByteArrayInputStream(b);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        JSONTokener tokener = new JSONTokener(bufferedReader);
        JSONObject json = new JSONObject(tokener);

        assertNotNull(json);
        assertEquals("John", json.get("name"));
        assertEquals(18, json.get("age"));
    }

    @Test
    public void givenValidJson_whenUsingString_thenJSONObjectConverts() throws IOException {
        byte[] b = "{ \"name\" : \"John\", \"age\" : 18 }".getBytes(StandardCharsets.UTF_8);
        InputStream is = new ByteArrayInputStream(b);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        JSONObject json = new JSONObject(sb.toString());

        assertNotNull(json);
        assertEquals("John", json.get("name"));
        assertEquals(18, json.get("age"));
    }
}
