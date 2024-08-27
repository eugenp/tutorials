package com.baeldung.boot.keycloak.resourceserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;

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
    void givenRequestIsAnonymous_whenGetMe_thenUnauthorized() throws Exception {
        api.perform(get("/me")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("brice.json")
    void givenUserIsBrice_whenGetMe_thenOk()
            throws Exception {
        api.perform(get("/me")).andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetLiveness_thenUnauthorized()
            throws Exception {
        api.perform(get("/actuator/health/liveness")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("brice.json")
    void givenUserIsBrice_whenGetLiveness_thenForbidden()
            throws Exception {
        api.perform(get("/actuator/health/liveness")).andExpect(status().isForbidden());
    }

}
