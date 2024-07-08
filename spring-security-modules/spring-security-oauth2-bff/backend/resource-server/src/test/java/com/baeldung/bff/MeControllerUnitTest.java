package com.baeldung.bff;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithAnonymousUser;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import com.c4_soft.springaddons.security.oauth2.test.webmvc.AutoConfigureAddonsWebmvcResourceServerSecurity;
import com.c4_soft.springaddons.security.oauth2.test.webmvc.MockMvcSupport;

@WebMvcTest
@AutoConfigureAddonsWebmvcResourceServerSecurity
class MeControllerUnitTest {
    @Autowired
    MockMvcSupport api;

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetMe_thenOk() throws Exception {
        api.get("/me")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").isEmpty())
            .andExpect(jsonPath("$.email").isEmpty())
            .andExpect(jsonPath("$.roles").isEmpty())
            .andExpect(jsonPath("$.exp").isEmpty());
    }

    @Test
    @WithMockAuthentication(authType = JwtAuthenticationToken.class, name = "ch4mpy", authorities = { "AUTHOR" })
    void givenRequestIsAuthorized_whenGetMe_thenOk() throws Exception {
        api.get("/me")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").isNotEmpty())
            .andExpect(jsonPath("$.roles").isNotEmpty());
    }

}
