package com.baeldung.jsonjava;

import org.json.HTTP;
import org.json.JSONObject;
import org.junit.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

public class HTTPIntegrationTest {
    @Test
    public void givenJSONObject_thenConvertToHTTPHeader() {
        JSONObject jo = new JSONObject();
        jo.put("Method", "POST");
        jo.put("Request-URI", "http://www.example.com/");
        jo.put("HTTP-Version", "HTTP/1.1");

        assertThat(HTTP.toString(jo))
            .isEqualTo("POST \"http://www.example.com/\" HTTP/1.1" + HTTP.CRLF + HTTP.CRLF);
    }

    @Test
    public void givenHTTPHeader_thenConvertToJSONObject() {
        JSONObject obj = HTTP.toJSONObject("POST \"http://www.example.com/\" HTTP/1.1");

        assertThatJson(obj)
          .isEqualTo("{\"Request-URI\":\"http://www.example.com/\",\"Method\":\"POST\",\"HTTP-Version\":\"HTTP/1.1\"}");
    }
}
