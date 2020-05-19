package com.baeldung.springcloudgateway.custompredicates;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

/**
 * This test requires
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("customroutes")
public class CustomPredicatesApplicationLiveTest {

    @LocalServerPort
    String serverPort;
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenNormalCustomer_whenCallHeadersApi_thenResponseForNormalCustomer() throws JSONException {

        String url = "http://localhost:" + serverPort + "/api/headers";        
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        JSONObject json = new JSONObject(response.getBody());
        JSONObject headers = json.getJSONObject("headers");
        assertThat(headers.getString("Goldencustomer")).isEqualTo("false");
        
    }

    @Test
    void givenGoldenCustomer_whenCallHeadersApi_thenResponseForGoldenCustomer() throws JSONException {

        String url = "http://localhost:" + serverPort + "/api/headers";   
        RequestEntity<Void> request = RequestEntity
            .get(URI.create(url))
            .header("Cookie", "customerId=baeldung")
            .build();
            
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        JSONObject json = new JSONObject(response.getBody());
        JSONObject headers = json.getJSONObject("headers");
        assertThat(headers.getString("Goldencustomer")).isEqualTo("true");
        
    }
    
}
