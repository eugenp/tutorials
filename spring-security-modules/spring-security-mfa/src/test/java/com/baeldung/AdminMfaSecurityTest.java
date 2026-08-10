package com.baeldung;

import com.baeldung.mfa.Application;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
class AdminMfaSecurityTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    void givenAdminWithoutMfa_whenAccessAdminEndpoint_thenForbidden() throws Exception {

        mockMvc.perform(
                        get("/admin/dashboard")
                                .with(user("admin").roles("ADMIN"))
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", containsString("/login")));
    }
}