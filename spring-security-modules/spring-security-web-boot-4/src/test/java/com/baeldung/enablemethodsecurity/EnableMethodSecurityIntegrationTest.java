package com.baeldung.enablemethodsecurity;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = EnableMethodSecurityApplication.class)
public class EnableMethodSecurityIntegrationTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
          .apply(springSecurity())
          .build();
    }

    @Test
    @WithUserDetails(value = "admin")
    public void whenAdminAccessOpenEndpoint_thenOk() throws Exception {
        mvc.perform(get("/openPolicy"))
          .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "admin")
    public void whenAdminAccessRestrictedEndpoint_thenOk() throws Exception {
        mvc.perform(get("/restrictedPolicy"))
          .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails()
    public void whenUserAccessOpenEndpoint_thenOk() throws Exception {
        mvc.perform(get("/openPolicy"))
          .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails()
    public void whenUserAccessRestrictedEndpoint_thenIsForbidden() throws Exception {
        mvc.perform(get("/restrictedPolicy"))
          .andExpect(status().isForbidden());
    }
}
