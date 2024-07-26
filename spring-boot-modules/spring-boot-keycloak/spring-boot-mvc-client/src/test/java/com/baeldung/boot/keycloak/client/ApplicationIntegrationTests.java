package com.baeldung.boot.keycloak.client;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithOidcLogin;

/**
 * @author ch4mp&#64;c4-soft.com
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ApplicationIntegrationTests {
    @Autowired
    MockMvc api;

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetIndex_thenOk() throws Exception {
        api.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    @WithOidcLogin()
    void givenUserIsBrice_whenGetIndex_thenOk()
            throws Exception {
        api.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetNice_thenRedirectToLogin() throws Exception {
        api.perform(get("/nice")).andExpect(status().is3xxRedirection());
    }

    @Test
    @WithOidcLogin("NICE")
    void givenUserIsGrantedWithNice_whenGetNice_thenOk()
            throws Exception {
        api.perform(get("/nice")).andExpect(status().isOk());
    }

    @Test
    @WithOidcLogin()
    void givenUserIsNotGrantedWithNice_whenGetNice_thenForbidden()
            throws Exception {
        api.perform(get("/nice")).andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetLiveness_thenRedirectToLogin()
            throws Exception {
        api.perform(get("/actuator/health/liveness")).andExpect(status().is3xxRedirection());
    }

    @Test
    @WithOidcLogin()
    void givenUserIsBrice_whenGetLiveness_thenForbidden()
            throws Exception {
        api.perform(get("/actuator/health/liveness")).andExpect(status().isForbidden());
    }

}
