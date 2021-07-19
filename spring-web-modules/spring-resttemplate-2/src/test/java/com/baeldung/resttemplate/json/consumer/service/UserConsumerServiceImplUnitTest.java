package com.baeldung.resttemplate.json.consumer.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
public class UserConsumerServiceImplUnitTest {

    private static String USER_JSON = "[{\"id\":1,\"name\":\"user1\",\"addressList\":[{\"addressLine1\":\"address1_addressLine1\",\"addressLine2\":\"address1_addressLine2\",\"town\":\"address1_town\",\"postCode\":\"user1_address1_postCode\"}," +
      "{\"addressLine1\":\"address2_addressLine1\",\"addressLine2\":\"address2_addressLine2\",\"town\":\"address2_town\",\"postCode\":\"user1_address2_postCode\"}]}," +
      "{\"id\":2,\"name\":\"user2\",\"addressList\":[{\"addressLine1\":\"address1_addressLine1\",\"addressLine2\":\"address1_addressLine2\",\"town\":\"address1_town\",\"postCode\":\"user2_address1_postCode\"}]}]";

    private MockRestServiceServer mockServer;
    private final RestTemplate restTemplate = new RestTemplate();
    private final UserConsumerService tested = new UserConsumerServiceImpl(restTemplate);

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void whenProcessUserDataAsObjects_thenOK() {
        String url = "http://localhost:8080/users";
        List<String> expected = Arrays.asList("user1", "user2");

        mockServer.expect(ExpectedCount.once(),
          requestTo(url))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(USER_JSON));
        
        List<String> actual = tested.processUserDataFromObjectArray();

        mockServer.verify();
        assertThat(actual).containsExactly(expected.get(0), expected.get(1));
    }

    @Test
    public void whenProcessUserDataAsArray_thenOK() {
        String url = "http://localhost:8080/users";
        List<String> expected = Arrays.asList("user1", "user2");

        mockServer.expect(ExpectedCount.once(),
          requestTo(url))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(USER_JSON));

        List<String> actual = tested.processUserDataFromUserArray();

        mockServer.verify();
        assertThat(actual).containsExactly(expected.get(0), expected.get(1));
    }

    @Test
    public void whenProcessUserDataAsList_thenOK() {
        String url = "http://localhost:8080/users";
        List<String> expected = Arrays.asList("user1", "user2");

        mockServer.expect(ExpectedCount.once(),
          requestTo(url))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(USER_JSON));

        List<String> actual = tested.processUserDataFromUserList();

        mockServer.verify();
        assertThat(actual).containsExactly(expected.get(0), expected.get(1));
    }


    @Test
    public void whenProcessNestedUserDataFromArray_thenOK() {
        String url = "http://localhost:8080/users";
        List<String> expected = Arrays.asList("user1_address1_postCode", "user1_address2_postCode", "user2_address1_postCode");

        mockServer.expect(ExpectedCount.once(),
          requestTo(url))
          .andExpect(method(HttpMethod.GET))
          .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(USER_JSON));

        List<String> actual = tested.processNestedUserDataFromUserArray();

        mockServer.verify();
        assertThat(actual).containsExactly(expected.get(0), expected.get(1), expected.get(2));
    }

    @Test
    public void whenProcessNestedUserDataFromList_thenOK() {
        String url = "http://localhost:8080/users";
        List<String> expected = Arrays.asList("user1_address1_postCode", "user1_address2_postCode", "user2_address1_postCode");

        mockServer.expect(ExpectedCount.once(),
                requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                   .contentType(MediaType.APPLICATION_JSON)
                   .body(USER_JSON));

        List<String> actual = tested.processNestedUserDataFromUserList();

        mockServer.verify();
        assertThat(actual).containsExactly(expected.get(0), expected.get(1), expected.get(2));
    }
}