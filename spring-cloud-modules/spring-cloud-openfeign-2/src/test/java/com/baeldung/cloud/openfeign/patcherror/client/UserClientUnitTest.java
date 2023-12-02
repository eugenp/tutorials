package com.baeldung.cloud.openfeign.patcherror.client;

import com.baeldung.cloud.openfeign.ExampleApplication;
import com.baeldung.cloud.openfeign.patcherror.model.User;

import com.github.tomakehurst.wiremock.WireMockServer;
import feign.FeignException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ExampleApplication.class)
public class UserClientUnitTest {

    @Autowired
    private UserClient userClient;

    private WireMockServer wireMockServer;

    @BeforeEach
    public void startWireMockServer() {
        wireMockServer = new WireMockServer(8082);
        configureFor("localhost", 8082);
        wireMockServer.start();
    }

    @AfterEach
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    void givenUserExistsAndIsValid_whenUpdateUserCalled_thenReturnSuccess() {
        String updatedUserResponse = "{\n" +
                "    \"userId\": 100001,\n" +
                "    \"userName\": \"name\",\n" +
                "    \"email\": \"updated-email@mail.in\"\n" +
                "}";

        stubFor(patch(urlEqualTo("/api/user/".concat("100001")))
          .willReturn(aResponse().withStatus(HttpStatus.OK.value())
          .withHeader("Content-Type", "application/json")
          .withBody(updatedUserResponse)));

        User user = new User();
        user.setUserId("100001");
        user.setEmail("updated-email@mail.in");
        User updatedUser = userClient.updateUser("100001", user);

        assertEquals(user.getUserId(), updatedUser.getUserId());
        assertEquals(user.getEmail(), updatedUser.getEmail());
    }

    @Test
    void givenUserNotFound_whenUpdateUserCalled_thenReturnNotFoundErrorAndFeignException() {
        User user = new User();
        user.setUserId("100002");
        user.setEmail("updated-email@mail.in");

        stubFor(patch(urlEqualTo("/api/user/".concat("100002")))
          .willReturn(aResponse().withStatus(404)));

        assertThrows(FeignException.class, () -> userClient.updateUser("100002", user));
    }
}
