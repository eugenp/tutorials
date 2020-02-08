package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ZuulPostFilterLiveTest {

    @LocalServerPort
    private int port;
    private static final String SIMPLE_GREETING = "/api/greeting/simple";
    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void whenClientCallApi_thenLogAndReturnResponseBody() {
        String url = "http://localhost:" + port + SIMPLE_GREETING;
        String response = restTemplate.getForEntity(url, String.class).getBody();
        assertEquals(response, "Hi");
    }
}
