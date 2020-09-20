package com.baeldung.resttemplate.json.consumer.controller;

import com.baeldung.resttemplate.json.model.Address;
import com.baeldung.resttemplate.json.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserConsumerControllerUnitTest {
    private MockRestServiceServer mockServer;
    private final RestTemplate restTemplate = new RestTemplate();


    private UserConsumerController tested = new UserConsumerController(restTemplate);

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenGetUsersAsObjects_thenOK() {
        String url = "http://localhost :8080/users";

        List<User> expected = Arrays.asList(
                new User(1, "user1", new ArrayList<Address>(
                        Arrays.asList(
                                new Address("address1_addressLine1", "address1_addressLine2", "address1_town", "address1_postCode"),
                                new Address("address2_addressLine1", "address2_addressLine2", "address2_town", "address2_postCode")))),
                new User(2,
                        "user2", new ArrayList<Address>(
                        Arrays.asList(
                                new Address("address1_addressLine1", "address1_addressLine2", "address1_town", "address1_postCode")))));

        String userJson = "[{\"id\":1,\"name\":\"user1\",\"addressList\":[{\"addressLine1\":\"address1_addressLine1\",\"addressLine2\":\"address1_addressLine2\",\"town\":\"address1_town\",\"postCode\":\"address1_postCode\"}," +
                                  "{\"addressLine1\":\"address2_addressLine1\",\"addressLine2\":\"address2_addressLine2\",\"town\":\"address2_town\",\"postCode\":\"address2_postCode\"}]}," +
                                  "{\"id\":2,\"name\":\"user2\",\"addressList\":[{\"addressLine1\":\"address1_addressLine1\",\"addressLine2\":\"address1_addressLine2\",\"town\":\"address1_town\",\"postCode\":\"address1_postCode\"}]}]";

        mockServer.expect(ExpectedCount.once(),
                requestTo("http://localhost:8080/users"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(userJson)
                );

        //when
        List<User> actual = tested.getUsersAsPOJO();
        mockServer.verify();
        assertEquals(actual.size(), expected.size());
        assertEquals(actual.get(0).getName(), expected.get(0).getName());

    }

}