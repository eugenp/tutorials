package com.baeldung.jsonjava;

import static org.junit.Assert.assertEquals;
import org.json.HTTP;
import org.json.JSONObject;
import org.junit.Test;

public class HTTPIntegrationTest {
    @Test
    public void givenJSONObject_thenConvertToHTTPHeader() {
        JSONObject jo = new JSONObject();
        jo.put("Method", "POST");
        jo.put("Request-URI", "http://www.example.com/");
        jo.put("HTTP-Version", "HTTP/1.1");
        
        assertEquals("POST \"http://www.example.com/\" HTTP/1.1"+HTTP.CRLF+HTTP.CRLF, HTTP.toString(jo));
    }

    @Test
    public void givenHTTPHeader_thenConvertToJSONObject() {
        JSONObject obj = HTTP.toJSONObject("POST \"http://www.example.com/\" HTTP/1.1");
        
        assertEquals("{\"Request-URI\":\"http://www.example.com/\",\"Method\":\"POST\",\"HTTP-Version\":\"HTTP/1.1\"}", obj.toString());
    }
}
