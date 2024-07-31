package com.baelding.springbootredis;

import com.baelding.springbootredis.config.RedisTestConfiguration;
import com.baelding.springbootredis.dto.SessionCreateRequest;
import com.baelding.springbootredis.model.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RedisTestConfiguration.class)
@AutoConfigureMockMvc
class SpringBootRedisApplicationIntegrationTest {

    private static final String V1_SESSIONS_ENDPOINT = "/v1/sessions";
    private static final String V1_GET_SESSION_BY_ID_ENDPOINT_TEMPLATE = V1_SESSIONS_ENDPOINT + "/%s";
    @Autowired
    private WebTestClient webTestClient;
    private static Random random;

    @BeforeAll
    public static void beforeAll() {
        random = new Random();
    }

    @Test
    @DisplayName("""
      WHEN get session endpoint is called
      THEN return a success response.
      """)
    void shouldBeAbleToCallGetSessionsEndpoint() {
        webTestClient.get().uri(V1_SESSIONS_ENDPOINT).exchange().expectStatus().isOk();
    }

    @Test
    @DisplayName("""
      WHEN requested to create a session with certain expiration value
      THEN successfully create a session with the desired expiration.
      """)
    void shouldBeAbleToCreateASessionWithCertainExpiration() {
        Long expirationInSeconds = 10L;

        SessionCreateRequest sessionCreateRequest = SessionCreateRequest.builder().expirationInSeconds(expirationInSeconds).build();

        Session session = webTestClient.post().uri(V1_SESSIONS_ENDPOINT).bodyValue(sessionCreateRequest).exchange().expectStatus().isCreated().expectHeader().exists(HttpHeaders.LOCATION).expectBody(Session.class).returnResult().getResponseBody();

        Assertions.assertNotNull(session);
        Assertions.assertEquals(expirationInSeconds, session.getExpirationInSeconds());
    }

    @Test
    @DisplayName("""
      GIVEN one or multiple session exists
      WHEN get sessions endpoint is called
      THEN return the all the existing session.
      """)
    void shouldBeAbleToGetTheExistingSessions() {
        // Given
        SessionCreateRequest session = SessionCreateRequest.builder().expirationInSeconds(300L).build();

        int numberOfSessionsToCreate = random.nextInt(5);

        List<Session> createdSessions = IntStream.range(0, numberOfSessionsToCreate)
          .mapToObj(i -> webTestClient.post().uri(V1_SESSIONS_ENDPOINT).bodyValue(session).exchange().expectStatus().isCreated().expectBody(Session.class).returnResult().getResponseBody())
          .collect(Collectors.toList());

        // WHEN
        webTestClient.get().uri(V1_SESSIONS_ENDPOINT).exchange().expectStatus()
          // THEN
          .isOk().expectBodyList(Session.class).value(sessions -> createdSessions.forEach(createdSession -> Assertions.assertTrue(sessions.contains(createdSession))));
    }

    @Test
    @DisplayName("""
      GIVEN a session is created with a given identifier
      WHEN the session with the identifier is requested
      THEN return the session
      """)
    void getSessionById() {
        // GIVEN
        SessionCreateRequest sessionCreateRequest = SessionCreateRequest.builder().expirationInSeconds(10L).build();

        Session createdSession = webTestClient.post().uri(V1_SESSIONS_ENDPOINT).bodyValue(sessionCreateRequest).exchange().expectStatus().isCreated().expectBody(Session.class).returnResult().getResponseBody();
        Assertions.assertNotNull(createdSession);

        // WHEN
        webTestClient.get().uri(String.format(V1_GET_SESSION_BY_ID_ENDPOINT_TEMPLATE, createdSession.getId())).exchange()
          // THEN
          .expectStatus().isOk().expectBody(Session.class).isEqualTo(createdSession);
    }

    @Test
    @DisplayName("""
      GIVEN a session identifier which doesn't exists
      WHEN the session with the identifier is requested
      THEN return 404 not found
      """)
    void getSessionByIdNotFound() {
        // GIVEN
        String sessionId = UUID.randomUUID().toString();

        // WHEN
        webTestClient.get().uri(String.format(V1_GET_SESSION_BY_ID_ENDPOINT_TEMPLATE, sessionId)).exchange()
          // THEN
          .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("""
      GIVEN a session is created with a stipulated expiration
      WHEN the session is requested before the expiration period
      THEN return the session
            
      WHEN the same session is requested after the expiration period
      THEN return 404 not found
      """)
    void sessionExpiration() throws InterruptedException {
        long expirationInSeconds = 3L;

        // GIVEN
        SessionCreateRequest sessionCreateRequest = SessionCreateRequest.builder().expirationInSeconds(expirationInSeconds).build();

        Session createdSession = webTestClient.post().uri(V1_SESSIONS_ENDPOINT).bodyValue(sessionCreateRequest).exchange().expectStatus().isCreated().expectBody(Session.class).returnResult().getResponseBody();
        Assertions.assertNotNull(createdSession);

        // WHEN
        webTestClient.get().uri(String.format(V1_GET_SESSION_BY_ID_ENDPOINT_TEMPLATE, createdSession.getId())).exchange()
          // THEN
          .expectStatus().isOk().expectBody(Session.class).isEqualTo(createdSession);

        // WHEN
        Thread.sleep(expirationInSeconds * 1000);
        webTestClient.get().uri(String.format(V1_GET_SESSION_BY_ID_ENDPOINT_TEMPLATE, createdSession.getId())).exchange()
          // THEN
          .expectStatus().isNotFound();
    }
}
