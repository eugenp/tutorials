package com.baeldung.fakingouath2sso;

import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(NoOAuth2Config.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class FakingOauth2SsoApplicationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void whenBypssingTheOAuthWithSpringConfig_thenItShouldBeAbleToLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .with(oauth2Login()))
            .andExpect(status().isOk());
    }

    static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(8787);
        configureFor(8787);
        wireMockServer.start();

        stubFor(get(urlEqualTo("/realms/my-realm/.well-known/openid-configuration")).willReturn(aResponse().withHeader("Content-Type", "application/json")
            .withBody("""
                
                 {
                  "issuer": "http://localhost:8787/realms/my-realm",
                  "authorization_endpoint": "http://localhost:8787/realms/my-realm/oauth/authorize",
                  "token_endpoint": "http://localhost:8787/realms/my-realm/oauth/token",
                  "userinfo_endpoint": "http://localhost:8787/realms/my-realm/userinfo",
                  "jwks_uri": "http://localhost:8787/realms/my-realm/.well-known/jwks.json",
                  "response_types_supported": [
                    "code",
                    "token",
                    "id_token",
                    "code token",
                    "code id_token",
                    "token id_token",
                    "code token id_token",
                    "none"
                  ],
                  "subject_types_supported": [
                    "public"
                  ],
                  "id_token_signing_alg_values_supported": [
                    "RS256"
                  ],
                  "scopes_supported": [
                    "openid",
                    "email",
                    "profile"
                  ]
                }
                """)));

    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void whenAuthServerIsMocked_thenItShouldBeAbleToLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                .with(oauth2Login()))
            .andExpect(status().isOk());
    }

}

