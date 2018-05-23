package org.baeldung.web.handler;

import org.baeldung.web.exception.NotFoundException;
import org.baeldung.web.model.Bar;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { NotFoundException.class, Bar.class })
@RestClientTest
public class RestTemplateResponseErrorHandlerIntegrationTest {

    @Autowired private MockRestServiceServer server;
    @Autowired private RestTemplateBuilder builder;

    @Test(expected = NotFoundException.class)
    public void givenRemoteApiCall_when404Error_thenThrowNotFound() {
        Assert.assertNotNull(this.builder);
        Assert.assertNotNull(this.server);

        RestTemplate restTemplate = this.builder
          .errorHandler(new RestTemplateResponseErrorHandler())
          .build();

        this.server
          .expect(ExpectedCount.once(), requestTo("/bars/4242"))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withStatus(HttpStatus.NOT_FOUND));

        Bar response = restTemplate.getForObject("/bars/4242", Bar.class);
        this.server.verify();
    }
}