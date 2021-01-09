package com.baeldung.webclient.json;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class UserConsumerServiceImplUnitTest {

    private static String USER_JSON = "[{\"id\":1,\"name\":\"user1\",\"addressList\":[{\"addressLine1\":\"address1_addressLine1\",\"addressLine2\":\"address1_addressLine2\",\"town\":\"address1_town\",\"postCode\":\"user1_address1_postCode\"}," +
      "{\"addressLine1\":\"address2_addressLine1\",\"addressLine2\":\"address2_addressLine2\",\"town\":\"address2_town\",\"postCode\":\"user1_address2_postCode\"}]}," +
      "{\"id\":2,\"name\":\"user2\",\"addressList\":[{\"addressLine1\":\"address1_addressLine1\",\"addressLine2\":\"address1_addressLine2\",\"town\":\"address1_town\",\"postCode\":\"user2_address1_postCode\"}]}]";

    private static String BASE_URL = "http://localhost:8080";

    WebClient webClientMock = WebClient.builder()
      .exchangeFunction(clientRequest -> Mono.just(ClientResponse.create(HttpStatus.OK)
        .header("content-type", "application/json")
        .body(USER_JSON)
        .build()))
      .build();

    private final UserConsumerService tested = new UserConsumerServiceImpl(webClientMock);

    @Test
    void when_processUserDataFromObjectArray_then_OK() {
        List<String> expected = Arrays.asList("user1", "user2");
        List<String> actual = tested.processUserDataFromObjectArray();
        assertThat(actual, contains(expected.get(0), expected.get(1)));
    }

    @Test
    void when_processUserDataFromUserArray_then_OK() {
        List<String> expected = Arrays.asList("user1", "user2");
        List<String> actual = tested.processUserDataFromUserArray();
        assertThat(actual, contains(expected.get(0), expected.get(1)));
    }

    @Test
    void when_processUserDataFromUserList_then_OK() {
        List<String> expected = Arrays.asList("user1", "user2");
        List<String> actual = tested.processUserDataFromUserList();
        assertThat(actual, contains(expected.get(0), expected.get(1)));
    }

    @Test
    void when_processNestedUserDataFromUserArray_then_OK() {
        List<String> expected = Arrays.asList(
          "user1_address1_postCode",
          "user1_address2_postCode",
          "user2_address1_postCode");

        List<String> actual = tested.processNestedUserDataFromUserArray();
        assertThat(actual, contains(expected.get(0), expected.get(1), expected.get(2)));
    }

    @Test
    void when_processNestedUserDataFromUserList_then_OK() {
        List<String> expected = Arrays.asList(
          "user1_address1_postCode",
          "user1_address2_postCode",
          "user2_address1_postCode");

        List<String> actual = tested.processNestedUserDataFromUserList();
        assertThat(actual, contains(expected.get(0), expected.get(1), expected.get(2)));
    }
}