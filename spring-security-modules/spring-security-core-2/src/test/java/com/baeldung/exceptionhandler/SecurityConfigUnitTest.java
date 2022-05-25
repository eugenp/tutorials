package com.baeldung.exceptionhandler;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.exceptionhandler.security.SecurityConfig;

@RunWith(SpringRunner.class)
@WebMvcTest(SecurityConfig.class)
class SecurityConfigUnitTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void whenUserAccessLogin_shouldSucceed() throws Exception {
        mvc.perform(get("/login"))
            .andExpect(status().isOk());
    }

    @Test
    void whenUserAccessWithWrongCredentials_shouldRedirectToCustomErrorPage() throws Exception {
        mvc.perform(formLogin("/login").user("username", "wrong")
                .password("password", "credentials"))
            .andExpect(redirectedUrl("/customError"));
    }

    @Test
    void whenUserAccessWithCorrectCredentials_shouldRedirectToHome() throws Exception {
        mvc.perform(formLogin("/login").user("username", "user")
                .password("password", "password"))
            .andExpect(redirectedUrl("/home"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    void whenUserAccessToSecuredPageWithoutUserRole_shouldRedirectToDeniedPage() throws Exception {
        mvc.perform(get("/secured"))
            .andExpect(redirectedUrl("/access-denied"));
    }
}