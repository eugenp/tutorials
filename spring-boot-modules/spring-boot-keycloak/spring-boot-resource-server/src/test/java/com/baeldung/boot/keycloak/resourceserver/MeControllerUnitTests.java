package com.baeldung.boot.keycloak.resourceserver;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;

/**
 * @author ch4mp&#64;c4-soft.com
 */
@WebMvcTest(MeController.class)
@Import(SecurityConfig.class)
class MeControllerUnitTests {
    @Autowired
    MockMvc api;

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetMe_thenUnauthorized() throws Exception {
        api.perform(get("/me")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("brice.json")
    void givenUserIsBrice_whenGetMe_thenOkAndPayloadContainsBriceAsNameAndNiceAmongRoles()
            throws Exception {
        api.perform(get("/me")).andExpect(status().isOk()).andExpect(jsonPath("$.name", is("brice")))
                .andExpect(jsonPath("$.roles", hasItem("NICE")));
    }

}
