package com.baeldung.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.security.config.SecSecurityConfig;

import javax.servlet.Filter;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecSecurityConfig.class})
@WebAppConfiguration
public class FormLoginUnitTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    public void givenValidRequestWithValidCredentials_shouldLoginSuccessfully() throws Exception {
        mvc
                .perform(formLogin("/perform_login").user("user1").password("user1Pass"))
                .andExpect(status().isFound())
                .andExpect(authenticated().withUsername("user1"));
    }

    @Test
    public void givenValidRequestWithInvalidCredentials_shouldFailWith401() throws Exception {
        MvcResult result = mvc
                .perform(formLogin("/perform_login").user("random").password("random")).andReturn();
                /*.andExpect(status().isUnauthorized())
                .andDo(print())
                .andExpect(unauthenticated())
                .andReturn();*/

        assertTrue(result.getResponse().getContentAsString().contains("Bad credentials"));
    }
}
