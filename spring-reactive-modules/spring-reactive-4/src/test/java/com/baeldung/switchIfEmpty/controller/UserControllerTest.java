package com.baeldung.switchIfEmpty.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.switchIfEmpty.model.User;
import com.baeldung.switchIfEmpty.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserController userController;

    private Map<String, User> usersCache;

    protected ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setLogger() {
        Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);
        logger.setLevel(Level.DEBUG);
        listAppender = new ListAppender<>();
        logger.addAppender(listAppender);
        listAppender.start();
    }

    @Test
    void givenUserDataIsAvailableInCache_whenUserByIdIsRequestedWithDeferParameter_thenCachedResponseShouldBeRetrieved() {
        usersCache = new HashMap<>();
        User cachedUser = new User("66b29672e6f99a7156cc4ada", "gwen_dodson@beadzza.bmw", "boyle94", "admin");
        usersCache.put("66b29672e6f99a7156cc4ada", cachedUser);
        userService.getUsers()
            .putAll(usersCache);

        webTestClient.get()
            .uri("/api/v1/user/66b29672e6f99a7156cc4ada?withDefer=true")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo("{\"id\":\"66b29672e6f99a7156cc4ada\"," + "\"email\":\"gwen_dodson@beadzza.bmw\",\"username\":\"boyle94\",\"roles\":\"admin\"}");

        assertTrue(listAppender.list.stream()
            .anyMatch(e -> e.toString()
                .contains("Fetched user 66b29672e6f99a7156cc4ada from cache")));

        assertTrue(listAppender.list.stream()
            .noneMatch(e -> e.toString()
                .contains("Fetched user 66b29672e6f99a7156cc4ada from file")));
    }

    @Test
    void givenUserDataIsAvailableInCache_whenUserByIdIsRequestedWithoutDeferParameter_thenUserIsFetchedFromFileInAdditionToCache() {
        usersCache = new HashMap<>();
        User cachedUser1 = new User("66b29672e6f99a7156cc4ada", "gwen_dodson@beadzza.bmw", "boyle94", "admin");
        usersCache.put("66b29672e6f99a7156cc4ada", cachedUser1);
        userService.getUsers()
            .putAll(usersCache);

        webTestClient.get()
            .uri("/api/v1/user/66b29672e6f99a7156cc4ada?withDefer=false")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo("{\"id\":\"66b29672e6f99a7156cc4ada\"," + "\"email\":\"gwen_dodson@beadzza.bmw\",\"username\":\"boyle94\",\"roles\":\"admin\"}");

        assertTrue(listAppender.list.stream()
            .anyMatch(e -> e.toString()
                .contains("Fetched user 66b29672e6f99a7156cc4ada from file")));

        assertTrue(listAppender.list.stream()
            .anyMatch(e -> e.toString()
                .contains("Fetched user 66b29672e6f99a7156cc4ada from cache")));
    }

    @Test
    void givenUserDataIsNotAvailableInCache_whenUserByIdIsRequestedWithDeferParameter_thenFileResponseShouldBeRetrieved() {
        webTestClient.get()
            .uri("/api/v1/user/66b29672e6f99a7156cc4ada?withDefer=true")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo("{\"id\":\"66b29672e6f99a7156cc4ada\",\"email\":\"gwen_dodson@beadzza.bmw\",\"username\":\"boyle94\",\"roles\":\"admin\"}");

        assertTrue(listAppender.list.stream()
            .anyMatch(e -> e.toString()
                .contains("Fetched user 66b29672e6f99a7156cc4ada from file")));

        assertTrue(listAppender.list.stream()
            .noneMatch(e -> e.toString()
                .contains("Fetched user 66b29672e6f99a7156cc4ada from cache")));
    }

    @Test
    void givenUserDataIsNotAvailableInCache_whenUserByIdIsRequestedWithoutDeferParameter_thenFileResponseShouldBeRetrieved() {
        webTestClient.get()
            .uri("/api/v1/user/66b29672e6f99a7156cc4ada?withDefer=false")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo("{\"id\":\"66b29672e6f99a7156cc4ada\"," + "\"email\":\"gwen_dodson@beadzza.bmw\",\"username\":\"boyle94\",\"roles\":\"admin\"}");

        assertTrue(listAppender.list.stream()
            .anyMatch(e -> e.toString()
                .contains("Fetched user 66b29672e6f99a7156cc4ada from file")));

        assertTrue(listAppender.list.stream()
            .noneMatch(e -> e.toString()
                .contains("Fetched user 66b29672e6f99a7156cc4ada from cache")));
    }

}
