package com.baeldung.springbootsecurity.form_login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = com.baeldung.springbootsecurity.form_login.SpringBootSecurityApplication.class)
public class FormLoginIntegrationTest {

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
    public void givenRequestWithoutSessionOrCsrfToken_shouldFailWith403() throws Exception {
        mvc
                .perform(post("/"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenRequestWithInvalidCsrfToken_shouldFailWith403() throws Exception {
        mvc
                .perform(post("/").with(csrf().useInvalidToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenRequestWithValidCsrfTokenAndWithoutSessionToken_shouldReceive302WithLocationHeaderToLoginPage() throws Exception {
        MvcResult mvcResult = mvc.perform(post("/").with(csrf())).andReturn();
        assertTrue(mvcResult.getResponse().getStatus() == 302);
        assertTrue(mvcResult.getResponse().getHeader("Location").contains("login"));
    }

    @Test
    public void givenValidRequestWithValidCredentials_shouldLoginSuccessfully() throws Exception {
        mvc
                .perform(formLogin().user("baeldung").password("baeldung"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("baeldung"));
    }

    @Test
    public void givenValidRequestWithInvalidCredentials_shouldFailWith401() throws Exception {
        MvcResult result = mvc
                .perform(formLogin().user("random").password("random"))
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andExpect(unauthenticated())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Bad credentials"));
    }
}
