package com.baeldung.brtojsonobject;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.junit.Test;

import com.google.common.io.CharStreams;

public class BRtoJSONObjectUnitTest {

    @Test
    public void whenUsingCoreJava_convertBufferedReaderToJSONObject() throws Exception {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/input.txt"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
            }
            br.close();
            JSONObject json = new JSONObject(response.toString());
            assertEquals("John Doe", json.getString("name")); 
            assertEquals("39", json.getString("age"));
    }
    @Test
    public void whenUsingJava8_convertBufferedReaderToJSONObject() throws Exception {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/input.txt")); 
            String line = br.lines().collect(Collectors.joining());
            JSONObject json = new JSONObject(line); 
            assertEquals("John Doe", json.getString("name")); 
            assertEquals("39", json.getString("age"));
    }
    @Test
    public void whenUsingApacheCommons_convertBufferedReaderToJSONObject() throws Exception {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/input.txt"));
            String responseNew= IOUtils.toString(br); 
            JSONObject json = new JSONObject(responseNew.toString());
            assertEquals("John Doe", json.getString("name")); 
            assertEquals("39", json.getString("age"));
    }
    @Test
    public void whenUsingGuava_convertBufferedReaderToJSONObject() throws Exception {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/input.txt"));
            String targetString = CharStreams.toString(br); 
            JSONObject json = new JSONObject(targetString);
            assertEquals("John Doe", json.getString("name")); 
            assertEquals("39", json.getString("age"));
    }

}
