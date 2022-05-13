package com.baeldung.keycloaksoap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The class contains Live tests.
 * These tests expect that the Keycloak server is up and running on port 8080.
 */
@DisplayName("Keycloak SOAP Webservice Live Tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class KeycloakSoapLiveTest {

    private static final Logger logger = LoggerFactory.getLogger(KeycloakSoapLiveTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${grant.type}")
    private String grantType;

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    @Value("${url}")
    private String keycloakUrl;

    /**
     * Test a happy flow. Test the <i>janedoe</i> user.
     * This user should be configured in Keycloak server with a role <i>user</i>
     */
    @Test
    @DisplayName("Get Products With Access Token")
    void givenAccessToken_whenGetProducts_thenReturnProduct() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "text/xml");
        headers.set("Authorization", "Bearer " + generateToken("janedoe", "password"));
        HttpEntity<String> request = new HttpEntity<>(Utility.getGetProductDetailsRequest(), headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/ws/api/v1/", request, String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseEntity.getBody()).isNotBlank();
        assertThat(responseEntity.getBody()).containsIgnoringCase(":id>1</");
    }

    /**
     * A negative test. Deliberately pass wrong credentials to Keycloak. Test the invalid <i>janeadoe</i> user.
     * Keycloak returns Unauthorized. Assert 401 status and empty body.
     */
    @Test
    @DisplayName("Get Products With Wrong Access Token")
    void givenWrongAccessToken_whenGetProducts_thenReturnError() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "text/xml");
        headers.set("Authorization", "Bearer " + generateToken("janeadoe", "password"));
        HttpEntity<String> request = new HttpEntity<>(Utility.getGetProductDetailsRequest(), headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/ws/api/v1/", request, String.class);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(responseEntity.getBody()).isBlank();
    }

    /**
     * Happy flow to test <i>deleteProduct</i> operation. Test the <i>jhondoe</i> user.
     * This user should be configured in Keycloak server with a role <i>user</i>
     */
    @Test
    @DisplayName("Delete Product With Access Token")
    void givenAccessToken_whenDeleteProduct_thenReturnSuccess() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "text/xml");
        headers.set("Authorization", "Bearer " + generateToken("jhondoe", "password"));
        HttpEntity<String> request = new HttpEntity<>(Utility.getDeleteProductsRequest(), headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/ws/api/v1/", request, String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseEntity.getBody()).isNotBlank();
        assertThat(responseEntity.getBody()).containsIgnoringCase("Deleted the product with the id");
    }

    /**
     * Negative flow to test <i></i>. Test the <i>janedoe</i> user.
     * Obtain the access token of <i>janedoe</i> and access the admin operation <i>deleteProduct</i>
     * Assume <i>janedoe</i> has restricted access to <i>deleteProduct</i> operation
     */
    @Test
    @DisplayName("Delete Products With Unauthorized Access Token")
    void givenUnauthorizedAccessToken_whenDeleteProduct_thenReturnUnauthorized() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "text/xml");
        headers.set("Authorization", "Bearer " + generateToken("janedoe", "password"));
        HttpEntity<String> request = new HttpEntity<>(Utility.getDeleteProductsRequest(), headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/ws/api/v1/", request, String.class);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(responseEntity.getBody()).isNotBlank();
        assertThat(responseEntity.getBody()).containsIgnoringCase("Access is denied");
    }

    private String generateToken(String username, String password) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type", grantType);
            map.add("client_id", clientId);
            map.add("client_secret", clientSecret);
            map.add("username", username);
            map.add("password", password);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
            ResponseEntity<String> response = restTemplate.exchange(keycloakUrl, HttpMethod.POST, entity, String.class);
            return Objects.requireNonNull(response.getBody()).contains("access_token") ? objectMapper.readTree(response.getBody()).get("access_token").asText() : "";
        } catch (Exception ex) {
            logger.error("There is an internal server error. Returning an empty access token", ex);
            return "";
        }

    }

}
