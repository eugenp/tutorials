package com.baeldung.denyonmissing;

import jakarta.servlet.ServletException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DenyApplication.class)
public class DenyOnMissingControllerIntegrationTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    @WithMockUser(username = "user")
    public void givenANormalUser_whenCallingHello_thenAccessDenied() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello world!"));
    }

    @Test
    @WithMockUser(username = "user")
    public void givenANormalUser_whenCallingBye_thenAccessDenied() {
        ServletException exception = Assertions.assertThrows(ServletException.class, () -> mockMvc.perform(get("/bye")));

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(exception.getCause().getClass(), AuthorizationDeniedException.class);
    }
}