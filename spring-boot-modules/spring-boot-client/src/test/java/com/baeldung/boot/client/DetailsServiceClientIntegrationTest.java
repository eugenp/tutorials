package com.baeldung.boot.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.baeldung.boot.Application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import tools.jackson.databind.ObjectMapper;

@RestClientTest({ DetailsServiceClient.class, Application.class })
public class DetailsServiceClientIntegrationTest {

    @Autowired
    private DetailsServiceClient client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
        String detailsString = objectMapper.writeValueAsString(new Details("John Smith", "john"));
        this.server.expect(requestTo("/john/details"))
            .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenCallingGetUserDetails_thenClientExecutesCorrectCall() throws Exception {

        Details details = this.client.getUserDetails("john");

        assertThat(details.getLogin()).isEqualTo("john");
        assertThat(details.getName()).isEqualTo("John Smith");

    }

}
