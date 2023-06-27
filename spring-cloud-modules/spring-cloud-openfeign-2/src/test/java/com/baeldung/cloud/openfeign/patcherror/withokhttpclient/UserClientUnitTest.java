package com.baeldung.cloud.openfeign.patcherror.withokhttpclient;

import com.baeldung.cloud.openfeign.ExampleApplication;
import com.baeldung.cloud.openfeign.patcherror.client.UserClient;
import com.baeldung.cloud.openfeign.patcherror.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.File;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ExampleApplication.class)
@TestPropertySource(locations = "/test2.properties")
public class UserClientUnitTest {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ObjectMapper objectMapper;

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
    void givenUserExistsAndIsValid_whenUpdateUserCalled_thenReturnSuccess() throws IOException {
        User user = objectMapper.readValue(new File("src/test/resources/existing-user.json"), User.class);
        user.setEmail("updated-email@mail.in");

        String updatedUserResponse = "{\n" +
                "    \"userId\": 100001,\n" +
                "    \"userName\": \"updated-name\",\n" +
                "    \"email\": \"updated-email@mail.in\"\n" +
                "}";

        stubFor(patch(urlEqualTo("/api/user/".concat("100001")))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(updatedUserResponse)));

        User updatedUser = userClient.updateUser("100001", user);

        assertEquals(user.getUserId(), updatedUser.getUserId());
        assertEquals("updated-name", updatedUser.getUserName());
        assertEquals("updated-email@mail.in", updatedUser.getEmail());

        assertDoesNotThrow(() -> userClient.updateUser("100001", user));
    }

    @Test
    void givenUserNotFound_whenUpdateUserCalled_thenReturnNotFoundErrorAndFeignException() throws IOException {
        User user = objectMapper.readValue(new File("src/test/resources/existing-user.json"), User.class);
        user.setEmail("updated-email@mail.in");

        stubFor(patch(urlEqualTo("/api/user/".concat("100002")))
                .willReturn(aResponse().withStatus(404)));

        assertThrows(FeignException.class, () -> userClient.updateUser("100002", user));
    }
}
