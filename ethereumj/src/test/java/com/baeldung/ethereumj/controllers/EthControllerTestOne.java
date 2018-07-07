package com.baeldung.ethereumj.controllers;

import com.baeldung.ethereumj.ApplicationMain;
import com.baeldung.ethereumj.Constants;
import com.baeldung.ethereumj.transfer.EthResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationMain.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8080")
public class EthControllerTestOne {

    @LocalServerPort
    int port;

    private RestTemplate restTemplate = new RestTemplate();

    private String url(String uri) {
        String s = "http://localhost:" + port + uri;
        System.out.println(s);
        return s;
    }

    @Before
    public void setup() {
        restTemplate = new RestTemplate();
    }

    @Test()
    public void bestBlockTest() throws Exception {

        Thread.sleep(20000);

        EthResponse a = restTemplate.getForObject(url(Constants.ENDPOINT_ONE), EthResponse.class);
        assertNotNull(a);

        ResponseEntity<EthResponse> b = restTemplate.exchange(
                url(Constants.ENDPOINT_ONE),
                HttpMethod.GET, new HttpEntity<EthResponse>(null, new HttpHeaders()), EthResponse.class);

        assertTrue("Status 200?", b.getStatusCode().equals(HttpStatus.OK));
        System.out.println("Status 200?: " + b.getStatusCode().equals(HttpStatus.OK));
        assertTrue("Dynamic data returned?", b.hasBody());
        System.out.println("Dynamic data returned?: " + b.hasBody());
    }

    @Test()
    public void difficultyTest() throws Exception {

        Thread.sleep(20000);

        ResponseEntity<EthResponse> a = restTemplate.exchange(
                url(Constants.ENDPOINT_TWO),
                HttpMethod.GET, new HttpEntity<EthResponse>(null, new HttpHeaders()), EthResponse.class);

        assertTrue("Status 200?", a.getStatusCode().equals(HttpStatus.OK));
        System.out.println("Status 200?: " + a.getStatusCode().equals(HttpStatus.OK));
        assertTrue("Dynamic data returned?", a.hasBody());
        System.out.println("Dynamic data returned?: " + a.hasBody());
    }
}
