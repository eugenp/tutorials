package com.baeldung.session;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * This Live Test requires the service to be up and running.
 */
public class SessionConfigurationLiveTest {

    private static final String USER = "user1";
    private static final String PASSWORD = "user1Pass";
    private static final String SESSION_SVC_URL = "http://localhost:8080/session-max-interval";

    @Test
    public void givenValidUser_whenRequestResourceAfterSessionExpiration_thenRedirectedToInvalidSessionUri() throws Exception {
        SessionFilter sessionFilter = new SessionFilter();
        simpleSvcRequestLoggingIn(sessionFilter);
        Response resp2 = simpleResponseRequestUsingSessionNotFollowingRedirects(sessionFilter);
        assertThat(resp2.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(resp2.getBody()
            .asString()).isEqualTo("Max Inactive Interval before Session expires: 60");

        // session will be expired in 60 seconds...
        Thread.sleep(62000);
        Response resp3 = simpleResponseRequestUsingSessionNotFollowingRedirects(sessionFilter);

        assertThat(resp3.getStatusCode()).isEqualTo(HttpStatus.FOUND.value());
        assertThat(resp3.getHeader("Location")).isEqualTo("http://localhost:8080/invalidSession.html");
    }

    @Test
    public void givenValidUser_whenLoginMoreThanMaxValidSession_thenRedirectedToExpiredSessionUri() throws Exception {
        SessionFilter sessionFilter = new SessionFilter();
        simpleSvcRequestLoggingIn(sessionFilter);
        simpleSvcRequestLoggingIn();

        // this login will expire the first session
        simpleSvcRequestLoggingIn();

        // now try to access a resource using expired session
        Response resp4 = given().filter(sessionFilter)
            .and()
            .redirects()
            .follow(false)
            .when()
            .get(SESSION_SVC_URL);

        assertThat(resp4.getStatusCode()).isEqualTo(HttpStatus.FOUND.value());
        assertThat(resp4.getHeader("Location")).isEqualTo("http://localhost:8080/sessionExpired.html");
    }

    private static void simpleSvcRequestLoggingIn() {
        simpleSvcRequestLoggingIn(null);
    }

    private static void simpleSvcRequestLoggingIn(SessionFilter sessionFilter) {
        Response response = simpleResponseSvcRequestLoggingIn(Optional.ofNullable(sessionFilter));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()
            .asString()).isEqualTo("Max Inactive Interval before Session expires: 60");
    }

    private static Response simpleResponseSvcRequestLoggingIn(Optional<SessionFilter> sessionFilter) {
        RequestSpecification spec = given().auth()
            .form(USER, PASSWORD);
        sessionFilter.ifPresent(filter -> spec.and()
            .filter(filter));
        return spec.when()
            .get(SESSION_SVC_URL);
    }

    private static Response simpleResponseRequestUsingSessionNotFollowingRedirects(SessionFilter sessionFilter) {
        return given().filter(sessionFilter)
            .and()
            .redirects()
            .follow(false)
            .when()
            .get(SESSION_SVC_URL);
    }

}
