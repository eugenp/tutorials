package com.baeldung.cloud.openfeign.patcherror.withokhttpclient;

import com.baeldung.cloud.openfeign.ExampleApplication;
import com.baeldung.cloud.openfeign.patcherror.withokhttpclient.client.UserClient;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ExampleApplication.class)
@TestPropertySource(locations = "/test2.properties")
public class UserClientUnitTest {

    public static final String USER_ID = "100001";

    public static final String EXISTING_USER = "{\n" +
            "    \"userId\": 100001,\n" +
            "    \"userName\": \"name\",\n" +
            "    \"email\": \"email@mail.in\"\n" +
            "}";

    @Autowired
    private UserClient userClient;

    private WireMockServer wireMockServer;

    @BeforeEach
    public void startWireMockServer() {
        wireMockServer = new WireMockServer(8082);
        configureFor("localhost", 8082);
        wireMockServer.start();
    }

    @BeforeEach
    public void mockGetUserApi() {
        stubFor(get(urlEqualTo("/api/user/".concat(USER_ID)))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(EXISTING_USER)));
    }

    @AfterEach
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    void givenUserExistsAndIsValid_whenUpdateUserCalled_thenReturnSuccess() {
        User user = userClient.getUser(USER_ID);
        user.setEmail("updated-email@mail.in");

        String updatedUserResponse = "{\n" +
                "    \"userId\": 100001,\n" +
                "    \"userName\": \"updated-name\",\n" +
                "    \"email\": \"updated-email@mail.in\"\n" +
                "}";

        stubFor(patch(urlEqualTo("/api/user/".concat(USER_ID)))
            .willReturn(aResponse().withStatus(HttpStatus.OK.value())
            .withHeader("Content-Type", "application/json")
            .withBody(updatedUserResponse)));

        User updatedUser = userClient.updateUser(USER_ID, user);

        assertEquals(user.getUserId(), updatedUser.getUserId());
        assertEquals("updated-name", updatedUser.getUserName());
        assertEquals("updated-email@mail.in", updatedUser.getEmail());
    }

    @Test
    void givenUserNotFound_whenUpdateUserCalled_thenReturnNotFoundErrorAndFeignException() {
        stubFor(patch(urlEqualTo("/api/user/".concat(USER_ID)))
                .willReturn(aResponse().withStatus(404)));

        assertThrows(FeignException.class, () -> userClient.updateUser(USER_ID,
            userClient.getUser(USER_ID)));
    }
}
