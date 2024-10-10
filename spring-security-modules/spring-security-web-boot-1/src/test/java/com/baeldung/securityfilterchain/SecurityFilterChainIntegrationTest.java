package com.baeldung.securityfilterchain;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(classes = SecurityFilterChainApplication.class)
public class SecurityFilterChainIntegrationTest {
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
    public void whenAdminAccessUserEndpoint_thenOk() throws Exception {
        mvc.perform(get("/user"))
          .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "admin")
    public void whenAdminAccessAdminSecuredEndpoint_thenIsOk() throws Exception {
        mvc.perform(get("/admin"))
          .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "admin")
    public void whenAdminAccessDeleteSecuredEndpoint_thenIsOk() throws Exception {
        mvc.perform(delete("/delete").content("{}"))
          .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousAccessLogin_thenOk() throws Exception {
        mvc.perform(get("/login"))
          .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void whenAnonymousAccessRestrictedEndpoint_thenIsUnauthorized() throws Exception {
        mvc.perform(get("/all"))
          .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails()
    public void whenUserAccessUserSecuredEndpoint_thenOk() throws Exception {
        mvc.perform(get("/user"))
          .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails()
    public void whenUserAccessRestrictedEndpoint_thenOk() throws Exception {
        mvc.perform(get("/all"))
          .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails()
    public void whenUserAccessAdminSecuredEndpoint_thenIsForbidden() throws Exception {
        mvc.perform(get("/admin"))
          .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails()
    public void whenUserAccessDeleteSecuredEndpoint_thenIsForbidden() throws Exception {
        mvc.perform(delete("/delete"))
          .andExpect(status().isForbidden());
    }
}
