package com.baeldung.cloud.openfeign.patcherror.withdefaulthttpclient;

import com.baeldung.cloud.openfeign.ExampleApplication;
import com.baeldung.cloud.openfeign.patcherror.withdefaulthttpclient.client.UserClient;
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

import java.net.HttpURLConnection;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ExampleApplication.class)
@TestPropertySource(locations = "/test1.properties")
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
        wireMockServer = new WireMockServer(8081);
        configureFor("localhost", 8081);
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
    void givenUserIsValid_whenUpdateUserCalled_thenThrowFeignException() {
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


        //assertDoesNotThrow(() -> userClient.updateUser(USER_ID, user));
        assertThrows(FeignException.class, () -> userClient.updateUser(USER_ID, user));
    }

    @Test
    void givenUserIsNotValid_whenUpdateUserCalled_thenReturnNotFoundErrorAndFeignException() {
        User user = userClient.getUser(USER_ID);
        user.setEmail("updated-email@mail.in");

        stubFor(patch(urlEqualTo("/api/user/".concat(USER_ID)))
                .willReturn(aResponse().withStatus(404)));

        assertThrows(FeignException.class, () -> userClient.updateUser(USER_ID, user));
    }
}
