package com.baeldung.web.handler;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.baeldung.resttemplate.web.exception.NotFoundException;
import com.baeldung.resttemplate.web.handler.RestTemplateResponseErrorHandler;
import com.baeldung.resttemplate.web.model.Bar;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { NotFoundException.class, Bar.class })
@RestClientTest
public class RestTemplateResponseErrorHandlerIntegrationTest {

    @Autowired private MockRestServiceServer server;
    @Autowired private RestTemplateBuilder builder;

    @Test
    public void givenRemoteApiCall_when404Error_thenThrowNotFound() {
        Assertions.assertNotNull(this.builder);
        Assertions.assertNotNull(this.server);

        RestTemplate restTemplate = this.builder
          .errorHandler(new RestTemplateResponseErrorHandler())
          .build();

        this.server
          .expect(ExpectedCount.once(), requestTo("/bars/4242"))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withStatus(HttpStatus.NOT_FOUND));
        
        Assertions.assertThrows(NotFoundException.class, () -> {
        	Bar response = restTemplate.getForObject("/bars/4242", Bar.class);
        });
    }
}