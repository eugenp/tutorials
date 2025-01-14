package com.baeldung.security.ott;

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
import org.springframework.web.client.RestClient;

import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SampleOttApplicationTest {

    @LocalServerPort
    int port;


    @Test
    void whenLoginWithOtt_thenSuccess() {

        // Stateful TestRestTemplate that'll keep session cookies and follow redirects
        var tpl = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES, TestRestTemplate.HttpClientOption.ENABLE_REDIRECTS);

        var baseUrl = "http://localhost:" + port;

        ResponseEntity<String> result = tpl.getForEntity(baseUrl , String.class);
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertTrue(requireNonNull(result.getHeaders().getContentType()).isCompatibleWith(MediaType.TEXT_HTML));

        var loginPage = Jsoup.parse(requireNonNull(result.getBody()));
        var tokenForms = loginPage.select("form#ott-form");
        assertEquals(1,tokenForms.size());
        var tokenForm = tokenForms.get(0);
        var generateAction = tokenForm.attr("action");
        assertNotNull(generateAction);
        var csrfToken = requireNonNull(tokenForm.selectFirst("input[name=_csrf]")).attr("value");
        assertNotNull(csrfToken);

//        result = tpl.postForEntity(baseUrl + generateAction,createFormEntity(Map.of("username","alice","_csrf",csrfToken)),String.class);
//        assertTrue(result.getStatusCode().is2xxSuccessful());
//        assertTrue(requireNonNull(result.getHeaders().getContentType()).isCompatibleWith(MediaType.TEXT_HTML));


    }

    private HttpEntity<MultiValueMap<String, String>> createFormEntity(Map<String,String> data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        data.forEach(map::add);

        return new HttpEntity<>(map, headers);
    }
  
}