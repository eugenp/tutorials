package com.baeldung.execution.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

public class SlowServiceResponseIntegrationTest {

    public static final String RESOURCE_URL = "http://localhost:8080/doSomething";

    private RestTemplate restTemplate;
    private MockRestServiceServer serviceServer;

    @BeforeEach
    public void setup() {

        restTemplate = new RestTemplate();
        serviceServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenGetThenSuccessAfterFiveSeconds() throws URISyntaxException {

        serviceServer.expect(ExpectedCount.once(),
          requestTo(RESOURCE_URL))
          .andExpect(method(HttpMethod.GET))
          .andRespond(request -> {
              try {
                  //do some processing
                  Thread.sleep(5000);
              } catch (InterruptedException e) {
              }

              return new MockClientHttpResponse("sucess".getBytes(), HttpStatus.OK);
          });

        ResponseEntity<String> entity = restTemplate.getForEntity(new URI(RESOURCE_URL), String.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());


    }
}
