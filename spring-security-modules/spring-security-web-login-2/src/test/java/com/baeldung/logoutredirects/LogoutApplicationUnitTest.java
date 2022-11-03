package com.baeldung.logoutredirects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.logoutredirects.securityconfig.SpringSecurityConfig;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SpringSecurityConfig.class)
public class LogoutApplicationUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(value = "spring")
    @Test
    public void whenLogout_thenDisableRedirect() throws Exception {

        this.mockMvc.perform(post("/logout").with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").doesNotExist())
            .andExpect(unauthenticated())
            .andReturn();
    }

}