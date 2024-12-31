package com.baeldung.filterresponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.filterresponse.config.AppConfig;

import jakarta.servlet.ServletException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class SpringSecurityJsonViewIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @WithMockUser(username = "admin", password = "adminPass", roles = "ADMIN")
    public void whenAdminRequests_thenOwnerNameIsPresent() throws Exception {
        mvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Item 1"))
                .andExpect(jsonPath("$[0].ownerName").exists());
    }

    @Test
    @WithMockUser(username = "user", password = "userPass", roles = "USER")
    public void whenUserRequests_thenOwnerNameIsAbsent() throws Exception {
        mvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Item 1"))
                .andExpect(jsonPath("$[0].ownerName").doesNotExist());
    }

    @Test
    @WithMockUser(username = "user", password = "userPass", roles = {"ADMIN", "USER"})
    public void whenMultipleRoles_thenExceptionIsThrown() throws Exception {
        ServletException exception = Assertions.assertThrows(ServletException.class, () -> {
            mvc.perform(get("/items"))
                    .andExpect(status().isOk());
        });

        Assertions.assertEquals(exception.getCause().getClass(), IllegalArgumentException.class);
        assertEquals("Ambiguous @JsonView declaration for roles ROLE_ADMIN,ROLE_USER", exception.getCause().getMessage());
    }
}
