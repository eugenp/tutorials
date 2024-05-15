package com.baeldung;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ServletResourceServerApplicationIntegrationTest {
    @Autowired
    MockMvc api;

    /*-----------------------------------------------------------------------------*/
    /* /greet                                                                      */
    /* This end-point secured with ".anyRequest().authenticated()" in SecurityConf */
    /*-----------------------------------------------------------------------------*/

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetGreet_thenUnauthorized() throws Exception {
        api.perform(get("/greet"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithJwt("ch4mpy.json")
    void givenUserIsAuthenticated_whenGetGreet_thenOk() throws Exception {
        api.perform(get("/greet"))
            .andExpect(status().isOk())
            .andExpect(content().string("Hello ch4mpy! You are granted with [admin, ROLE_AUTHORIZED_PERSONNEL]."));
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    /* /secured-route                                                                                                      */
    /* This end-point is secured with ".requestMatchers("/secured-route").hasRole("AUTHORIZED_PERSONNEL")" in SecurityConf */
    /*---------------------------------------------------------------------------------------------------------------------*/

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetSecuredRoute_thenUnauthorized() throws Exception {
        api.perform(get("/secured-route"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockAuthentication("ROLE_AUTHORIZED_PERSONNEL")
    void givenUserIsGrantedWithRoleAuthorizedPersonnel_whenGetSecuredRoute_thenOk() throws Exception {
        api.perform(get("/secured-route"))
            .andExpect(status().isOk())
            .andExpect(content().string("Only authorized personnel can read that"));
    }

    @Test
    @WithMockAuthentication("admin")
    void givenUserIsNotGrantedWithRoleAuthorizedPersonnel_whenGetSecuredRoute_thenForbidden() throws Exception {
        api.perform(get("/secured-route"))
            .andExpect(status().isForbidden());
    }

    /*---------------------------------------------------------------------------------------------------------*/
    /* /secured-method                                                                                         */
    /* This end-point is secured with "@PreAuthorize("hasRole('AUTHORIZED_PERSONNEL')")" on @Controller method */
    /*---------------------------------------------------------------------------------------------------------*/

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetSecuredMethod_thenUnauthorized() throws Exception {
        api.perform(get("/secured-method"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockAuthentication("ROLE_AUTHORIZED_PERSONNEL")
    void givenUserIsGrantedWithRoleAuthorizedPersonnel_whenGetSecuredMethod_thenOk() throws Exception {
        api.perform(get("/secured-method"))
            .andExpect(status().isOk())
            .andExpect(content().string("Only authorized personnel can read that"));
    }

    @Test
    @WithMockAuthentication("admin")
    void givenUserIsNotGrantedWithRoleAuthorizedPersonnel_whenGetSecuredMethod_thenForbidden() throws Exception {
        api.perform(get("/secured-method"))
            .andExpect(status().isForbidden());
    }

}
