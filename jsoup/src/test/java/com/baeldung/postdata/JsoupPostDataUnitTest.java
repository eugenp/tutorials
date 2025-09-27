package com.baeldung.postdata;

import com.baeldung.jsoup.postdata.JsoupPostData;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsoupPostDataUnitTest {
    private final JsoupPostData client = new JsoupPostData();
    @Test
    public void givenJSONData_whenUsingHttpBin_thenPostContent() throws Exception {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("Content-Type", "application/json");
        String payload = "{ \"name\": \"Joe\", \"role\": \"Tester\" }";
        String response = client.sendPost("https://httpbin.org/post", headersMap, payload);

        assertNotNull(response);
        assertTrue(response.contains("Joe"));
        assertTrue(response.contains("Tester"));
    }

    @Test
    public void givenJSONData_whenUsingReqRes_thenPostContent() throws Exception {
        String payload = "{ \"name\": \"Joe\", \"job\": \"Developer\" }";
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("Content-Type", "application/json");
        headersMap.put("x-api-key", "reqres-free-v1");
        String response = client.sendPost("https://reqres.in/api/users",headersMap, payload);

        assertNotNull(response);
        assertTrue(response.contains("Joe"));
        assertTrue(response.contains("Developer"));
        assertTrue(response.contains("id"));
        assertTrue(response.contains("createdAt"));
    }
}
