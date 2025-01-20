package com.baeldung.security.ott;

import com.baeldung.security.ott.service.OttSenderService;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SampleOttApplicationUnitTest {

    @LocalServerPort
    int port;

    @Autowired
    OttSenderService ottSenderService;

    @Test
    void whenLoginWithOtt_thenSuccess() throws Exception {
        var baseUrl = "http://localhost:" + port;

        var conn = Jsoup.newSession().followRedirects(true);
        var loginPage = conn.newRequest(baseUrl)
          .followRedirects(true)
          .get();

        var tokenForms = loginPage.select("form#ott-form");
        assertEquals(1,tokenForms.size());
        var tokenForm = tokenForms.get(0);
        var generateAction = tokenForm.attr("action");
        assertNotNull(generateAction);
        var csrfToken = requireNonNull(tokenForm.selectFirst("input[name=_csrf]")).attr("value");
        assertNotNull(csrfToken);

        var tokenSubmitPage = conn.newRequest(baseUrl + generateAction)
          .data("username","user")
          .data("_csrf",csrfToken)
          .post();

        var tokenSubmitForm = tokenSubmitPage.selectFirst("form.login-form");
        assertNotNull(tokenSubmitForm);
        var tokenSubmitAction = tokenSubmitForm.attr("action");
        csrfToken = requireNonNull(tokenSubmitForm.selectFirst("input[name=_csrf]")).attr("value");
        assertNotNull(csrfToken);

        // Retrieve the generated token
        var optToken = this.ottSenderService.getLastTokenForUser("user");
        assertTrue(optToken.isPresent());

        var homePage = conn.newRequest(baseUrl + tokenSubmitAction)
          .data("token", optToken.get())
          .data("_csrf",csrfToken)
          .post();

        var username = requireNonNull(homePage.selectFirst("span#current-username")).text();
        assertEquals("user",username);

    }
}