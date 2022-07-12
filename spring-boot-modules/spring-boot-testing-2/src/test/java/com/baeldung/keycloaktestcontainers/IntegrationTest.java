package com.baeldung.keycloaktestcontainers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.RestAssured;

@ContextConfiguration(initializers = { IntegrationTest.Initializer.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationTest.class.getName());

    @LocalServerPort
    private int port;

    static final KeycloakContainer keycloak = new KeycloakContainer().withRealmImportFile("keycloak/realm-export.json");

    @PostConstruct
    public void init() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            keycloak.start();
            TestPropertyValues.of("keycloak.auth-server-url=" + keycloak.getAuthServerUrl())
                .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    protected String getJaneDoeBearer() {

        try {
            URI authorizationURI = new URIBuilder(keycloak.getAuthServerUrl() + "/realms/baeldung/protocol/openid-connect/token").build();
            WebClient webclient = WebClient.builder()
                .build();
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.put("grant_type", Collections.singletonList("password"));
            formData.put("client_id", Collections.singletonList("baeldung-api"));
            formData.put("username", Collections.singletonList("jane.doe@baeldung.com"));
            formData.put("password", Collections.singletonList("s3cr3t"));

            String result = webclient.post()
                .uri(authorizationURI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .block();

            JacksonJsonParser jsonParser = new JacksonJsonParser();

            return "Bearer " + jsonParser.parseMap(result)
                .get("access_token")
                .toString();
        } catch (URISyntaxException e) {
            LOGGER.error("Can't obtain an access token from Keycloak!", e);
        }

        return null;
    }
}
