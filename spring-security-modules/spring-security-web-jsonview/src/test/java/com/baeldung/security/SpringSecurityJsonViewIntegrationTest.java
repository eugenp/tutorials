package com.baeldung.security;

import com.baeldung.spring.AppConfig;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class SpringSecurityJsonViewIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
        expectedException.expect(new BaseMatcher<NestedServletException>() {
            @Override
            public boolean matches(Object o) {
                NestedServletException exception = (NestedServletException) o;
                return exception.getCause() instanceof IllegalArgumentException && exception.getCause().getMessage().equals("Ambiguous @JsonView declaration for roles ROLE_ADMIN,ROLE_USER");
            }

            @Override
            public void describeTo(Description description) {

            }
        });

        mvc.perform(get("/items"))
                .andExpect(status().isOk());

    }
}
